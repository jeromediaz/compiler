package fr.jdiaz.compiler;

import fr.jdiaz.compiler.parser.Token;

public interface Instruction {
    
    public void init(Token token);
    public void processInstruction(ExecutionResult executionResult, ExecutionContext context);
    
}
