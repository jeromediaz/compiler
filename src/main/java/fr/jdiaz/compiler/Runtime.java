package fr.jdiaz.compiler;

import fr.jdiaz.compiler.branches.InstructionBranch;

public class Runtime {
    
    private Runtime() {
        // to hide constructor
    }
    
    public static ExecutionResult run(Bytecode bc, Scope ctx) {
        
        ExecutionResult er = new ExecutionResult();
        
        int instructionCount = bc.instructionSize();
        
        ExecutionContext context = new ExecutionContext();
        context.setScope(ctx);
        
        int instructionPosition = context.getCurrentLine();
        
        do {
            Instruction worker = bc.getInstruction(instructionPosition);
            
            InstructionBranch branchBefore = bc.getInstructionBranch(instructionPosition);
            InstructionBranch branchAfter = bc.getInstructionBranch(instructionPosition + 1);
            
            context.setInstructionBranchBefore(branchBefore);
            context.setInstructionBranchAfter(branchAfter);
            
            worker.processInstruction(er, context);
            instructionPosition = context.getCurrentLine();
            
        } while(instructionPosition < instructionCount);
        
        
        return er;
    }
    
}
