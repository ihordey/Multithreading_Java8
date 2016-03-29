package simulation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class HorseRacing {

    private static final int N_THREADS = 3;

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService ex = Executors.newCachedThreadPool();
        final Storage storage = new Storage();
        final GameRule rule = new GameRule(53, storage, ex);

        for (int i = 0; i < N_THREADS; i++) {
            final Horse horse = new Horse(storage);
            storage.add(horse);
            ex.execute(horse);
        }
        ex.execute(rule);

        TimeUnit.SECONDS.sleep(5);
        ex.shutdownNow();
    }
}
