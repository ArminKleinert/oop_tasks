package u9;

/*
def next_message():
    while True:
        # Random priority of 0 to 5 (inclusive). We chose this number so
        # that some priority collisions can be seen in the tests.
        priority = random.randint(0, 5) # O(log(N)) where N is the number of bits in 5; But depends on implementation
        timestamp = time.time()         # O(1)
        yield (priority, timestamp, "") # O(n) (n = 3)

def reorder_for_last_message(pqueue):
    index = heap_size(pqueue)
    par_idx = parent(index)
    while index > 1 and prio(pqueue[par_idx], pqueue[index]) < 0:
        pqueue[index], pqueue[par_idx] = pqueue[par_idx], pqueue[index]
        index = par_idx
        par_idx = parent(index)

# SUBSECT Priority-queue functions

"""
message is a Tuple of priority and timestamp

O(n+n) for re-ordering and 1 call to max_heapify
Complexity: O(n)
"""
def insert(pqueue, message):
    pqueue.append(message) # O(1)
    inc_heap_size(pqueue) # O(1)
    pqueue[heap_size(pqueue)] = message # O(1)
    reorder_for_last_message(pqueue) # O(n)
    max_heapify(pqueue, 1) # O(n)
    return pqueue

"""
O(1+1) # Array access and comparison
Complexity: O(1)
"""
def is_empty(pqueue):
    return heap_size(pqueue) == 0

"""
Complexity: O(n) where n is heap_size(pqueue)
"""
def delete(pqueue):
    if is_empty(pqueue): # O(1)
        return None

    result = pqueue[1] # O(1)
    pqueue[1] = pqueue[heap_size(pqueue)] # O(1+1+1)
    dec_heap_size(pqueue) # O(1)
    max_heapify(pqueue, 1) # O(n)
    return result

"""
Messagess are sorted on insertion or deletion.
This function can be used to re-sort a queue if it was corrupted
by evil programmers.

Complexity: O(n)
"""
def sort_messages(pqueue):
    hsize = heap_size(pqueue) # O(1)

    # Return if there is no sorting to be done
    if hsize < 2:             # O(1)
        return pqueue         # ignored for worst case

    # Do the sorting
    max_heapify(pqueue, 1)    # O(n)

    return pqueue

# SUBSECT 2d

"""
Generates 30 random actions (insertion or removal of messages) on a priority queue.
For each action, a random number between 0 and 2 (inclusive) is generated.
On a 0 or 1, a new message is inserted. On a 2, the first message is deleted.
After each action, a bit of information about the current state of the queue is
shown.
There is a random delay of 0 to 1 seconds in between each action.

Complexity: O(N + M + n)
N is the number of bits in a float
M is the number of bits in an int
n is the size of the heap.
"""
def sim_message_traffic():
    # Setup generator aand queue
    generator = next_message() # O(1)
    pqueue = [0]               # O(1)

    actions = 30 # Do 30 actions, each can be insertion or deletioon

    for _ in range(actions):
        # Wait random time between 0 and 1 seconds
        time.sleep(random.random()) # O(N) where N is the number of bits in float)

        # Insertion should be done 2 thirds of the time to show a little traffic
        if random.randint(0, 2) < 2:
            m = next(generator) # O(M)
            insert(pqueue, m) # O(n)
            print("Inserting", m)
        else:
            m = delete(pqueue) # O(n)
            print("Deleting", m)

        # Show information about the current state.
        print("  Size:", heap_size(pqueue), "Empty?", is_empty(pqueue), "Next:", peek(pqueue))

    # Uncomment to clear the queue and show each action
    #while not is_empty(pqueue):
        #m = delete(pqueue)
        #print("Deleting", m)
        #print("        Size:", heap_size(pqueue), "Empty?", is_empty(pqueue), "Next:", peek(pqueue))

    # Return the queue.
    return pqueue

 */

import java.util.Collection;
import java.util.Map;

public class PriorityQueue<P extends Comparable<P>, Data> {

    public final class PQEntry {
        final P prio;
        final Data data;

        public PQEntry(P prio, Data data) {
            this.prio = prio;
            this.data = data;
        }

        public PQEntry(Map.Entry<P, Data> pd) {
            this.prio = pd.getKey();
            this.data = pd.getValue();
        }
    }

    private Object[] heap;

    public PriorityQueue(Map.Entry<P, Data>[] init) {
        this(init.length);
        heap[0] = init.length;

        for (int i = 0; i < init.length; i++) {
            heap[i + 1] = new PQEntry(init[i]);
        }

        // TODO Sort
    }

    public PriorityQueue(Collection<Map.Entry<P, Data>> init) {
        this(init.size());
        heap[0] = init.size();

        int i = 1;
        for (Map.Entry<P, Data> e : init) {
            heap[i + 1] = e;
            i++;
        }

        // TODO Sort
    }

    public PriorityQueue(int initSize) {
        heap = new Object[initSize + 1];
        heap[0] = 0;
    }

    public PriorityQueue() {
        this(16);
    }

    public int size() {
        return ((Number) heap[0]).intValue();
    }

    public Data peek() {
        if (size() == 0) {
            return null;
        }
        return ((PQEntry) heap[1]).data;
    }

    public void add(P prio, Data data) {
        if (size() == heap.length - 1) {
            // TODO Queue is full, resize now!
        }
        heap[size()] = new PQEntry(prio, data);
        incHeapSize();
        reorderForLastMessage();
        maxHeapify(1);
    }

    private void reorderForLastMessage() {
        int index = size();
        int parentIdx = parent(index);
        while (index > 1 && compareAt(parentIdx, index) < 0) {
            Object temp = heap[parentIdx];
            heap[parentIdx] = heap[index];
            heap[index] = temp;
            index = parentIdx;
            parentIdx = parent(index);
        }
    }

    public Data delete() {
        if (isEmpty()) return null;

        PQEntry result = (PQEntry) heap[1];
        heap[1] = heap[size()];
        decHeapSize();
        maxHeapify(1);
        return result.data;
    }

    private Object[] sortMessages() {
        int hsize = size();
        if (hsize < 2) return heap;
        maxHeapify(1);
        return heap;
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    private void decHeapSize() {
        heap[0] = ((Number) heap[0]).intValue() - 1;
    }

    private void incHeapSize() {
        heap[0] = ((Number) heap[0]).intValue() + 1;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return i * 2;
    }

    private int right(int i) {
        return i * 2 + 1;
    }

    private int compareAt(int index0, int index1) {
        Comparable prioElem0 = ((PQEntry) heap[index0]).prio;
        Comparable<P> prioElem1 = ((PQEntry) heap[index1]).prio;
        return prioElem0.compareTo(prioElem1);
    }

    private void maxHeapify(int pos) {
        int leftIndex = left(pos);
        int rightIndex = right(pos);
        int biggest;

        if (leftIndex <= size() && compareAt(leftIndex, pos) > 0) {
            biggest = leftIndex;
        } else if (rightIndex <= size() && compareAt(rightIndex, pos) > 0) {
            biggest = rightIndex;
        } else {
            biggest = pos;
        }

        if (biggest != pos) {
            Object temp = heap[pos];
            heap[pos] = heap[biggest];
            heap[biggest] = temp;
            maxHeapify(biggest);
        }
    }

    private void buildMaxHeap() {
        for (int i = size() / 2; i > 0; i--) {
            maxHeapify(i);
        }
    }
}
