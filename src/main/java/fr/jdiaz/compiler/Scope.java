package fr.jdiaz.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;

public class Scope implements Bindings {
    
    private Map<String, Object> mContent;
    private Set<String> mConstants;
    private Scope mParentScope;
    private Scope mGlobalScope;
    private boolean mIsIsolated = false;
    

    public Scope() {
        this(null);
        this.mGlobalScope = this;
    }
    
    protected Scope(Scope globalScope) {
        this(globalScope, null, false);
    }
    
    protected Scope(Scope globalScope, Scope parentScope, boolean isolated) {
        mIsIsolated = isolated;
        mGlobalScope = globalScope;
        mParentScope = parentScope;
        
        mContent = new HashMap<>();
        mConstants = new HashSet<>();
        
    }
    
    
    public Scope newScope(boolean isolate) {
        return new Scope(this.getGlobalScope(), this, isolate);
    }
    
    public Scope newScope() {
        return newScope(false);
    }
    
    public Scope parentScope() {
        return mParentScope;
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
    
    public Scope getGlobalScope() {
        return mGlobalScope;
    }
    
    public Scope getScopeContainingVar(String name) {
        if (this == mGlobalScope) {
            // we are global scope
            if (mContent.containsKey(name) || mConstants.contains(name)) {
                return this;
            }
            return null;
        }
        
        if (mContent.containsKey(name) || mConstants.contains(name)) {
            return this;
        }
        
        if (!mIsIsolated && mParentScope != mGlobalScope && mParentScope != null) {
            return mParentScope.getScopeContainingVar(name);
        }
        
        return mGlobalScope.getScopeContainingVar(name);
    }
    
    public Object getVar(String name) {
        Scope containingScope = this.getScopeContainingVar(name);
        if (containingScope != null) {
            return containingScope.get(name);
        }
        
        return null;
    }
    
    public void setVar(String name, Object value) {
        Scope containingScope = this.getScopeContainingVar(name);
        
        if (containingScope != null) {
            containingScope.put(name, value);
        } else {
            mContent.put(name, value);
        }
    }
    
    public void defineVar(String name, Object value) {
        if (!mConstants.contains(name) || !mContent.containsKey(name)) {
            mContent.put(name, value);
        }
    }
    
    public void defineConst(String name, Object value) {
        defineVar(name, value);
        mContent.put(name, value);
    }

}
