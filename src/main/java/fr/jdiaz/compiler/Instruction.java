package fr.jdiaz.compiler;

import fr.jdiaz.compiler.parser.Token;

public interface Instruction {
    
    public void init(Token token);
    public Scope processInstruction(ExecutionResult executionResult, Scope scope);
    
}
