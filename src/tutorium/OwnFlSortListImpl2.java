package tutorium;

public class OwnFlSortListImpl2 implements OwnFlSortList {

    static class Node {
        Node next;
        int data;

        Node(int data) {
            this.data = data;
        }
    }

    Node head;

    public OwnFlSortListImpl2 () {
        head = new Node(0);
    }

    @Override
    public void insert(int data) {
        Node current = head;
        if (head.next != null) {
            current = head.next;
            while (current.next != null) {
                if (current.data < data && current.next.data >= data) {
                    Node newnode = new Node(data);
                    newnode.next = current.next;
                    current.next = newnode;
                    return;
                }
                current = current.next;
            }
        }
        current.next = new Node(data);
    }

    @Override
    public void remove(int data) {
        Node prev = head;
        Node current = head.next;
        if (current == null)
            return;
        do {
            if (current.data == data) {
                prev.next = current.next;
                return;
            }
            prev = current;
            current = current.next;
        }while (current != null);
    }

    @Override
    public void show() {
        System.out.print("[");
        Node current = head.next;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print(", ");
            current = current.next;
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        OwnFlSortList ofsl = new OwnFlSortListImpl2();

        ofsl.insert(1);
        ofsl.show();
        ofsl.insert(3);
        ofsl.show();
        ofsl.insert(2);
        ofsl.show();
        ofsl.insert(5);
        ofsl.show();
        ofsl.insert(4);
        ofsl.show();

        ofsl.remove(3);
        ofsl.show();
        ofsl.remove(1);
        ofsl.show();
        ofsl.remove(4);
        ofsl.show();
        ofsl.remove(5);
        ofsl.show();
        ofsl.remove(2);
        ofsl.show();
    }
}
