package u9;

/*
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

import java.util.Random;

public class SimulateMessageTraffic {
    private final Random rand = new Random();

    private Integer newPriority() {
        return rand.nextInt(5);
    }

    private void simulateMessageTraffic(PriorityQueue<Integer, Object> pqueue, int actions) {
        for (int i = 0; i < actions; i++) {
            if (rand.nextInt(3) < 2) {
                int np = newPriority();
                pqueue.add(np, i);
                System.out.println("Added (" + np + ", " + i + ")");
            } else {
                try {
                    Object removed = pqueue.dequeue();
                    System.out.println("Removed " + removed);
                } catch(EmptyQueueException eqe) {
                    System.out.println("Tried to remove empty entry.");
                }
            }
            System.out.println(pqueue);
        }

        while (!pqueue.empty()) {
            Object removed = pqueue.dequeue();
            System.out.println("Removed " + removed);
            System.out.println(pqueue);
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Integer, Object> pqueue = new PriorityQueue<>(1);
        (new SimulateMessageTraffic()).simulateMessageTraffic(pqueue, 30);
    }
}
