package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SyncCostEstimatorHashMap implements SyncCostEstimator{
    public Map<Integer, String> hashMap;
    public int elements;

    public SyncCostEstimatorHashMap(int elements) {
        this.elements = elements;
        this.hashMap = Collections.synchronizedMap(new HashMap<Integer, String>());

    }

    @Override
    public int getCounter() {
        return this.hashMap.size();
    }

    @Override
    public void run() {
        for(int i=0; i<elements; i++) {
            this.hashMap.put(1, "c");
        }
    }
}
