package fr.jdiaz.compiler;

public class ScopeRange {
    private int mStartOfScope;
    private int mEndOfScope;
    private int mLevel;
    
    public ScopeRange(int start, int level) {
        mStartOfScope = start;
        mLevel = level;
    }
    
    public void setEndOfScope(int stop) {
        mEndOfScope = stop;
    }
    
    public int getStartOfScope() {
        return mStartOfScope;
    }
    
    public int getEndOfScope() {
        return mEndOfScope;
    }
    
    public int getLevel() {
        return mLevel;
    }
    
    @Override
    public String toString() {
        return String.format("%03d -> %03d (%d)", mStartOfScope, mEndOfScope, mLevel);
    }
    
}