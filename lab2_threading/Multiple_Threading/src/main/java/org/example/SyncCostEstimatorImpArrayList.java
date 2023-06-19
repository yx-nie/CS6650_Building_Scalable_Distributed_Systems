package org.example;

import java.util.ArrayList;
import java.util.List;

public class SyncCostEstimatorImpArrayList implements SyncCostEstimator{
    public List<String> list;
    public int elements;

    public SyncCostEstimatorImpArrayList(int elements) {
        this.elements = elements;
        this.list = new ArrayList<String>(elements);
    }

    @Override
    public int getCounter() {
        return this.list.size();
    }

    @Override
    public void run() {
        for(int i=0; i<elements; i++) {
            this.list.add("c");
        }
    }
}
