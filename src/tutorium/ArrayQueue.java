package tutorium;

import java.util.Iterator;

public class ArrayQueue<T> implements Queue<T>, Iterable<T> {

    public static class QueueIterator<T> implements Iterator<T> {

        private final ArrayQueue<T> queue;

        QueueIterator(ArrayQueue<T> _queue) {
            ArrayQueue<T> temp = new ArrayQueue<>(_queue.size());
            queue = new ArrayQueue<>(_queue.size());

            try {
            while (!_queue.empty()) {
                T t = null;
                    t = _queue.dequeue();
                temp.enqueue(t);
                queue.enqueue(t);
            }

            while (!temp.empty()) {
                _queue.enqueue(temp.dequeue());
            }
            } catch (EmptyQueueException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.empty();
        }

        @Override
        public T next() {
            try {
                return queue.dequeue();
            } catch (EmptyQueueException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int head;
    private int tail;
    private T[] queue;

    public ArrayQueue(int size) {
        head = tail = 0;
        queue = (T[]) new Object[size];
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(this);
    }

    @Override
    public void enqueue(T newElement) {
        if (isFull()) {
            resizeQueueTo(size() << 1);
        }
        queue[tail] = newElement;
        if (tail == (queue.length - 1)) {
            if (head > 0) {
                tail = 0;
            } else {
                resizeQueueTo(size() << 1);
                tail++;
            }
        } else {
            tail++;
        }
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (empty()) throw new EmptyQueueException();
        T e = queue[head];
        head++;
        if (head == queue.length) {
            head = 0;
        }
        return e;
    }

    @Override
    public boolean empty() {
        return head == tail;
    }

    private void resizeQueueTo(int max) {
        T[] tmpQueue = (T[]) new Object[max];
        if (tail < head)
            tail += queue.length - head;
        else
            tail -= head;
        for (int i = 0; i < tail; ++i) {
            tmpQueue[i] = queue[(i + head) % queue.length];
        }
        head = 0;
        queue = tmpQueue;
    }

    public int size() {
        if (isWrapped())
            return queue.length - head + tail;
        else
            return tail - head;
    }

    private boolean isWrapped() {
        return head > tail;
    }

    private boolean isFull() {
        return ((tail == queue.length - 1) && (head == 0))
                || (head == (tail + 1));
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(16);
        try {
            queue.dequeue();
            System.out.println("1 Fail");
        }catch(EmptyQueueException eqe) {
            System.out.println("1");}

        System.out.println(queue.empty() ? "2" : "2 Fail");

        queue.enqueue(123);
        queue.enqueue(124);
        queue.enqueue(125);

        System.out.println(queue.empty() ? "3 Fail" : "3");

        try {
            System.out.println("4 Pull: " + (queue.dequeue() == 123));
            System.out.println("4 Pull: " + (queue.dequeue() == 124));
            System.out.println("4 Pull: " + (queue.dequeue() == 125));
        }catch(EmptyQueueException eqe) {
            System.out.println("4 Fail");
        }

        System.out.println(queue.empty() ? "5" : "5 fail");

        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }

        Iterator<Integer> iter = queue.iterator();
        for (int i = 0; i < 5; i++) {
            System.out.println(iter.hasNext() ? "6 hasNext":"6 hasNext failed");
            //System.out.println(iter.next().intValue() == i ? "6 next" : "6 next failed");
            System.out.println(iter.next().intValue());
        }
        System.out.println(iter.hasNext() ? "7 Iter still has next???" : "7");
    }
}
