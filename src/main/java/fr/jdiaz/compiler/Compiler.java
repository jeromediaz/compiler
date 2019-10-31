package fr.jdiaz.compiler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import fr.jdiaz.compiler.branches.InstructionBranch;
import fr.jdiaz.compiler.parser.ParseResult;
import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.ParserFactory;
import fr.jdiaz.compiler.parser.Token;

public class Compiler {
    
    private static final Logger LOGGER = Logger.getLogger(Compiler.class);
    
    private Map<String, String> mTokenKindToWorkerClass;
    private Map<String, InstructionFactory> mTokenKindToWorkerFactoryInstance;
    private Map<String, Class<? extends Instruction>> mTokenKindToClass;
    private String mDefaultWorkerClass = null;
    private InstructionFactory mDefaultWorkerFactory = null;
    
    private URI mUri = null;
    private boolean isInitialized = false;
    
    private Parser mParser = null;
    
    public Compiler(URI uri) {
        mTokenKindToWorkerClass = new HashMap<>();
        mTokenKindToWorkerFactoryInstance = new HashMap<>();
        mTokenKindToClass = new HashMap<>();
        mUri = uri;
    }
    
    public synchronized void init() {
        if (isInitialized) {
            return;
        }
        
        SAXReader saxReader = new SAXReader();
        try {
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(mUri);
            }
            
            Document document;
            if ("classpath".equalsIgnoreCase(mUri.getScheme())) {
                document = saxReader.read(this.getClass().getClassLoader().getResource(mUri.getHost()));
            }
            else {
                document = saxReader.read(mUri.toURL());
            }
            
            Element builderElement = document.getRootElement();
            String packageName = builderElement.attributeValue("package");
            
            @SuppressWarnings("unchecked")
            List<Node> factoryNodes = document.selectNodes("//Factory");
            for (Node factoryNode : factoryNodes) {
                Element factoryElement = (Element)factoryNode;
                initInstructionFactory(packageName, factoryElement);
            }
            
            String parserAttribute = builderElement.attributeValue("parser");
            URI parserUri = new URI(parserAttribute);
            
            mParser = ParserFactory.getInstance().getParser(parserUri);
        } catch (Exception e1) {
            LOGGER.error(e1);
        }
        
        isInitialized = true;
    }
    
    public void initInstructionFactory(String packageName, Element factoryElement) {
        String kind = factoryElement.attributeValue("kind");
        String className = factoryElement.attributeValue("class");
        String fullClassName = String.format("%s.%sInstruction", packageName, className);
        String factoryFullClassName = String.format("%sFactory", fullClassName);
        
        boolean factoryExists = false;
        Class<? extends Instruction> instructionClass = null;
        
        try {
            Class<?> clazz = Class.forName(fullClassName);
            if (Instruction.class.isAssignableFrom(clazz)) {
                instructionClass = (Class<? extends Instruction>)clazz;
            }
        } catch (ClassNotFoundException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e);
            }
        }
        
        try {
            Class<?> clazz = Class.forName(factoryFullClassName);
            if (InstructionFactory.class.isAssignableFrom(clazz)) {
                factoryExists = true;
            }
        } catch (ClassNotFoundException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e);
            }
        }
        
        if (!StringUtils.isEmpty(kind)) {
            if (factoryExists) {
                mTokenKindToWorkerClass.put(kind, factoryFullClassName);
            } else if (instructionClass != null) {
                mTokenKindToClass.put(kind,  instructionClass);
            }
        }
        else {
            mDefaultWorkerClass = fullClassName;
        }
    }
    
    public Instruction getInstruction(Token token) {
        String tokenName = token.getName();
        
        Class<? extends Instruction> clazz = mTokenKindToClass.get(tokenName);
        if (clazz == null) {
            return null;
        }
        
        Instruction instruction = null;
        try {
            instruction = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(e);
        }
        
        return instruction;
    }
    
    public InstructionFactory getInstructionFactory(Token token) {
        String tokenName = token.getName();
        InstructionFactory instance = null;
        if (mTokenKindToWorkerFactoryInstance.containsKey(tokenName)) {
            instance = mTokenKindToWorkerFactoryInstance.get(tokenName);
        }
        else if (mTokenKindToClass.containsKey(tokenName)) {
            return null;
        }
        else if (mTokenKindToWorkerClass.containsKey(tokenName)) {
            try {
                Class<?> instructionClass = Class.forName(mTokenKindToWorkerClass.get(tokenName));
                instance = (InstructionFactory)instructionClass.newInstance();
                mTokenKindToWorkerFactoryInstance.put(tokenName, instance);
                
            } catch (Exception e) {
                LOGGER.error(e);
            }
        } else if (mDefaultWorkerClass != null) {
            if (mDefaultWorkerFactory != null) {
                instance = mDefaultWorkerFactory;
            } else {
                try {
                    Class<?> instructionClass = Class.forName(mDefaultWorkerClass);
                    instance = (InstructionFactory)instructionClass.newInstance();
                    mDefaultWorkerFactory = instance;
                } catch (Exception e) {
                    LOGGER.error(e);
                }
            }
        }
        
        return instance;
    }
    
    public Bytecode generateBytecode(File file) throws IOException {
        this.init();
        ParseResult pr = mParser.parseFile(file);
        
        return generateBytecode(pr);
    }
    
    public Bytecode generateBytecode(String string) throws IOException {
        this.init();
        
        ParseResult pr = mParser.parseString(string);
        
        return generateBytecode(pr);
    }
    
    private Bytecode generateBytecode(ParseResult pr) {
        init();
        
        Bytecode bytecode = new Bytecode();
        
        int instructionLine = 0;
        
        InstructionBranch workBranch = null;
        
        bytecode.addInstructionBranch(workBranch);
        for (Token token : pr) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(String.format("%s %s", token.getName(), token.getToken()));
            }
            
            InstructionFactory factory = getInstructionFactory(token);
            Instruction worker;
            if (factory != null) {
                worker = factory.instantiate(token);
                workBranch = factory.getBranch(workBranch, instructionLine);
                
            } else {
                worker = getInstruction(token);
                if (worker != null) {
                    worker.init(token);
                }
            }
            
            if (worker == null) {
                return null;
            }
            
            bytecode.addInstructionBranch(workBranch);
            
            bytecode.addInstruction(worker);
            
            instructionLine++;
        }
        
        return bytecode;
    }
    
}
