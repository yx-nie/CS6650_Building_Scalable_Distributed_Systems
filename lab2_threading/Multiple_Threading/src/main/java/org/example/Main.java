package org.example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class Main {
    public static StringBuilder str;

    public static void main(String[] args) {
        try (PrintWriter printWriter = new PrintWriter("test.csv")) {
            str = new StringBuilder();
            str.append("task, ");
            str.append("type, ");
            str.append("limitation, ");
            str.append("millionseconds");
            str.append('\n');

            for (int i = 10; i < 1000000; i *= 10) {
                run1(i);
                run2(i);
                run3a(i);
                run3b(i, 100);
            }

            printWriter.write(str.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void run1(int limit) {
        Timestamp start = new Timestamp(System.currentTimeMillis());

        for(int i=0; i<limit; i++) {
            Thread thread = new Thread(new SyncCostEstimatorImp1());
            thread.start();

            try{
                thread.join();;
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Timestamp end = new Timestamp(System.currentTimeMillis());
        long duration = end.getTime() - start.getTime();

        str.append("Counter, ");
        str.append("Multi thread, ");
        str.append(limit);
        str.append(',');
        str.append(duration);
        str.append('\n');

    }

    public static void run2(int limit) {
        Timestamp startVector = new Timestamp(System.currentTimeMillis());
        Thread thread = new Thread(new SyncCostEstimatorImpVector(limit));
        thread.start();

        try{
            thread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        Timestamp endVector = new Timestamp(System.currentTimeMillis());
        long vectorDuration = endVector.getTime() - startVector.getTime();

        Timestamp startArray = new Timestamp(System.currentTimeMillis());
        Thread  thread2 = new Thread(new SyncCostEstimatorImpArrayList(limit));
        thread2.start();
        try{
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Timestamp endArray = new Timestamp(System.currentTimeMillis());
        long arrayDuration = endArray.getTime() - startArray.getTime();

        str.append("Collections1, ");
        str.append("VectorSingleThread, ");
        str.append(limit);
        str.append(',');
        str.append(vectorDuration);
        str.append('\n');
        str.append("Collections1, ");
        str.append("ArraySingleThread, ");
        str.append(limit);
        str.append(',');
        str.append(arrayDuration);
        str.append('\n');

    }

    public static void run3a(int limit) {
        Timestamp hashTableStart = new Timestamp(System.currentTimeMillis());
        Thread thread = new Thread(new SyncCostEstimatorHashTable(limit));

        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        Timestamp hashTableEnd = new Timestamp(System.currentTimeMillis());
        long hashTableDuration = hashTableEnd.getTime() - hashTableStart.getTime();

        Timestamp hashMapStart = new Timestamp(System.currentTimeMillis());
        Thread thread2 = new Thread(new SyncCostEstimatorHashMap(limit));

        thread2.start();
        try{
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Timestamp hashMapEnd = new Timestamp(System.currentTimeMillis());
        long hashMapDuration = hashMapEnd.getTime() - hashMapStart.getTime();

        Timestamp cmapStart = new Timestamp(System.currentTimeMillis());
        Thread thread3 = new Thread(new SyncCostEstimatorConcurrentMap(limit));

        thread3.start();
        try{
            thread3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Timestamp cmpaEnd = new Timestamp(System.currentTimeMillis());
        long cmapDuration = cmpaEnd.getTime() - cmapStart.getTime();

        str.append("Collections2, ");
        str.append("HashTableSingleThreading,  ");
        str.append(limit);
        str.append(',');
        str.append(hashTableDuration);
        str.append('\n');
        str.append("Collections2, ");
        str.append("HashMapSingleThreading,  ");
        str.append(limit);
        str.append(',');
        str.append(hashMapDuration);
        str.append('\n');
        str.append("Collections2, ");
        str.append("ConcurrentHashMapSingleThreading,  ");
        str.append(limit);
        str.append(',');
        str.append(cmapDuration);
        str.append('\n');
    }

    public static void run3b(int limit, int threads) {

        Timestamp hashTableStart = new Timestamp(System.currentTimeMillis());
        Thread thread = new Thread(new SyncCostEstimatorHashTable(limit/threads));

        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        Timestamp hashTableEnd = new Timestamp(System.currentTimeMillis());
        long hashTableDuration = hashTableEnd.getTime() - hashTableStart.getTime();

        Timestamp hashMapStart = new Timestamp(System.currentTimeMillis());
        Thread thread2 = new Thread(new SyncCostEstimatorHashMap(limit/threads));

        thread2.start();
        try{
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Timestamp hashMapEnd = new Timestamp(System.currentTimeMillis());
        long hashMapDuration = hashMapEnd.getTime() - hashMapStart.getTime();

        Timestamp cmapStart = new Timestamp(System.currentTimeMillis());
        Thread thread3 = new Thread(new SyncCostEstimatorConcurrentMap(limit/threads));

        thread3.start();
        try{
            thread3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Timestamp cmpaEnd = new Timestamp(System.currentTimeMillis());
        long cmapDuration = cmpaEnd.getTime() - cmapStart.getTime();

        str.append("Collections2, ");
        str.append("HashTableMultiThreading,  ");
        str.append(limit);
        str.append(',');
        str.append(hashTableDuration);
        str.append('\n');
        str.append("Collections2, ");
        str.append("HashMapMultiThreading,  ");
        str.append(limit);
        str.append(',');
        str.append(hashMapDuration);
        str.append('\n');
        str.append("Collections2, ");
        str.append("ConcurrentHashMapMultiThreading,  ");
        str.append(limit);
        str.append(',');
        str.append(cmapDuration);
        str.append('\n');

    }
}