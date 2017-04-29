package fr.jdiaz.compiler.parser.reducers;

import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;

public class OptReducer extends RepetitionReducer implements TokenReducer {
    
    @Override
    public void init(Parser p, Element e) {
        super.init(p, e);
        this.setMinValue(0);
        this.setMaxValue(1);
    }
    
}
