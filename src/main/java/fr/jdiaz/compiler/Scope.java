package fr.jdiaz.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.jdiaz.compiler.branches.InstructionBranch;

public class Scope implements Map<String, Object> {
    
    private int mCurrentLine = 0;
    private int mPreviousLine = -1;
    private Map<String, Object> mContent;
    private Set<String> mConstants;
    private Map<String, Scope> mScopeContaining;
    private Scope mParentScope;
    private Scope mGlobalScope;
    private boolean mIsIsolated = false;
    
    private InstructionBranch mInstructionBranchBefore = null;
    private InstructionBranch mInstructionBranchAfter = null;
    
    public Scope() {
        this(null);
        this.mGlobalScope = this;
    }
    
    protected Scope(Scope rootScope) {
        this(rootScope, null, false);
    }
    
    protected Scope(Scope rootScope, Scope parentScope, boolean isolated) {
        mIsIsolated = isolated;
        mGlobalScope = rootScope;
        mParentScope = parentScope;
        
        mContent = new HashMap<>();
        mScopeContaining = new HashMap<>();
        
        if (parentScope != null) {
            mCurrentLine = parentScope.getCurrentLine();
            mPreviousLine = parentScope.getPreviousLine();
            
            mScopeContaining.putAll(parentScope.mScopeContaining);
        }
        
    }
    
    
    public Scope newScope(boolean isolate) {
        Scope newContext = new Scope(this.getRootScope(), this, isolate);
        
        newContext.setCurrentLine(this.getCurrentLine());
        return newContext;
    }
    
    public Scope newScope() {
        return newScope(false);
    }
    
    public Scope parentScope() {
        return mParentScope;
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

    @Override
    public int size() {
        return mContent.size();
    }

    @Override
    public boolean isEmpty() {
        return mContent.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mContent.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mContent.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return mContent.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return mContent.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return mContent.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        mContent.putAll(m);
    }

    @Override
    public void clear() {
        mContent.clear();
    }

    @Override
    public Set<String> keySet() {
        return mContent.keySet();
    }

    @Override
    public Collection<Object> values() {
        return mContent.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return mContent.entrySet();
    }
    
    public Scope getRootScope() {
        return mGlobalScope;
    }
    
    public void defineVar(String name, Object value) {
        
    }
    
    public void defineConst(String name, Object value) {
        
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
    
    
}
