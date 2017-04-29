package fr.jdiaz.compiler.parser;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ParserFactory {
    
    private static ParserFactory sInstance = null;
    
    private Cache<URI, Parser> mUriToParser = null;
    
    private ParserFactory() {
        mUriToParser = CacheBuilder.newBuilder().build();
    }
    
    public static synchronized ParserFactory getInstance() {
        if (sInstance == null) {
            sInstance = new ParserFactory();
        }
        return sInstance;
    }
    
    public synchronized Parser getParser(final URI parserUri) throws ExecutionException {
        return mUriToParser.get(parserUri, new Callable<Parser>() {

            @Override
            public Parser call() throws Exception {
                return new Parser(parserUri);
            }
            
        });
    }
    
}
