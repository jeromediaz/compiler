package fr.jdiaz.compiler;

import java.util.ArrayList;
import java.util.List;

import fr.jdiaz.compiler.branches.InstructionBranch;

public class Bytecode {
    
    private List<Instruction> mInstructions = null;
    private List<InstructionBranch> mInstructionBranches = null;
    
    public Bytecode() {
        mInstructions = new ArrayList<>();
        mInstructionBranches = new ArrayList<>();
    }
    
    public void addInstruction(Instruction instruction) {
        mInstructions.add(instruction);
    }
    
    public void addInstructionBranch(InstructionBranch branch) {
        mInstructionBranches.add(branch);
    }
        
    public int instructionSize() {
        return mInstructions.size();
    }
    
    public Instruction getInstruction(int index) {
        return mInstructions.get(index);
    }
    
    public InstructionBranch getInstructionBranch(int index) {
        return mInstructionBranches.get(index);
    }
    
}
