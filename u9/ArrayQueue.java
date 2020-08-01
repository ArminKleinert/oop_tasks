package u9;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArrayQueue<T> implements Queue<T>, Iterable<T> {

    public static final class QueueIterator<T> implements Iterator<T> {

        private final ArrayQueue<T> queue;
        private int index = 0;

        // final because there is no reason to update these,
        // since remove() will not be implemented.
        private final int oldHead;
        private final int oldTail;
        private final int oldSize;

        public QueueIterator(ArrayQueue<T> queue) {
            this.queue = queue;
            oldHead = queue.head;
            oldTail = queue.tail;
            oldSize = queue.size();
        }

        @Override
        public boolean hasNext() {
            if (queue.head != oldHead
                    || queue.tail != oldTail
                    || queue.size() != oldSize) {
                throw new ConcurrentModificationException();
            }
            class C {}
            var c = new C();
            return queue.hasElementAt(index + 1);
        }

        @Override
        public T next() {
            if (queue.head != oldHead
                    || queue.tail != oldTail
                    || queue.size() != oldSize) {
                throw new ConcurrentModificationException();
            }
            if (!queue.hasElementAt(index)) {
                throw new IllegalStateException("Trying to read outside queue bounds.");
            }
            int i = index;
            index++;
            return queue.elementAt(i);
        }
    }

    private int head;
    private int tail;
    private T[] elements;
    private final Object lock;

    public ArrayQueue(T[] init) {
        head = 0;
        elements = Arrays.copyOf(init, ((init.length + 1) << 1));
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
        enqueueAll(coll);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(this);
    }

    public void enqueueAll(Collection<T> collection) {
        if (elements.length <= size() + collection.size()) {
            resize((size() + collection.size()) << 1);
        }
        for (T e : collection) {
            enqueue(e);
        }
    }

    @Override
    public void enqueue(T element) {
        /*
        if (isFull()) {
            resize(size() << 1);
        }
        elements[tail] = element;
        tail++;
        */

        if (isFull()) {
            resize(size() << 1);
        }
        elements[tail] = element;
        if (tail == (elements.length - 1)) {
            if (head > 0) {
                tail = 0;
            } else {
                resize(size() << 1);
                tail++;
            }
        } else {
            tail++;
        }
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (empty()) throw new EmptyQueueException();
        T e = elements[head];
        head++;
        if (head == elements.length) {
            head = 0;
        }
        return e;
    }

    @Override
    public T first() throws EmptyQueueException {
        if (empty()) throw new EmptyQueueException();
        return elements[head];
    }

    @Override
    public boolean empty() {
        return head == tail;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    private boolean isFull() {
        return ((tail == elements.length - 1) && (head == 0))
                || (head == (tail + 1));
    }

    public int size() {
        return isWrapped()
                ? elements.length - head + tail
                : tail - head;
    }

    private boolean isWrapped() {
        return tail < head;
    }

    public T elementAt(int n) {
        int index = (head + n >= elements.length) ? n - head : n + head;
        return elements[index];
    }

    private boolean hasElementAt(int n) {
        if (isWrapped()) {
            return n < tail || n >= head;
        } else {
            return n >= head && n < tail;
        }
    }

    private void resize(int minSize) {
        tail = size();
        head = 0;
        elements = toUnwrapped(minSize);
    }

    private T[] toUnwrapped(int newSize) {
        T[] dest;
        if (newSize < size()) {
            throw new RuntimeException("size()");
        }
        synchronized (lock) {
            dest = (T[]) (new Object[newSize]);
            if (isWrapped()) {
                System.arraycopy(elements, head, dest, 0, elements.length - head);
                System.arraycopy(elements, 0, dest, elements.length - head, tail);
            } else {
                System.arraycopy(elements, 0, dest, 0, tail);
            }
        }
        return dest;
    }


}
