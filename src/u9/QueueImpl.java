package u9;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class QueueImpl<T> implements Queue<T>, Iterable<T> {
    public class QueueIterator<E extends T> implements Iterator<T> {

        @Override
        public boolean hasNext() {

            return false;
        }

        @Override
        public T next() {
            // TODO
            return null;
        }
    }

    private int head;
    private int tail;
    private T[] elements;

    public QueueImpl(T[] init) {
        elements = Arrays.copyOf(init, init.length << 2);
    }

    public QueueImpl(int initialSize) {
        head = 0;
        tail = 0;
        elements = (T[]) (new Object[initialSize]);
    }

    public QueueImpl() {
        this(16);
    }

    public QueueImpl(Collection<T> coll) {
        this(coll.size() << 2);
        addAll(coll);
    }

    @Override
    public int size() {
        return isWrapped() ? elements.length + head - tail : tail - head;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean contains(Object o) {
        return Arrays.stream(elements).anyMatch(e -> Objects.equals(e, o));
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        // TODO
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] out = new Object[size()];
        if (isWrapped()) {
            System.arraycopy(elements, head, out, 0, elements.length - head);
            System.arraycopy(elements, 0, out, elements.length - head, tail);
        } else {
            System.arraycopy(elements, 0, out, 0, tail);
        }
        return out;
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] t1s) {
        int size = size();
        T[] unwrapped = toUnwrapped();
        if (t1s.length < size) {
            return (T1[]) Arrays.copyOf(unwrapped, size, t1s.getClass());
        } else {
            System.arraycopy(unwrapped, 0, t1s, 0, size);
            if (t1s.length > size) {
                t1s[size] = null;
            }
            return t1s;
        }
    }

    @Override
    public boolean add(T t) {
        elements[tail] = t;
        tail ++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        // TODO
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return Arrays.stream(elements).allMatch(collection::contains);
    }

    // TODO If necessary
    @Override
    public boolean addAll(@NotNull Collection<? extends T> collection) {
        for (T e: collection) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        // TODO
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        // TODO
        return false;
    }

    @Override
    public void clear() {
        head = 0;
        tail = 0;
    }

    @Override
    public boolean offer(T t) {
        return add(t);
    }

    @Override
    public T remove() {
        // TODO
        return null;
    }

    @Override
    public T poll() {
        return elements[head++];
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return (T) elements[head];
    }

    @Override
    public T peek() {
        return (T) elements[head];
    }

    private void resize() {
        // TODO

    }

    private boolean isWrapped() {
        return tail < head;
    }

    private T elementAt(int n) {
        int index = (head + n >= elements.length) ? n - head : n + head;
        return (T) elements[index];
    }

    private T[] toUnwrapped() {
        T[] dest = (T[]) Arrays.copyOf(elements, size(), elements.getClass());
        if (isWrapped()) {
            System.arraycopy(elements, head, dest, 0, elements.length - head);
            System.arraycopy(elements, 0, dest, elements.length - head, tail);
        } else {
            System.arraycopy(elements, 0, dest, 0, tail);
        }
        return dest;
    }
}
