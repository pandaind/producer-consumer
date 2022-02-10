package com.example.demo.waitnnotify;

import com.example.demo.Item;

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        CustomBlockingQueue<Item> queue = new CustomBlockingQueue<>(10);

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
        //Thread.sleep(1000);
        System.out.println("New Item Consumed");
    }

    private static Item createItem() throws InterruptedException {
        //Thread.sleep(5000);
        System.out.println("New Item Created");
        return new Item();
    }
}
