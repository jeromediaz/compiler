package fr.jdiaz.compiler.parser.tokenizer;

import fr.jdiaz.compiler.parser.Parser;

public class LetterTokenFilter extends DefaultTokenFilter {

    @Override
    public void parserVisitor(Parser p) {
        super.parserVisitor(p);
        
        DefaultTokenFilter hexaTokenFilter = new DefaultTokenFilter();
        hexaTokenFilter.init("letters_hexa", "^[a-fA-F]");
        p.addTokenFilter(hexaTokenFilter);
    }
    
}
