package fr.jdiaz.compiler.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import fr.jdiaz.compiler.parser.reducers.TokenReducer;
import fr.jdiaz.compiler.parser.tokenizer.TokenFilter;
import fr.jdiaz.compiler.parser.tokenizer.Tokenizer;
import fr.jdiaz.compiler.parser.tokenizer.TokenizerImpl;

public class Parser {
    
    private static final Logger LOGGER = Logger.getLogger(Parser.class);
    
    private static final String UTF8 = "UTF-8";
    private static final String DEFAULT_TOKENIZER = "fr.jdiaz.compiler.parser.tokenizer.TokenizerImpl";
        
    private List<TokenFilter> tokenFilters = null;
    private Map<String, TokenReducer> filterNameToReducer;
    private Map<Integer, List<TokenReducer>> levelToTokenList;
    
    private boolean mIsInitialized = false;
    private URI mParserFileUri = null;
    
    public Parser(URI uri) {
        mParserFileUri = uri;
    }
    
    private static String readFileContent(final String fileName) throws IOException {
        // Récupération du fichier
        
        final File file = new File(fileName);
        
        // Création d'un flux de lecture de fichier
        final InputStream fileReader = new FileInputStream(file);
        
        return IOUtils.toString(fileReader, UTF8);
    }


    
    public synchronized void init() throws MalformedURLException {
        if (mIsInitialized) {
            return;
        }
        
        levelToTokenList = new TreeMap<>();
        
        SAXReader saxReader = new SAXReader();
        try {
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(mParserFileUri);
            }
            
            Document document;
            if ("classpath".equalsIgnoreCase(mParserFileUri.getScheme())) {
                document = saxReader.read(this.getClass().getClassLoader().getResource(mParserFileUri.getHost()));
            }
            else {
                document = saxReader.read(mParserFileUri.toURL());
            }
            
            Node n = document.selectSingleNode("//Tokenizer");
            initTokenizer(n);
            n = document.selectSingleNode("//Reducers");
            initReducers(n);
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(levelToTokenList);
            }
            
        } catch (DocumentException e1) {
            LOGGER.error(e1);
        }
        
        mIsInitialized = true;
    }
    
    private void initTokenizer(Node n) {
        List<Tokenizer> tokenizers = new ArrayList<>();
        
        filterNameToReducer = new HashMap<>();
        
        TokenizerImpl tokenizer;
        
        if (n instanceof Element) {
            Element tokenizerElement = (Element)n;
            
            String className = tokenizerElement.attributeValue(
                    "class", 
                    DEFAULT_TOKENIZER
                    );

            try {
                tokenFilters = new ArrayList<>();
                tokenizer = (TokenizerImpl)Class.forName(className).newInstance();
                tokenizer.init(this, tokenizerElement);
                tokenizers.add(tokenizer);
                
            } catch (InstantiationException | IllegalAccessException
                    | ClassNotFoundException e) {
                LOGGER.error(e);
            }
        }
        
    }
    
    private void initReducers(Node n) {
        @SuppressWarnings("rawtypes")
        List reducers = n.selectNodes("Reducer");
        
        for (Object reducerObject : reducers) {
            if (!(reducerObject instanceof Element)) {
                continue;
            }
            
            Element reducerElement = (Element)reducerObject;
            String name = reducerElement.attributeValue("name");
            
            String level = reducerElement.attributeValue("level", "0");
            Integer levelInt = Integer.valueOf(level);
            List<TokenReducer> tokenReducerList= levelToTokenList.get(levelInt);
            if (tokenReducerList == null) {
                tokenReducerList = new ArrayList<>();
                levelToTokenList.put(levelInt, tokenReducerList);
            }
            
            @SuppressWarnings("rawtypes")
            List subNodes = reducerElement.elements();
            for (Object subNode: subNodes) {
                if (subNode instanceof Element) {
                    Element element = (Element)subNode;
                    TokenReducer reducer = this.createTokenReducer(element);
                    filterNameToReducer.put(name, reducer);
                    tokenReducerList.add(reducer);
                }
            }
            
        }
        
    }
    
    public List<Token> tokenize(String fileContent) {
        int fileContentLength = fileContent.length();
        int i = 0;
        
        List<Token> tokenList = new ArrayList<>();
        Token token;
        Integer otherCharFirstIndex = null;
        
        boolean otherChar = false;
        charloop: while (i < fileContentLength) {
            String workString = fileContent.substring(i);
            
            for (TokenFilter tokenFilter : tokenFilters) {
                
                Pattern pattern = tokenFilter.getPattern();
                
                Matcher matcher =  pattern.matcher(workString);
                if (matcher.find()) {
                    if (otherChar) { // NOSONAR
                        String otherChars = fileContent.substring(otherCharFirstIndex.intValue(), i);
                        token = new Token(otherChars, otherCharFirstIndex, otherChars.length(), "other");
                        
                        tokenList.add(token);
                        otherCharFirstIndex = null;
                        otherChar = false;
                    }
                    
                    String group = matcher.group();
                    int groupLength = group.length();
                    
                    token = new Token(group, i, groupLength, tokenFilter.getFilterName());
                    tokenList.add(token);
                    
                    i += groupLength;
                    
                    continue charloop;
                }
            
            }
            
            if (!otherChar) {
                otherChar = true;
                otherCharFirstIndex = Integer.valueOf(i);
            }
            i++;
            
        }
        
        return tokenList;
        
    }
    
    public void addTokenFilter(TokenFilter filter) {
        tokenFilters.add(filter);
    }
    
    public TokenReducer getNamedReducer(String id) {
        return filterNameToReducer.get(id);
    }
    
    public TokenReducer createTokenReducer(Element element) {
        String reducerName = element.getName();
        String tokenClassName = String.format("fr.jdiaz.compiler.parser.reducers.%sReducer", reducerName);
        
        TokenReducer tokenReducer = null;
        
        try {
            Class<?> tokenClass = Class.forName(tokenClassName);
            tokenReducer = (TokenReducer)tokenClass.newInstance();
            tokenReducer.init(this, element);
            
        } catch (Exception e) {
            LOGGER.error(e);
        }
        
        return tokenReducer;
    }
    
    public List<Token> tokenize(File file) throws IOException {
        return tokenize(readFileContent(file.getAbsolutePath()));
    }
    
    public List<Token> reduceTokenList(List<Token> tokens) {
        List<Token> result = new ArrayList<Token>();
        List<Token> workTokens = new ArrayList<Token>(tokens);
        
        for(Map.Entry<Integer, List<TokenReducer>>levelToList : levelToTokenList.entrySet()) {
            
            Integer level = levelToList.getKey();
            
            if (level == 0) {
                continue;
            }
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("reducer level " + level);
                LOGGER.debug("reducers " + levelToList.getValue());
            }
            
            result = new ArrayList<>();
            
            while (!workTokens.isEmpty()) {
                
                boolean matched = false;
                
                List<TokenReducer> reducers = levelToList.getValue();
                
                for (TokenReducer reducer : reducers) {
                    List<Token> matchedTokens = reducer.tokenMatching(this, workTokens);
                    
                    if (!CollectionUtils.isEmpty(matchedTokens)) {
                        TokenList tokenList = TokenList.instantiate(matchedTokens, reducer.getReducerName());
                        workTokens = workTokens.subList(matchedTokens.size(), workTokens.size());
                        result.add(tokenList);
                        matched = true;
                        break;
                    }
                    
                }
                
                
                if (!matched) {
                    result.add(workTokens.get(0));
                    workTokens.remove(0);
                }
            }
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("result count " + result.size());
            }
            
            workTokens = new ArrayList<>(result);
            
        }
        
        return result;
    }
    
    public ParseResult parseFile(File file) throws IOException {
        ParseResult pr = new ParseResult();
        this.init();
        
        List<Token> tokens = tokenize(file);
        pr.setInitialTokenCount(tokens.size());
        
        List<Token> reducedTokens = this.reduceTokenList(tokens);
        pr.setReducedTokens(reducedTokens);
        
        return pr;
    }
}
