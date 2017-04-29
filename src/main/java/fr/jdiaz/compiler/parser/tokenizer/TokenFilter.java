package fr.jdiaz.compiler.parser.tokenizer;

import java.util.regex.Pattern;

import fr.jdiaz.compiler.parser.Parser;

public interface TokenFilter {
    
    public void parserVisitor(Parser p);
    public String getFilterName();
    
    public void init(String filterName);
    public void init(String filterName, String regex);
    
    public Pattern getPattern();
}
