package u9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {
    Integer[] genericData = new Integer[]{1, 2, 3, 4, 5, 6};
    List<Integer> genericColl = Arrays.asList(genericData);
    List<Integer> genericEmptyColl = Arrays.asList();
    int genericSize = 6;

    @BeforeEach
    void setUp() {
    }

    @Test
    void size() {
        Queue<Integer> aqueue;

        aqueue = new ArrayQueue<>(new Integer[0]);
        assertEquals(aqueue.size(), 0);
        aqueue = new ArrayQueue<>(new Integer[16]);
        assertEquals(aqueue.size(), 16);
        aqueue = new ArrayQueue<>(genericData);
        assertEquals(aqueue.size(), genericData.length);

        aqueue = new ArrayQueue<>(genericColl);
        assertEquals(aqueue.size(), genericColl.size());
        aqueue = new ArrayQueue<>(genericEmptyColl);
        assertEquals(aqueue.size(), genericEmptyColl.size());
        aqueue = new ArrayQueue<>(Set.of(genericData));
        assertEquals(aqueue.size(), Set.of(genericData).size());
        aqueue = new ArrayQueue<>(new java.util.PriorityQueue<>(genericColl));
        assertEquals(aqueue.size(), genericColl.size());
    }

    @Test
    void isEmpty() {
        Queue<Integer> aqueue;

        aqueue = new ArrayQueue<>(new Integer[0]);
        assertTrue(aqueue.isEmpty());
        aqueue = new ArrayQueue<>(new Integer[16]);
        assertFalse(aqueue.isEmpty());
        aqueue = new ArrayQueue<>(genericData);
        assertFalse(aqueue.isEmpty());

        aqueue = new ArrayQueue<>(genericColl);
        assertFalse(aqueue.isEmpty());
        aqueue = new ArrayQueue<>(genericEmptyColl);
        assertTrue(aqueue.isEmpty());
        aqueue = new ArrayQueue<>(Set.of(genericData));
        assertFalse(aqueue.isEmpty());
        aqueue = new ArrayQueue<>(new java.util.PriorityQueue<>(genericColl));
        assertFalse(aqueue.isEmpty());
    }

    @Test
    void contains() {
        Queue<Integer> aqueue;

        aqueue = new ArrayQueue<>(new Integer[0]);
        assertFalse(aqueue.contains(null));
        assertFalse(aqueue.contains(""));
        assertFalse(aqueue.contains(0));
        aqueue = new ArrayQueue<>(new Integer[16]);
        assertTrue(aqueue.contains(null));
        assertFalse(aqueue.contains(""));
        assertFalse(aqueue.contains(0));
        aqueue = new ArrayQueue<>(genericData);
        assertFalse(aqueue.contains(null));
        assertFalse(aqueue.contains(""));
        assertTrue(aqueue.contains(0));

        aqueue = new ArrayQueue<>(genericColl);
        assertTrue(aqueue.contains(0));
        aqueue = new ArrayQueue<>(genericEmptyColl);
        assertTrue(aqueue.contains(0));
        aqueue = new ArrayQueue<>(Set.of(genericData));
        assertTrue(aqueue.contains(0));
        aqueue = new ArrayQueue<>(new java.util.PriorityQueue<>(genericColl));
        assertTrue(aqueue.contains(0));
    }

    @Test
    void iterator() {
        // TODO
    }

    @Test
    void toArray() {
    }

    @Test
    void testToArray() {
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void containsAll() {
    }

    @Test
    void addAll() {
    }

    @Test
    void removeAll() {
    }

    @Test
    void retainAll() {
    }

    @Test
    void clear() {
    }

    @Test
    void offer() {
    }

    @Test
    void testRemove() {
    }

    @Test
    void poll() {
    }

    @Test
    void element() {
    }

    @Test
    void peek() {
    }
}