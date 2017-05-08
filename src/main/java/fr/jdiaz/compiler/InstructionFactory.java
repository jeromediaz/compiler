package fr.jdiaz.compiler;

import fr.jdiaz.compiler.branches.InstructionBranch;
import fr.jdiaz.compiler.parser.Token;

public interface InstructionFactory {
    
    public Instruction instantiate(Token token);
    
    public InstructionBranch getBranch(InstructionBranch branch, int index);

}
