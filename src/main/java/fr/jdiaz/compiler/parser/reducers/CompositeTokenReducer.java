package fr.jdiaz.compiler.parser.reducers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Element;

import fr.jdiaz.compiler.parser.Parser;

public abstract class CompositeTokenReducer extends NamedTokenReducer implements List<TokenReducer>, Iterable<TokenReducer> {
    
    private List<TokenReducer> mReducers;
    
    
    public List<TokenReducer> getReducers() {
        return mReducers;
    }
    
    @Override
    public int size() {
        return mReducers.size();
    }

    @Override
    public boolean isEmpty() {
        return mReducers.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mReducers.contains(o);
    }

    @Override
    public Iterator<TokenReducer> iterator() {
        return mReducers.iterator();
    }

    @Override
    public Object[] toArray() {
        return mReducers.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return mReducers.toArray(a);
    }

    @Override
    public boolean add(TokenReducer e) {
        return mReducers.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return mReducers.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mReducers.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends TokenReducer> c) {
        return mReducers.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends TokenReducer> c) {
        return mReducers.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return mReducers.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mReducers.retainAll(c);
    }

    @Override
    public void clear() {
        mReducers.clear();
    }

    @Override
    public TokenReducer get(int index) {
        return mReducers.get(index);
    }

    @Override
    public TokenReducer set(int index, TokenReducer element) {
        return mReducers.set(index, element);
    }

    @Override
    public void add(int index, TokenReducer element) {
        mReducers.add(index, element);
    }

    @Override
    public TokenReducer remove(int index) {
        return mReducers.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return mReducers.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mReducers.lastIndexOf(o);
    }

    @Override
    public ListIterator<TokenReducer> listIterator() {
        return mReducers.listIterator();
    }

    @Override
    public ListIterator<TokenReducer> listIterator(int index) {
        return mReducers.listIterator(index);
    }

    @Override
    public List<TokenReducer> subList(int fromIndex, int toIndex) {
        return mReducers.subList(fromIndex, toIndex);
    }
    

    @Override
    public void init(Parser p, Element e) {
        super.init(p,  e);
        mReducers = new ArrayList<>();
        
        @SuppressWarnings("rawtypes")
        List subElements = e.elements();
        
        for (Object subObject : subElements) {
            if (subObject instanceof Element) {
                TokenReducer tokenReducer = p.createTokenReducer((Element)subObject);
                mReducers.add(tokenReducer);
            }
            
        }
        
    }
}
