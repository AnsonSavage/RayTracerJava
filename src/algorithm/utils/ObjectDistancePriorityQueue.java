package algorithm.utils;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ObjectDistancePriorityQueue {

    private PriorityQueue<ObjectDistancePair> queue;

    public ObjectDistancePriorityQueue() {
        // Define the comparator to order the elements by distance
        Comparator<ObjectDistancePair> comparator = new Comparator<ObjectDistancePair>() {
            @Override
            public int compare(ObjectDistancePair o1, ObjectDistancePair o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        };

        this.queue = new PriorityQueue<>(comparator);
    }

    // Method to add an ObjectDistancePair to the collection
    public void add(ObjectDistancePair pair) {
        queue.add(pair);
    }

    // Method to merge another collection with this one
    public void merge(ObjectDistancePriorityQueue otherQueue) {
        queue.addAll(otherQueue.getCollection());
    }

    // Method to get the first item (with the smallest distance)
    public ObjectDistancePair peek() {
        return queue.peek();
    }

    // Optional: Method to remove and return the first item
    public ObjectDistancePair poll() {
        return queue.poll();
    }

    // Optional: Method to get the underlying collection (for read-only purposes, etc.)
    public Collection<ObjectDistancePair> getCollection() {
        return queue;
    }

    public int size() {
        return queue.size();
    }
}
