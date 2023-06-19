package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class SyncCostEstimatorConcurrentMap implements SyncCostEstimator{
    private ConcurrentHashMap<Integer, String> cmap;
    private int elements;

    public SyncCostEstimatorConcurrentMap(int elements) {
        this.elements = elements;
        this.cmap = new ConcurrentHashMap<Integer, String>();
    }

    @Override
    public int getCounter() {
        return this.cmap.size();
    }

    @Override
    public void run() {
        for(int i=0; i<elements; i++) {
            this.cmap.put(1, "c");
        }
    }
}
