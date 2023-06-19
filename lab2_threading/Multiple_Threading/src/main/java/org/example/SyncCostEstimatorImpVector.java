package org.example;

import java.util.Vector;

public class SyncCostEstimatorImpVector implements SyncCostEstimator{
    private Vector<String> vector;
    private int elements;

    public SyncCostEstimatorImpVector(int elements) {
        this.elements = elements;
        this.vector = new Vector<>(elements);
    }

    @Override
    public int getCounter() {
        return this.vector.capacity();
    }

    @Override
    public void run() {
        for(int i=0; i<elements; i++) {
            this.vector.add("c");
        }
    }



}
