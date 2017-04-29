package fr.jdiaz.compiler.parser.reducers;

import java.util.List;

import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;
import fr.jdiaz.compiler.parser.Token;

public interface TokenReducer {
    
    public void init(Parser p, Element e);
    
    public List<Token> tokenMatching(Parser parser, List<Token> input);
    
    public String getReducerName();
    
    
}
