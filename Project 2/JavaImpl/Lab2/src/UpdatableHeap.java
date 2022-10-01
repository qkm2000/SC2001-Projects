import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class UpdatableHeap<T> {

    private final PriorityQueue<Node<T>> pq = new PriorityQueue<>((o1, o2) -> {
            Djikstra.PQInnerCount++;
            if (o1.priority < o2.priority)
                return -1;
            else if (o1.priority > o2.priority)
                return 1;

            return 0;
        });
    private final Map<T, Node<T>> entries = new HashMap<>();

    public void addOrUpdate(T value, int priority) {
        if (entries.containsKey(value)) {
            entries.remove(value).removed = true;
        }

        Node<T> node = new Node<>(value, priority);
        entries.put(value, node);
        pq.add(node);
    }

    public T pop() {
        while (!pq.isEmpty()) {
            Node<T> node = pq.poll();
            if (!node.removed) {
                entries.remove(node.value);
                return node.value;
            }
        }

        throw new IllegalStateException("pop from empty heap");
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
    public int size(){
        return entries.size();
    }

    private static class Node<T> {
        private final T value;
        private final int priority;
        private boolean removed = false;

        private Node(T value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }
}