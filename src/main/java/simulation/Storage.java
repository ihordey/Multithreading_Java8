package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Storage {
    private final List<Horse> storage = new ArrayList<>();
    private AtomicInteger waiting = new AtomicInteger();

    public int getWaiting() {
        return waiting.get();
    }

    public int incrementAndGetWaiting() {
        return waiting.incrementAndGet();
    }

    public synchronized List<Horse> getStorage() {
        return  Collections.unmodifiableList(storage);
    }

    public void add(Horse horse) {
        synchronized (storage) {
            this.storage.add(horse);
        }
    }

    public synchronized int size(){
        return storage.size();
    }
}
