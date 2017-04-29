package fr.jdiaz.compiler.parser;

public class Token {
    
    private final long mIndex;
    private final long mLength;
    private final String mTokenContent;
    private final String mFilterName;
    
    
    public Token(String token, long index, long length, String filterName) {
        mTokenContent = token;
        mIndex = index;
        mLength = length;
        mFilterName = filterName;
    }
    
    public Token(String token, long index, long length) {
        this(token, index, length, null);
    }
    
    public long getIndex() {
        return mIndex;
    }
    
    public long getLength() {
        return mLength;
    }
    
    public String getToken() {
        return mTokenContent;
    }
    
    public String getFilterName() {
        return mFilterName;
    }
    
    public String getName() {
        return mFilterName;
    }
    
}
