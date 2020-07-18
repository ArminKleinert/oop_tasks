package u9;

/*
# SUBSECT 2b

"""
random priority -> O(log(N)) where N is the number of bits in an integer
timestamp       -> O(1)
tuple creation  -> O(3)
                => O(log(N))
"""
def next_message():
    while True:
        # Random priority of 0 to 5 (inclusive). We chose this number so
        # that some priority collisions can be seen in the tests.
        priority = random.randint(0, 5) # O(log(N)) where N is the number of bits in 5; But depends on implementation
        timestamp = time.time()         # O(1)
        yield (priority, timestamp, "") # O(n) (n = 3)

# SUBSECT 2c

# SUBSECT Helpers for heap-structure

def heap_size(H):
    return H[0]

"""
O(1+1)
= O(1)
"""
def dec_heap_size(H):
    H[0] = H[0]-1

# O(1) # Same as above
def inc_heap_size(H):
    H[0] = H[0]+1

# O(1)
def parent(i):
    return i//2

# O(1)
def left(i):
    return i*2

# O(1)
def right(i):
    return i*2+1

"""
Comparison function for 2 message-tuples.

If the priorities (at m1[0] and m2[0]) are equal, the timestamps (at m1[1] and m2[1]) are compared.

Complexity: O(1)
"""
def prio(m1, m2):
    if m1[0] == m2[0]: # O(1)
        if m1[1] == m2[1]: # O(1)
            return 0
        else:
            return -1 if m1[1] < m2[1] else 1 # O(1)
    else:
        return -1 if m1[0] < m2[0] else 1 # O(1)

"""
Typical max_heapify modified to use the function prio(...) from above

Complexity: O(n)
"""
def max_heapify(H, pos):
    left_t = left(pos)
    right_t = right(pos)
    if left_t <= heap_size(H) and prio(H[left_t], H[pos]) > 0:
        biggest = left_t
    else:
        biggest = pos

    if right_t <= heap_size(H) and prio(H[right_t], H[pos]) > 0:
        biggest = right_t

    if biggest != pos:
        H[pos], H[biggest] = H[biggest], H[pos]
        max_heapify(H, biggest)

"""
max_heapify is ran n/2 times, where n is the result of heap_size(H)

(n/2) * max_heapify
O((n/2) * n)
= O(n)
"""
def build_max_heap(H):
    H[0] = len(H) - 1
    for i in range(heap_size(H) // 2, 0, -1):
        max_heapify(H, i)

"""
2 Operationen:
- is_empty -> O(1)
- pqueue[1] -> O(1)
O(1)
"""
def peek(pqueue):
    if is_empty(pqueue):
        return None
    return pqueue[1]

"""
The while-loop runs a maximum of n/2 times.
index starts at the heap_size(pqueue) (which I will call n for convenience).
With each iteration, index is halved (by setting it to the parent index).

All other operations are trivial.

Complexity: O(n/2)
= O(n)
"""
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
    public static final class PQEntry<P extends Comparable<P>, Data> {
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
            heap[i + 1] = new PQEntry<>(init[i]);
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


}
