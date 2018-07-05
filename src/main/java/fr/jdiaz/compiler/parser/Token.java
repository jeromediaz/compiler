package fr.jdiaz.compiler.parser;

public class Token {
    
    private final long mIndex;
    private final long mLength;
    private final String mTokenContent;
    private final String mName;
    
    
    public Token(String token, long index, long length, String name) {
        mTokenContent = token;
        mIndex = index;
        mLength = length;
        mName = name;
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
    
    
    public String getName() {
        return mName;
    }
    
}
