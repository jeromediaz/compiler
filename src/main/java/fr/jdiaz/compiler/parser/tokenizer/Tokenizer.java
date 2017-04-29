package fr.jdiaz.compiler.parser.tokenizer;

import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;

public interface Tokenizer {

    public void init(final Parser parser, Element n);
    
}
