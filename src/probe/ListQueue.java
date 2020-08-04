package probe;

import tutorium.EmptyQueueException;

import java.util.Iterator;

public class ListQueue<T> implements Queue<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
        }
    }

    public static class ListQueueIter<T> implements Iterator<T> {

        Node<T> curr;

        public ListQueueIter(ListQueue<T> lq) {
            this.curr = lq.head;
        }

        @Override
        public boolean hasNext() {
            return curr == null;
        }

        @Override
        public T next() {
            curr = curr.next;
            return curr.data;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    private ListQueue() {
        head = null;
        tail = null;
    }

    @Override
    public void enqueue(T element) {
        if (head == null) {
            head = new Node<>(element, null);
            tail = head;
            return;
        }

        Node<T> curr = new Node<>(element, null);
        tail.next = curr;
        tail = curr;
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (empty()) throw new EmptyQueueException();
        T data = head.data;
        head = head.next;
        return data;
    }

    @Override
    public T head() throws EmptyQueueException {
        if (empty()) throw new EmptyQueueException();
        return head.data;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListQueueIter<T>(this);
    }
}
