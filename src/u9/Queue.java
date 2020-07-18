package u9;

public interface Queue<E> {
    public void enqueue(E element);

    public E dequeue() throws EmptyQueueException;

    public E first() throws EmptyQueueException;

    public boolean empty();

    public String toString();
}
