package fr.jdiaz.compiler;

import java.io.IOException;

public class ExecutionResult implements Appendable {
    
    private StringBuilder mBuilder;
    
    public ExecutionResult() {
        mBuilder = new StringBuilder();
    }
    
    public ExecutionResult append(String str) {
        mBuilder.append(str);
        return this;
    }

    @Override
    public ExecutionResult append(CharSequence csq) throws IOException {
        mBuilder.append(csq);
        return this;
    }

    @Override
    public ExecutionResult append(CharSequence csq, int start, int end) throws IOException {
        mBuilder.append(csq, start, end);
        return this;
    }

    @Override
    public ExecutionResult append(char c) throws IOException {
        mBuilder.append(c);
        return this;
    }
    
    public StringBuilder getStringBuilder() {
        return mBuilder;
    }
    
    public ExecutionResult setStringBuilder(StringBuilder builder) {
        mBuilder = builder;
        return this;
    }
    
    public String getResult() {
        return mBuilder.toString();
    }
    
}
