package fr.jdiaz.compiler.parser.reducers;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public class NotReducer extends NamedTokenReducer {

    private TokenReducer mReducer;
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        if (!input.isEmpty()) {
            Token firstToken = input.get(0);
            List<Token> matchedTokens = mReducer.tokenMatching(parser, input);
            if (CollectionUtils.isEmpty(matchedTokens)) {
                return Collections.singletonList(firstToken);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void init(Parser p, Element e) {
        super.init(p, e);
        
        mReducer = null;
        
        @SuppressWarnings("rawtypes")
        List subElements = e.elements();
        
        for (Object subObject : subElements) {
            if (subObject instanceof Element) {
                Element subElement = (Element)subObject;
                mReducer = p.createTokenReducer(subElement);
            }
            
        }
        
    }

}
