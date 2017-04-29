package fr.jdiaz.compiler.parser;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TokenList extends Token implements List<Token> {
    
    private String mName = null;
    private List<Token> mInternalList = null;
    
    protected TokenList(List<Token> tokens, String token, long index, long length, String name) {
        super(token, index, length);
        mInternalList = tokens;
        mName = name;
    }
    
    public static TokenList instantiate(List<Token> tokens, String name) {
        Token firstToken = tokens.get(0);
        Token lastToken = tokens.get(tokens.size() - 1);
        
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(token.getToken());
        }
        
        long length = lastToken.getIndex() + lastToken.getLength();
        return new TokenList(tokens, sb.toString(), firstToken.getIndex(), length, name);
    }
    
    @Override
    public String getName() {
        return mName;
    }
    
    @Override
    public int size() {
        return mInternalList.size();
    }

    @Override
    public boolean isEmpty() {
        return mInternalList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mInternalList.contains(o);
    }

    @Override
    public Iterator<Token> iterator() {
        return mInternalList.iterator();
    }

    @Override
    public Object[] toArray() {
        return mInternalList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return mInternalList.toArray(a);
    }

    @Override
    public boolean add(Token e) {
        return mInternalList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return mInternalList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mInternalList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Token> c) {
        return mInternalList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Token> c) {
        return mInternalList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return mInternalList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mInternalList.retainAll(c);
    }

    @Override
    public void clear() {
        mInternalList.clear();
    }

    @Override
    public Token get(int index) {
        return mInternalList.get(index);
    }

    @Override
    public Token set(int index, Token element) {
        return mInternalList.set(index, element);
    }

    @Override
    public void add(int index, Token element) {
        mInternalList.add(index, element);
    }

    @Override
    public Token remove(int index) {
        return mInternalList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return mInternalList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mInternalList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Token> listIterator() {
        return mInternalList.listIterator();
    }

    @Override
    public ListIterator<Token> listIterator(int index) {
        return mInternalList.listIterator(index);
    }

    @Override
    public List<Token> subList(int fromIndex, int toIndex) {
        return mInternalList.subList(fromIndex, toIndex);
    }
    
}
