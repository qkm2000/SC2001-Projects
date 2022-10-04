import java.util.ArrayList;

public class PriorityQueueImpl {
    private ArrayList<Comparable> pQueue;
    private int index;

    public PriorityQueueImpl(int capacity) {
        pQueue = new ArrayList<>();
    }

    public boolean isEmpty() {
        return pQueue.isEmpty();
    }

    public void add(Comparable item) {
        int maxIndex = 0;
        // find the index of the item with the lowest cost priority
        for (int i = 0; i < pQueue.size(); i++) {
            Djikstra.PQInnerCount++;
            if (item.compareTo(pQueue.get(i)) < 0) {
                break;
            }else{
                maxIndex++;
            }
        }
        pQueue.add(maxIndex, item);
    }

    public Comparable remove() {
        if (isEmpty()) {
            System.out.println("The priority queue is empty!! can not remove.");
            return null;
        }
        int maxIndex = 0;
        Comparable result = pQueue.get(maxIndex);
        pQueue.remove(maxIndex);
        return result;
    }

    public void remove(QNode item) {
        if (isEmpty()) {
            System.out.println("The priority queue is empty!! can not remove.");
            return;
        }
        int maxIndex = 0;
        // find the index of the item with the lowest cost priority
        for (int i = 0; i < pQueue.size(); i++) {
            Djikstra.PQInnerCount++;
            if (((QNode)pQueue.get(i)).node == item.node) {
                maxIndex = i;
                // If I add this break it optimizes the whole thing by alot :(
                pQueue.remove(maxIndex);
                break;
            }
        }
    }
}
