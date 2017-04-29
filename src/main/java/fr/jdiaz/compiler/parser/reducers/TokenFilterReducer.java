package fr.jdiaz.compiler.parser.reducers;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public class TokenFilterReducer extends NamedTokenReducer {
    
    private String mKind;
    private String mTokenContent;
    
    
    @Override
    public void init(Parser p, Element e) {
        super.init(p, e);
        mKind = e.attributeValue("kind");
        
        String content = e.getText();
        if (StringUtils.isEmpty(content)) {
            mTokenContent = null;
        }
        else {
            mTokenContent = content;
        }
    }
    
    
    @Override
    public List<Token> tokenMatching(Parser parser, List<Token> input) {
        if (input.isEmpty()) 
            return Collections.emptyList();
        Token firstToken = input.get(0);
        String firstTokenName = firstToken.getName();
        if (mTokenContent != null ) {
        
            if (mKind.equals(firstTokenName)) {
                Pattern mTokenPattern = Pattern.compile(mTokenContent);
                Matcher m = mTokenPattern.matcher(firstToken.getToken());
                if (m.matches()) {
                    return Collections.singletonList(firstToken);
                }
            }
        }
        else if (this.mKind.equals(firstTokenName)) {
            return Collections.singletonList(firstToken);
        }
        
        return Collections.emptyList();
    }
    

}
