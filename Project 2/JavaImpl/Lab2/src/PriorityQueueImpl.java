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
        //System.out.println("Adding element: "+item);
    }

    public Comparable remove() {
        if (isEmpty()) {
            System.out.println("The priority queue is empty!! can not remove.");
            return null;
        }
        int maxIndex = 0;
//        // find the index of the item with the lowest cost priority
//        for (int i = 1; i < pQueue.size(); i++) {
//            if (pQueue.get(i).compareTo(pQueue.get(maxIndex)) < 0) {
//                maxIndex = i;
//            }
//        }
        Comparable result = pQueue.get(maxIndex);
        //System.out.println("removing: "+result);
        pQueue.remove(maxIndex);
        return result;
    }

    public void remove(QNode item) {
        // of course this is simpler but we have to count node comparisons?
        //pQueue.removeIf(node-> ((QNode) node).node == item.node);
        //System.out.println("removing: "+result);

        if (isEmpty()) {
            System.out.println("The priority queue is empty!! can not remove.");
            return;
        }
        int maxIndex = 0;
        // find the index of the item with the lowest cost priority
        for (int i = 0; i < pQueue.size(); i++) {
            Djikstra.PQInnerCount++;
            if (((QNode)pQueue.get(i)).node == item.node) {
                pQueue.remove(i);
                i--;
            }
        }
    }
}
