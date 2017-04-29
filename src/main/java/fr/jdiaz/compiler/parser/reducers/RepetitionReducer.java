package fr.jdiaz.compiler.parser.reducers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public class RepetitionReducer extends NamedTokenReducer {
    
    private static final Logger LOGGER = Logger.getLogger(RepetitionReducer.class);
    
    private int mMinRepeat = 0;
    private int mMaxRepeat = -1;
    
    private TokenReducer mTokenReducer;
    
    @Override
    public void init(Parser p, Element e) {
        super.init(p, e);
        
        String minRepeat = e.attributeValue("min", "0");
        String maxRepeat = e.attributeValue("max", "-1");
        
        mMinRepeat = Integer.parseInt(minRepeat);
        mMaxRepeat = Integer.parseInt(maxRepeat);
        
        Element eSelement = (Element) e.elements().get(0);
        
        mTokenReducer = p.createTokenReducer(eSelement);
        
        if (mTokenReducer == null) {
            LOGGER.error("nullTokenReducer " + e.asXML());
        }
    }
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        List<Token> matchedTokens = new ArrayList<>();
        List<Token> workTokens = new ArrayList<>(input);
        
        int matchedRepeat = -1;
        List<Token> addedTokens;
        do {
            addedTokens = mTokenReducer.tokenMatching(parser, workTokens);
            if (!CollectionUtils.isEmpty(addedTokens)) {
                workTokens = workTokens.subList(addedTokens.size(), workTokens.size());
                matchedTokens.addAll(addedTokens);
            }
            matchedRepeat++;
        } while(!CollectionUtils.isEmpty(addedTokens));
        
        if (matchedRepeat >= mMinRepeat && (matchedRepeat <= mMaxRepeat || mMaxRepeat == -1)) {
            return matchedTokens;
        }
        
        return Collections.emptyList();
    }

    public void setMinValue(int min) {
        mMinRepeat = min;
    }
    
    public void setMaxValue(int max) {
        mMaxRepeat = max;
    }
    
}
