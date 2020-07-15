package u9;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueue<T> implements Queue<T>, Iterable<T> {
    public class QueueIterator<E extends T> implements Iterator<T> {

        final ArrayQueue aqueue;
        int index;

        private QueueIterator(ArrayQueue aqueue) {
            this.aqueue = aqueue;
        }

        @Override
        public boolean hasNext() {
            return aqueue.hasElementAt(index);
        }

        @Override
        public T next() {
            return (T) aqueue.elementAt(index++);
        }
    }

    private int head;
    private int tail;
    private T[] elements;
    private Object lock;

    public ArrayQueue(T[] init) {
        head = 0;
        elements = Arrays.copyOf(init, init.length << 1);
        tail = init.length;
        lock = new Object();
    }

    public ArrayQueue(int initialSize) {
        head = 0;
        tail = 0;
        elements = (T[]) (new Object[initialSize]);
        lock = new Object();
    }

    public ArrayQueue() {
        this(16);
    }

    public ArrayQueue(Collection<T> coll) {
        this(coll.size() << 1);
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
        T[] unwrapped = toUnwrapped(size);
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
        tail++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(elementAt(i), o)) {
                elements = toUnwrapped(elements.length);
                System.arraycopy(elements, i, elements, i + 1, size() - i);
                tail--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        var s = Set.of(Arrays.copyOfRange(toUnwrapped(elements.length), 0, size()));
        return s.containsAll(collection);
    }

    // TODO If necessary
    @Override
    public boolean addAll(@NotNull Collection<? extends T> collection) {
        for (T e : collection) {
            elements[tail] = e;
            tail++;
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
        if (isEmpty()) throw new EmptyQueueException();
        return elements[head++];
    }

    @Override
    public T poll() {
        if (isEmpty()) throw new EmptyQueueException();
        return elements[head++];
    }

    @Override
    public T element() {
        if (isEmpty()) throw new EmptyQueueException();
//        if (isEmpty()) throw new NoSuchElementException();
        return (T) elements[head];
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new EmptyQueueException();
        return (T) elements[head];
    }

    private void resize() {
        elements = toUnwrapped(elements.length << 1);
    }

    private boolean isWrapped() {
        return tail < head;
    }

    private T elementAt(int n) {
        int index = (head + n >= elements.length) ? n - head : n + head;
        return (T) elements[index];
    }

    private boolean hasElementAt(int n) {
        if (isWrapped()) {
            return n < tail || n >= head;
        } else {
            return n >= head && n < tail;
        }
    }

    private T[] toUnwrapped(int newSize) {
        T[] dest;
        synchronized (lock) {
            dest = (T[]) Arrays.copyOf(elements, newSize, elements.getClass());
            if (isWrapped()) {
                System.arraycopy(elements, head, dest, 0, elements.length - head);
                System.arraycopy(elements, 0, dest, elements.length - head, tail);
            } else {
                System.arraycopy(elements, 0, dest, 0, tail);
            }
        }
        return dest;
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> aqueue = new ArrayQueue<>(new Integer[]{1, 2, 3, 4, 5, 6});
        System.out.println(aqueue);
    }
}
