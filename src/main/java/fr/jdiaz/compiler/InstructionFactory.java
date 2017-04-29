package fr.jdiaz.compiler;

import fr.jdiaz.compiler.branches.InstructionBranch;
import fr.jdiaz.compiler.parser.Token;

public interface InstructionFactory {
    
    public boolean willPopContext();
    public boolean willPushContext();
    
    public boolean willCreateConditionBranch();
    public boolean willStartConditionBlock();
    public boolean willEndConditionBlock();
    
    public Instruction instantiate(Token token);
    
    public InstructionBranch getBranch(InstructionBranch branch, int index);

}
