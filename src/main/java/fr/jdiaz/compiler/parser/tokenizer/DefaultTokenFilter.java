package fr.jdiaz.compiler.parser.tokenizer;

import java.util.regex.Pattern;

import fr.jdiaz.compiler.parser.Parser;

public class DefaultTokenFilter implements TokenFilter {
    
    private String mFilterName = null;
    private Pattern mFilterPattern = null;
    
    
    @Override
    public void init(String filterName) {
        init(filterName, null);
    }

    @Override
    public void init(String name, String pattern) {
        mFilterName = name;
        mFilterPattern = pattern == null ? null : Pattern.compile(pattern);
    
    }
    
    @Override
    public Pattern getPattern() {
        return mFilterPattern;
    }
    
    @Override
    public String getFilterName() {
        return mFilterName;
    }
    
    public boolean matches(String text) {
        if (mFilterPattern == null) {
            return true;
        }
        return mFilterPattern.matcher(text).matches();
    }
    
    @Override
    public void parserVisitor(Parser p) {
        p.addTokenFilter(this);
    }
    
    
}
