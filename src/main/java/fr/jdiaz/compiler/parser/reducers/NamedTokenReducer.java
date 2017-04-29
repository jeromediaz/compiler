package fr.jdiaz.compiler.parser.reducers;

import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;

public abstract class NamedTokenReducer implements TokenReducer {

    private String mName;
    
    @Override
    public void init(Parser p, Element e) {
        mName = e.getParent().attributeValue("name", "N/A");
        
    }
    
    @Override
    public String getReducerName() {
        return mName;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", mName, this.getClass().getName());
    }
}
