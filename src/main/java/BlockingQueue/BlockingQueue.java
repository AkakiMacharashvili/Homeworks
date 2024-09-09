package BlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {

    List<T> items = new ArrayList<>();
    Object mutex = new Object();
    Object addition = new Object();
    public void put(T item) {
        synchronized (addition) {
            items.add(item);
        }
        synchronized (mutex) {
            mutex.notify();
        }
    }


    public T get() {
        while (items.isEmpty()) {
            synchronized (mutex) {
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return items.remove(0);
    }
}