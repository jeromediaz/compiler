package fr.jdiaz.compiler.parser.reducers;

import java.util.Collections;
import java.util.List;

import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;
import fr.jdiaz.compiler.parser.TokenList;

public class TokenListFilterReducer extends NamedTokenReducer {
    
    private String mKind;
    
    @Override
    public void init(Parser p, Element e) {
        super.init(p, e);
        mKind = e.attributeValue("kind");
        
    }
    
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        if (input.isEmpty()) 
            return Collections.emptyList();
        
        Token firstToken = input.get(0);

        if (firstToken instanceof TokenList && mKind.equalsIgnoreCase(firstToken.getName())) {
            return Collections.singletonList(firstToken);
        }
        
        
        return Collections.emptyList();
    }
    

}
