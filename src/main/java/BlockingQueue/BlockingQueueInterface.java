package BlockingQueue;

public interface BlockingQueueInterface<T> {
    void put(T item);
    T get();
    int size();
}
