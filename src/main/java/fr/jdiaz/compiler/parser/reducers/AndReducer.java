package fr.jdiaz.compiler.parser.reducers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public class AndReducer extends CompositeTokenReducer {
    
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        List<Token> workTokenList = new ArrayList<>(input);
        
        List<Token> matchingTokens = new ArrayList<>();
        
        for (TokenReducer tokenFilter : this.getReducers()) {
            
            List<Token> matches = tokenFilter.tokenMatching(parser, workTokenList);
            
            if (CollectionUtils.isEmpty(matches)) {
                return Collections.emptyList();
            }
            
            matchingTokens.addAll(matches);
            workTokenList = workTokenList.subList(matches.size(), workTokenList.size());
            if (workTokenList.isEmpty()) {
                break;
            }
        }
        
        return matchingTokens;
    }
}
