package simulation;

import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;


public class GameRule implements Runnable {
    private static int count;

    private Storage storage;
    private int maxStep;
    private final ExecutorService ex;

    public GameRule(int maxStep, Storage storage, ExecutorService ex) {
        this.maxStep = maxStep;
        this.storage = storage;
        this.ex = ex;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (storage) {
                if (isGameOver()) {
                    print();
                    ex.shutdownNow();
                }

                System.out.println("storage size : " + storage.size() + "\n" +
                        "waiting size :" + storage.getWaiting());
                if (storage.getWaiting() % storage.size() == 0) {
                    System.out.println("Loop N: " + count++);
                    print();
                    storage.notifyAll();
                }
                try {
                    storage.wait(300);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private void print() {
        Stream.generate(() -> "*").limit(maxStep).forEach(System.out::print);
        System.out.println();
        storage.getStorage().forEach(horse -> {
            Stream.generate(() -> "=").limit(horse.getStep()).forEach(System.out::print);
            System.out.print(horse.getNumber()+"\n");
        });

    }

    public synchronized boolean isGameOver() {
        for (Horse horse : storage.getStorage()) {
            if (horse.getStep() >= maxStep) {
                System.out.println("isGameOver : true");
                return true;
            }
        }
        System.out.println("isGameOver : false");
        return false;
    }
}
