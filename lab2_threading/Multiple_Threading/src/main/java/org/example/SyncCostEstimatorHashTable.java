package org.example;

import java.util.Hashtable;

public class SyncCostEstimatorHashTable implements SyncCostEstimator{
    public Hashtable<Integer, String> table;
    public int elements;

    public SyncCostEstimatorHashTable(int elements) {
        this.elements = elements;
        this.table = new Hashtable<Integer, String>();
    }

    @Override
    public int getCounter() {
        return this.table.size();
    }

    @Override
    public void run() {
        for(int i=0; i<elements; i++) {
            table.put(1,"c");
        }
    }
}
