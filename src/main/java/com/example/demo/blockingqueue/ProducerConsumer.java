package com.example.demo.blockingqueue;

import com.example.demo.Item;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Item> queue = new ArrayBlockingQueue<>(10);

        // Producer
        final Runnable producer = () -> {
            while (true) {
                try {
                    // Thread blocks if the queue is full
                    queue.put(createItem());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(producer).start();
        new Thread(producer).start();

        // Consumer
        final Runnable consumer = () -> {
            while (true){
                try {
                    // Thread blocks if the queue is empty
                    Item i = queue.take();
                    process(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(consumer).start();
        new Thread(consumer).start();

        Thread.sleep(1000);
    }

    private static void process(Item i) throws InterruptedException {
        // consuming slowly...
        Thread.sleep(5000);
        System.out.println("New Item Consumed");
    }

    private static Item createItem() throws InterruptedException {
       // producing faster...than consumers...
        Thread.sleep(1000);
        System.out.println("New Item Created");
        return new Item();
    }
}
