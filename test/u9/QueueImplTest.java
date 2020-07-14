package u9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QueueImplTest {
    Integer[] genericData = new Integer[]{1, 2, 3, 4, 5, 6};
    List<Integer> genericColl = Arrays.asList(genericData);
    List<Integer> genericEmptyColl = Arrays.asList();
    int genericSize = 6;

    @BeforeEach
    void setUp() {
    }

    @Test
    void size() {
        Queue<Integer> qi;

        qi = new QueueImpl<>(new Integer[0]);
        assertEquals(qi.size(), 0);
        qi = new QueueImpl<>(new Integer[16]);
        assertEquals(qi.size(), 0);
        qi = new QueueImpl<>(genericData);
        assertEquals(qi.size(), genericSize);

        qi = new QueueImpl<>(genericColl);
        assertEquals(qi.size(), genericSize);
        qi = new QueueImpl<>(genericEmptyColl);
        assertEquals(qi.size(), 0);
        qi = new QueueImpl<>(Set.of(genericData));
        assertEquals(qi.size(), genericSize);
        qi = new QueueImpl<>(new java.util.PriorityQueue<>(genericColl));
        assertEquals(qi.size(), genericSize);
    }

    @Test
    void isEmpty() {
        Queue<Integer> qi;

        qi = new QueueImpl<>(new Integer[0]);
        assertTrue(qi.isEmpty());
        qi = new QueueImpl<>(new Integer[16]);
        assertTrue(qi.isEmpty());
        qi = new QueueImpl<>(genericData);
        assertFalse(qi.isEmpty());

        qi = new QueueImpl<>(genericColl);
        assertTrue(qi.isEmpty());
        qi = new QueueImpl<>(genericEmptyColl);
        assertFalse(qi.isEmpty());
        qi = new QueueImpl<>(Set.of(genericData));
        assertTrue(qi.isEmpty());
        qi = new QueueImpl<>(new java.util.PriorityQueue<>(genericColl));
        assertTrue(qi.isEmpty());
    }

    @Test
    void contains() {
    }

    @Test
    void iterator() {
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