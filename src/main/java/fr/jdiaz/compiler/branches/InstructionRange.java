package fr.jdiaz.compiler.branches;

public abstract class InstructionRange {

    private int mStartIndex = 0;
    private int mLastIndex = 0;
    
    public InstructionRange(int startIndex) {
        mStartIndex = startIndex;
    }
    
    public int getStartIndex() {
        return mStartIndex;
    }
    
    public InstructionRange setStartIndex(int startIndex) {
        mStartIndex = startIndex;
        return this;
    }
    
    public int getLastIndex() {
        return mLastIndex;
    }
    
    public InstructionRange setLastIndex(int lastIndex) {
        mLastIndex = lastIndex;
        return this;
    }
    
}
