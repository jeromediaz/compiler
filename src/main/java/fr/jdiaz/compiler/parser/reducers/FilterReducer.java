package fr.jdiaz.compiler.parser.reducers;

import java.util.Collections;
import java.util.List;

import org.dom4j.Element;
import org.springframework.util.CollectionUtils;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public class FilterReducer extends NamedTokenReducer {
    
    private String mReducerId;
    
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        TokenReducer reducer = parser.getNamedReducer(mReducerId);
        
        if (!CollectionUtils.isEmpty(input) && reducer != null) {
            return reducer.tokenMatching(parser, input);
        }
        
        return Collections.emptyList();
    }

    @Override
    public void init(Parser p, Element e) {
        super.init(p, e);
        mReducerId = e.attributeValue("ref");
        
    }
    
}
