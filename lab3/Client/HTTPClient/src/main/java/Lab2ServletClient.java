import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Lab2ServletClient implements Runnable{
    private static String url = "http://ec2-3-144-187-147.us-east-2.compute.amazonaws.com:8080/LoadTesting_war/HelloServlet";


    @Override
    public void run() {
        HttpClient httpClient = new HttpClient();

        GetMethod getMethod = new GetMethod(url);

        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        try{
            int statusCode = httpClient.executeMethod(getMethod);
            System.out.println("run----" + statusCode);

            if(statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }

            InputStream stream = getMethod.getResponseBodyAsStream();
            byte[] responseBody = stream.readAllBytes();

        }catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        }catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        }finally {
            getMethod.releaseConnection();
        }
    }

    public void runLatencyTest(int requests) {
        StringBuilder stringBuilder;
        List<Long> durations = new ArrayList<>();
        Timestamp start = new Timestamp(System.currentTimeMillis());
        System.out.println("running runLatencyTest");

        try(PrintWriter printWriter = new PrintWriter("LatencyTestOutput.csv")) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("thread id: ");
            stringBuilder.append("start: ");
            stringBuilder.append("end: ");
            stringBuilder.append("duration: ");
            stringBuilder.append('\n');
            System.out.println("running try");

            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod(url);

            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, false));

            try{
                for(int i=0; i<requests; i++) {
                    Timestamp innerStart = new Timestamp(System.currentTimeMillis());
                    int statusCode = client.executeMethod(method);
                    System.out.println(statusCode);

                    if(statusCode != HttpStatus.SC_OK) {
                        System.err.println("Method failed: "+ method.getStatusLine());
                    }

                    InputStream stream = method.getResponseBodyAsStream();
                    byte[] responseBody = stream.readAllBytes();

                    Timestamp innerEnd = new Timestamp(System.currentTimeMillis());
                    long innerDuration = innerEnd.getTime() - innerStart.getTime();
                    durations.add(innerDuration);

                    stringBuilder.append(i);
                    stringBuilder.append(',');
                    stringBuilder.append(innerStart);
                    stringBuilder.append(',');
                    stringBuilder.append(innerEnd);
                    stringBuilder.append(',');
                    stringBuilder.append(innerDuration);
                    stringBuilder.append('\n');

                }
            } catch (HttpException e) {
                System.err.println("Fatal protocol violation: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Fatal transport error: " + e.getMessage());
                e.printStackTrace();
            }finally {
                method.releaseConnection();
                printWriter.write(stringBuilder.toString());
                Timestamp end = new Timestamp(System.currentTimeMillis());
                calculateStatistics(durations, start, end);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void calculateStatistics(List<Long> durations, Timestamp start, Timestamp end) {
        Collections.sort(durations);
        long min = durations.get(0);
        long max = durations.get(durations.size()-1);
        Double averageDuration = durations.stream().collect(Collectors.averagingDouble(d -> d));
        int medianIndex = durations.size()/2 - 1;
        int percentile99Index = (int)((int)durations.size()*0.99) - 1;
        long median = durations.get(medianIndex);
        long percentile99 = durations.get(percentile99Index);
        long totalDuration = end.getTime() - start.getTime();
        int totalRequests = durations.size();
        long throughput = totalRequests/(totalDuration/1000);

        System.out.println("Latency statistics:");
        System.out.println("Average Duration [ms]: " + averageDuration);
        System.out.println("Median Duration [ms]: " + median);
        System.out.println("Throughput [r/s]: " + throughput);
        System.out.println("Percentile 99th Duration [ms]: " + percentile99);
        System.out.println("Min Duration [ms]: " + min);
        System.out.println("Max Duration [ms]: " + max);
        System.out.println("\n");
    }
}
