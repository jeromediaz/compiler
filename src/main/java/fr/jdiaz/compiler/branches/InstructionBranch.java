package fr.jdiaz.compiler.branches;

public class InstructionBranch extends InstructionRange {
    
    private final BranchKind mKind;
    private final InstructionBlock mContainingBlock;
    
    public InstructionBranch(BranchKind kind, int startIndex) {
        this(kind, null, startIndex);
    }
    
    public InstructionBranch(BranchKind kind, InstructionBlock parent, int startIndex) {
        super(startIndex);
        mContainingBlock = parent;
        mKind = kind;
        if (parent != null) {
            parent.add(this);
        }
    }
    
    public BranchKind getBranchKind() {
        return mKind;
    }
    
    public InstructionBlock getContainingBlock() {
        return mContainingBlock;
    }
    
    @Override
    public InstructionBranch setLastIndex(int lastIndex) {
        super.setLastIndex(lastIndex);
        mContainingBlock.setLastIndex(lastIndex);
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("InstructionBranch %s", mKind.name());
    }
    
}
