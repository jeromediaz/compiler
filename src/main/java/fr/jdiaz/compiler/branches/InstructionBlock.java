package fr.jdiaz.compiler.branches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class InstructionBlock extends InstructionRange implements List<InstructionBranch> {
    
    private final BlockKind mKind;
    private final InstructionBlock mParentBlock;
    private final List<InstructionBranch> mBranches;
    
    public InstructionBlock(BlockKind kind, int startIndex) {
        this(kind, null, startIndex);
    }
    
    public InstructionBlock(BlockKind kind, InstructionBlock parent, int startIndex) {
        super(startIndex);
        mParentBlock = parent;
        mKind = kind;
        mBranches = new ArrayList<>();
    }
    
    public BlockKind getBlockKind() {
        return mKind;
    }
    
    public InstructionBlock getParentBlock() {
        return mParentBlock;
    }

    @Override
    public int size() {
        return mBranches.size();
    }

    @Override
    public boolean isEmpty() {
        return mBranches.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mBranches.contains(o);
    }

    @Override
    public Iterator<InstructionBranch> iterator() {
        return mBranches.iterator();
    }

    @Override
    public Object[] toArray() {
        return mBranches.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return mBranches.toArray(a);
    }

    @Override
    public boolean add(InstructionBranch e) {
        return mBranches.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return mBranches.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mBranches.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends InstructionBranch> c) {
        return mBranches.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends InstructionBranch> c) {
        return mBranches.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return mBranches.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mBranches.retainAll(c);
    }

    @Override
    public void clear() {
        mBranches.clear();
    }

    @Override
    public InstructionBranch get(int index) {
        return mBranches.get(index);
    }

    @Override
    public InstructionBranch set(int index, InstructionBranch element) {
        return mBranches.set(index, element);
    }

    @Override
    public void add(int index, InstructionBranch element) {
        mBranches.add(index, element);
    }

    @Override
    public InstructionBranch remove(int index) {
        return mBranches.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return mBranches.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mBranches.lastIndexOf(o);
    }

    @Override
    public ListIterator<InstructionBranch> listIterator() {
        return mBranches.listIterator();
    }

    @Override
    public ListIterator<InstructionBranch> listIterator(int index) {
        return mBranches.listIterator(index);
    }

    @Override
    public List<InstructionBranch> subList(int fromIndex, int toIndex) {
        return mBranches.subList(fromIndex, toIndex);
    }

    public InstructionBranch lastBranch() {
        if (!mBranches.isEmpty()) {
            return mBranches.get(mBranches.size() - 1);
        }
        
        return null;
    }
    
    
    
    
    
}
