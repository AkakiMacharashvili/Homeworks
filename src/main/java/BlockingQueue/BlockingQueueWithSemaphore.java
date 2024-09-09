package BlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BlockingQueueWithSemaphore<T> {
    private final List<T> items = new ArrayList<>();
    private final Semaphore itemsSemaphore = new Semaphore(0);  // counts available items
    private final Semaphore lock = new Semaphore(1);  // for exclusive access to the queue

    public void put(T item) throws InterruptedException {
        lock.acquire();
        try {
            items.add(item);
        } finally {
            lock.release();
            itemsSemaphore.release();  // signal that an item is available
        }
    }

    public T get() throws InterruptedException {
        itemsSemaphore.acquire();  // wait if no items are available
        lock.acquire();  // lock for exclusive access
        try {
            return items.remove(0);
        } finally {
            lock.release();  // release the lock
        }
    }
}
