package simulation;

import java.util.Random;


public class Horse implements Runnable {
    private final Random random = new Random();
    private static int count;
    private int number = count++;
    private int step;
    private Storage storage;

    public Horse(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            step += random.nextInt(15);
            synchronized (storage) {
                try {
                    storage.incrementAndGetWaiting();
                    System.out.println("Horse N: " + number + " steps " + step);
                    storage.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public int getStep() {
        return step;
    }

    public int getNumber() {
        return number;
    }
}
