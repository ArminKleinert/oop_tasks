package tutorium;

public interface Queue<T> {
    public void enqueue( T newElement ) ;
    public T dequeue() throws EmptyQueueException;
    public boolean empty();
}
