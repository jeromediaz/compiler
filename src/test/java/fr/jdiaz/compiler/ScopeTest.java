package fr.jdiaz.compiler;

import org.junit.Assert;
import org.junit.Test;

public class ScopeTest {

    @Test
    public void testAccessToGlobalScope() {
        String myVarKey = "myVar";
        String myVarVal = "myVal";
        
        Scope globalScope = new Scope();
        Scope level1Scope = new Scope(globalScope);
        Scope level2Scope = new Scope(globalScope, level1Scope, false);
        
        globalScope.setVar(myVarKey, myVarVal);
        
        Assert.assertEquals("access in globalScope", myVarVal, globalScope.getVar(myVarKey));
        Assert.assertEquals("access in level1 scope", myVarVal, level1Scope.getVar(myVarKey));
        Assert.assertEquals("access in level2 scope", myVarVal, level2Scope.getVar(myVarKey));
        
    }
    
    @Test
    public void testScopeIsolation() {
        String myVarKey = "myVar";
        String myVarVal = "myVal";
        
        Scope globalScope = new Scope();
        Scope level1Scope = new Scope(globalScope);
        Scope level2Scope = new Scope(globalScope, level1Scope, true);
        
        level1Scope.setVar(myVarKey, myVarVal);
        
        Assert.assertEquals("access in globalScope", null, globalScope.getVar(myVarKey));
        Assert.assertEquals("access in level1 scope", myVarVal, level1Scope.getVar(myVarKey));
        Assert.assertEquals("access in level2 scope", null, level2Scope.getVar(myVarKey));
    }
    
    @Test
    public void testScopeConstants() {
        
    }
    
}
