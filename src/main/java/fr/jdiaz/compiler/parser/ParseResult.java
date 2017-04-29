package fr.jdiaz.compiler.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParseResult implements Iterable<Token> {

    public static final String MEASURE_INIT = "init";
    public static final String MEASURE_TOKENIZE = "tokenize";
    public static final String MEASURE_TOKENREDUCTION = "tokenreduction";
    
    private List<Token> mReducedTokens;
    private Map<String, Long> mElaspedTimes;
    private int mInitialTokenCount = 0;
    private long mTotalTime = 0L;
    
    public ParseResult() {
        mReducedTokens = new ArrayList<>();
        mElaspedTimes = new LinkedHashMap<>();
    }
    
    public ParseResult setReducedTokens(List<Token> pTokens) {
        mReducedTokens = new ArrayList<>(pTokens);
        return this;
    }
    
    public List<Token> getReducedTokens() {
        return mReducedTokens;
    }
    
    public ParseResult setInitialTokenCount(int initialTokenCount) {
        mInitialTokenCount = initialTokenCount;
        return this;
    }
    
    public int getInitialTokenCount() {
        return mInitialTokenCount;
    }
    
    public ParseResult setElaspedTimes(Map<String, Long> elapsedTimes) {
        mElaspedTimes = new LinkedHashMap<>(elapsedTimes);
        return this;
    }
    
    public Map<String, Long> getElapsedTimes() {
        return mElaspedTimes;
    }
    
    public ParseResult setTotalTime(long totalTime) {
        mTotalTime = totalTime;
        return this;
    }
    
    public long getTotalTime() {
        return mTotalTime;
    }
    
    @Override
    public Iterator<Token> iterator() {
        return mReducedTokens.iterator();
    }
    
}
