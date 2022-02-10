package com.example.demo.waitnnotify;

import java.util.LinkedList;
import java.util.Queue;

public class CustomBlockingQueue<T> {
    private final Queue<T> queue;
    private final int max;
    private final Object sharedObject = new Object();

    public CustomBlockingQueue(int size) {
        queue = new LinkedList<>();
        this.max = size;
    }

    public void put(T t) throws InterruptedException {
        synchronized (sharedObject) {
            if (queue.size() == max) {
                // block
                sharedObject.wait();
            }
            queue.add(t);
            // signal consumers that the queue is not empty
            sharedObject.notifyAll();
        }
    }

    public T take() throws InterruptedException {
        synchronized (sharedObject) {
            if (queue.size() == 0) {
                // block
                sharedObject.wait();
            }
            T item = queue.remove();
            // signal producers that the queue is not full
            sharedObject.notifyAll();
            return item;
        }
    }
}
