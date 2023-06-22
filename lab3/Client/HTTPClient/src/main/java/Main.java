import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class Main {
    public static StringBuilder stringBuilder;

    public static void main(String[] args) {
        boolean latencyTest = false;

        if(latencyTest) {
            runLatencyTest(200);
        }else {
            try(PrintWriter printWriter = new PrintWriter("Multi_vs_Single.csv")){
                stringBuilder = new StringBuilder();
                stringBuilder.append("task, ");
                stringBuilder.append("type, ");
                stringBuilder.append("numberOfRequests: ");
                stringBuilder.append("numberOfThreads: ");
                stringBuilder.append("durationMiliseconds");
                stringBuilder.append('\n');
                System.out.println("Multi  vs  single");

                for(int i=1; i<1001; i*=10) {
                    run1m(i);

                    run1s(i);

                }
                printWriter.write(stringBuilder.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            runLatencyTest(1000);
        }
    }

    public static void runLatencyTest(int requestNumber) {
        Lab2ServletClient lab2ServletClient = new Lab2ServletClient();
        lab2ServletClient.runLatencyTest(requestNumber);
    }

    public static void run1m(int limit) {

        Timestamp start = new Timestamp(System.currentTimeMillis());
        for(int i=0; i<limit; i++) {
            System.out.println("run 1m//////////");
            Thread thread = new Thread(new Lab2ServletClient());
            thread.start();
        }

        Timestamp end = new Timestamp(System.currentTimeMillis());
        long duration = end.getTime() - start.getTime();


        stringBuilder.append("Get vertical, ");
        stringBuilder.append("Multi thread, ");
        stringBuilder.append(limit);
        stringBuilder.append('\n');
        stringBuilder.append(',');
        stringBuilder.append(duration);
        stringBuilder.append('\n');
    }

    public static void run1s(int limit) {

        Timestamp start = new Timestamp(System.currentTimeMillis());
        for(int i=0; i<limit; i++) {
            System.out.println("run 1s+++++++++");
            Thread thread = new Thread(new Lab2ServletClient());
            thread.start();
            try{
                thread.join();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Timestamp end = new Timestamp(System.currentTimeMillis());
        long duration = end.getTime() - start.getTime();


        stringBuilder.append("Get vertical, ");
        stringBuilder.append("Single thread, ");
        stringBuilder.append(limit);
        stringBuilder.append('\n');
        stringBuilder.append(',');
        stringBuilder.append(duration);
        stringBuilder.append('\n');
    }
}
