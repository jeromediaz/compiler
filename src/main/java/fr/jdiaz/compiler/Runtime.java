package fr.jdiaz.compiler;

import fr.jdiaz.compiler.branches.InstructionBranch;

public class Runtime {
    
    private Runtime() {
        // to hide constructor
    }
    
    public static ExecutionResult run(Bytecode bc, Scope ctx) {
        
        ExecutionResult er = new ExecutionResult();
        
        int instructionCount = bc.instructionSize();
        int instructionPosition = ctx.getCurrentLine();
        
        Scope workContext = ctx;
        do {
            Instruction worker = bc.getInstruction(instructionPosition);
            int currentLine = workContext.getCurrentLine();
            InstructionBranch branchBefore = bc.getInstructionBranch(currentLine);
            InstructionBranch branchAfter = bc.getInstructionBranch(currentLine + 1);
            workContext.setInstructionBranchBefore(branchBefore);
            workContext.setInstructionBranchAfter(branchAfter);
            workContext = worker.processInstruction(er, workContext);
            instructionPosition = workContext.getCurrentLine();
            
        } while(instructionPosition < instructionCount);
        
        
        return er;
    }
    
}
