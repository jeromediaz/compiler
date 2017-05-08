package fr.jdiaz.compiler;

import fr.jdiaz.compiler.branches.InstructionBranch;

public class ExecutionContext {
    
    private Scope mScope;
    private int mCurrentLine = 0;
    private int mPreviousLine = 1;
    
    private InstructionBranch mInstructionBranchBefore;
    private InstructionBranch mInstructionBranchAfter;
    
    
    public Scope getScope() {
        return mScope;
    }
    
    public void setScope(Scope mScope) {
        this.mScope = mScope;
    }
    
    public int getCurrentLine() {
        return this.mCurrentLine;
    }
    
    public void setCurrentLine(int pCurrentLine) {
        mPreviousLine = mCurrentLine;
        mCurrentLine = pCurrentLine;
    }
    
    public int getPreviousLine() {
        return mPreviousLine;
    }
    
    public int incrementCurrentLine() {
        mPreviousLine = mCurrentLine;
        return ++mCurrentLine;
    }
    
    public InstructionBranch getInstructionBranchBefore() {
        return mInstructionBranchBefore;
    }

    public void setInstructionBranchBefore(InstructionBranch mInstructionBranchBefore) {
        this.mInstructionBranchBefore = mInstructionBranchBefore;
    }

    public InstructionBranch getInstructionBranchAfter() {
        return mInstructionBranchAfter;
    }

    public void setInstructionBranchAfter(InstructionBranch mInstructionBranchAfter) {
        this.mInstructionBranchAfter = mInstructionBranchAfter;
    }
    
    
    public Scope popScope() {
        mScope = mScope.parentScope();
        return mScope;
    }
    
    public Scope newScope(boolean isolate) {
        mScope = mScope.newScope(isolate);
        return mScope;
    }
    
}
