package BlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class BlockingQueueWithLocks<T> {

    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final List<T> items = new ArrayList<>();

    public void put(T item) {
        lock.lock();
        try {
            items.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        lock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }
            return items.removeFirst();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        } finally {
            lock.unlock();
        }
    }
}
