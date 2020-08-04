package tutorium;

public class OwnFlSortListImpl implements OwnFlSortList {

    class Node {
        Node prev, next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    private Node head;

    public OwnFlSortListImpl() {
        head = new Node(0);
    }

    @Override
    public void insert(int data) {
        Node newNode = new Node(data);
        Node current = head;

        if (newNode.data < 0) {
            current = head.next;
        }

        while (current != null) {
            if (current.next == null) {
                current.next = newNode;
                newNode.prev = current;
                return;
            }
            if (current.data <= data && current.next.data > data) {
                newNode.prev = current;
                newNode.next = current.next;
                current.next.prev = newNode;
                current.next = newNode;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public void remove(int data) {
        Node current = head.next;
        while (current != null) {
            if (current.data == data) {
                if (current.next == null) {
                    current.prev.next = null;
                    return;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    return;
                }
            }
            current = current.next;
        }
    }

    @Override
    public void show() {
        Node current = head.next;
        System.out.print("[");
        while(current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print(", ");
            current= current.next;
        }
        System.out.println("]");
    }
}
