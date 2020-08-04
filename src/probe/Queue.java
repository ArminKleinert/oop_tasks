package probe;

import tutorium.EmptyQueueException;

import java.util.Iterator;

public interface Queue<T> {
    void enqueue(T element);
    T dequeue() throws EmptyQueueException;
    T head() throws EmptyQueueException;
    boolean empty();
    Iterator<T> iterator();
}
