package fr.jdiaz.compiler.parser.reducers;

public class ReducerId {
    
    private final String mId;
    private TokenReducer mReducer;
    
    public ReducerId(String id) {
        mId = id;
    }
    
    public void setReducer(TokenReducer reducer) {
        mReducer = reducer;
    }
    
    public TokenReducer getReducer() {
        return mReducer;
    }
    
    public String getId() {
        return mId;
    }
    
    
    
}
