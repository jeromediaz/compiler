package fr.jdiaz.compiler.parser.tokenizer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import fr.jdiaz.compiler.parser.Parser;

public class TokenizerImpl implements Tokenizer {
    
    public static final String DEFAULT_TOKENFILTER = "fr.jdiaz.compiler.parser.tokenizer.DefaultTokenFilter";
    
    private static final Logger LOGGER = Logger.getLogger(TokenizerImpl.class);
    
    public void init(final Parser parser, Element n) {
        
        @SuppressWarnings("unchecked")
        List<Node> tokenNodes = n.selectNodes("//Token");
        
        for (Node token : tokenNodes) {
            Element tokenElement = (Element)token;
            String tokenName = tokenElement.attributeValue("name");
            String tokenClassName = tokenElement.attributeValue("class", DEFAULT_TOKENFILTER);
            String regex = tokenElement.getStringValue();
            
            try {
                Class<?> tokenFilterClass = Class.forName(tokenClassName);
                
                TokenFilter tf = (TokenFilter) tokenFilterClass.newInstance();
                

                if (StringUtils.isNotEmpty(regex)) {
                    tf.init(tokenName, regex);
                }
                else {
                    tf.init(tokenName);
                }
                
                tf.parserVisitor(parser);
                
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                LOGGER.error(e);
            }
            
        }
        
    }
    
    
}
