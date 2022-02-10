package com.example.demo.lockncondition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<T> {
    private Queue<T> queue;
    private int max = 16;
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public CustomBlockingQueue(int size) {
        queue = new LinkedList<>();
        this.max = size;
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            /*
            in case of if:
            When code execute the await() function this will temporally release the lock and pause the execution,
            thus another thread can take the lock and enter to the same status, but when a signal is received only one can take
            the lock again and continue and then the other, but in both cases the execution will continue after IF condition
             */
            while (queue.size() == max){
                // block
                notFull.await();
            }
            queue.add(t);
            // signal consumers that the queue is not empty
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0){
                // block
                notEmpty.await();
            }
            T item = queue.remove();
            // signal producers that the queue is not full
            notFull.signalAll();
            return item;
        }finally {
            lock.unlock();
        }
    }
}
