package fr.jdiaz.compiler.parser.reducers;

import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public class OrReducer extends CompositeTokenReducer {
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        for (TokenReducer tokenFilter : this) {
            List<Token> tokens = tokenFilter.tokenMatching(parser, input);
            if (!CollectionUtils.isEmpty(tokens)) {
                return tokens;
            }
        }
        
        return Collections.emptyList();
    }

}
