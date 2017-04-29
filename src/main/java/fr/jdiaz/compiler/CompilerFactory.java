package fr.jdiaz.compiler;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CompilerFactory {
    
    private static CompilerFactory sInstance = null;
    
    private Cache<URI, Compiler> mUriToCompiler = null;
    
    private CompilerFactory() {
        mUriToCompiler = CacheBuilder.newBuilder().build();
    }
    
    public static synchronized CompilerFactory getInstance() {
        if (sInstance == null) {
            sInstance = new CompilerFactory();
        }
        return sInstance;
    }
    
    public synchronized Compiler getCompiler(final URI compilerUri) throws ExecutionException {
        return mUriToCompiler.get(compilerUri, new Callable<Compiler>() {

            @Override
            public Compiler call() throws Exception {
                return new Compiler(compilerUri);
            }
            
        });
    }
    
}
