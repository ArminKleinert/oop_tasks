package u9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

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
        ArrayQueue<Integer> aqueue;

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
    void empty() {
        ArrayQueue<Integer> aqueue;

        // Testing empty constructor and initial sizes
        aqueue = new ArrayQueue<>();
        assertTrue(aqueue.empty());
        aqueue = new ArrayQueue<>(1);
        assertTrue(aqueue.empty());
        aqueue = new ArrayQueue<>(16);
        assertTrue(aqueue.empty());

        // Testing with input array
        aqueue = new ArrayQueue<>(new Integer[0]);
        assertTrue(aqueue.empty());
        aqueue = new ArrayQueue<>(new Integer[16]);
        assertFalse(aqueue.empty());
        aqueue = new ArrayQueue<>(genericData);
        assertFalse(aqueue.empty());

        // Testing with Collection as input
        aqueue = new ArrayQueue<>(genericColl);
        assertFalse(aqueue.empty());
        aqueue = new ArrayQueue<>(genericEmptyColl);
        assertTrue(aqueue.empty());
        aqueue = new ArrayQueue<>(Set.of(genericData));
        assertFalse(aqueue.empty());
        aqueue = new ArrayQueue<>(new java.util.PriorityQueue<>(genericColl));
        assertFalse(aqueue.empty());
    }

    @Test
    void iterator() {
        ArrayQueue<Integer> aqueue = new ArrayQueue<>(new Integer[]{1, 2, 3, 4, 5, 6});
        Iterator<Integer> itr = aqueue.iterator();
        assertTrue(itr.hasNext());
        assertEquals(itr.next(), 1);
        assertEquals(itr.next(), 2);
        assertEquals(itr.next(), 3);
        assertEquals(itr.next(), 4);
        assertEquals(itr.next(), 5);
        assertEquals(itr.next(), 6);
        assertFalse(itr.hasNext());

        // Test exception when queue is iterated all the way
        assertThrows(IllegalStateException.class, itr::next);

        // Test modification exception
        aqueue = new ArrayQueue<>(new Integer[]{1, 2, 3, 4, 5, 6});
        itr = aqueue.iterator();
        assertTrue(itr.hasNext());
        assertEquals(itr.next(), 1);
        assertEquals(1, aqueue.dequeue());
        assertThrows(ConcurrentModificationException.class, itr::next);
        assertThrows(ConcurrentModificationException.class, itr::hasNext);

        // Test for-each Loop
        int i = 0;
        for (Integer current : new ArrayQueue<>(genericData)) {
            assertEquals(genericData[i], current);
            i++;
        }
    }

    @Test
    void enqueueAll() {
    }

    @Test
    void enqueue() {
        ArrayQueue<Integer> aqueue = new ArrayQueue<>();
        assertTrue(aqueue.empty());
        aqueue.enqueue(1);
        assertFalse(aqueue.empty());
        assertEquals(1, aqueue.dequeue());
        assertTrue(aqueue.empty());

        for (int i = 0; i < 20; i++) {
            aqueue.enqueue(i);
        }
        assertEquals(20, aqueue.size());
    }

    @Test
    void dequeue() {
        Integer[] input = new Integer[20];
        for (int i = 0; i < 20; i++) {
            input[i] = i;
        }

        ArrayQueue<Integer> aqueue = new ArrayQueue<>(input);
        assertEquals(aqueue.size(), 20);

        Integer[] dequeueResults = new Integer[20];
        for (int i = 0; i < 20; i++) {
            dequeueResults[i] = aqueue.dequeue();
        }

        assertTrue(aqueue.empty());
        assertThrows(EmptyQueueException.class, aqueue::dequeue);
        assertTrue(Arrays.equals(input, dequeueResults));
    }

    @Test
    void first() {
        ArrayQueue<Integer> aqueue = new ArrayQueue<>();
        assertThrows(EmptyQueueException.class, aqueue::first);
        aqueue.enqueue(1000);
        assertEquals(1000, aqueue.first());
        aqueue.enqueue(2);
        assertEquals(1000, aqueue.first());
    }
}