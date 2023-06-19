package org.example;

public class SyncCostEstimatorImp1 implements SyncCostEstimator{
    private static int counter = 0;
    private static final Object lock = new Object();
    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void run() {
        increaseCounter();
    }

    private void increaseCounter(){
        synchronized (lock){
            for(int i=0; i<10; i++) {
                counter++;
            }
        }
    }
}
