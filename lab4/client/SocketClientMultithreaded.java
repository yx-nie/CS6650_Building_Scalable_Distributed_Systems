package socketexamples;

/**
 * Skeleton socket client. 
 * Accepts host/port on command line or defaults to localhost/12031
 * Then (should) starts MAX_Threads and waits for them all to terminate before terminating main()
 * @author Ian Gorton
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientMultithreaded {
    
    static CyclicBarrier barrier; 
    
    public static void main(String[] args)  {
        String hostName;
        final int MAX_THREADS = 10000 ;
        int port;
        
        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;  // default port in SocketServer
        }
        barrier = new CyclicBarrier(MAX_THREADS);


        // TO DO create and start MAX_THREADS SocketClientThread
        Timestamp start = new Timestamp(System.currentTimeMillis());
        for(int i=0; i<MAX_THREADS; i++) {
            Thread thread = new SocketClientThread(hostName, port, barrier);
            thread.start();
        }

        // TO DO wait for all threads to comple
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        Timestamp end = new Timestamp(System.currentTimeMillis());
        long duration = end.getTime() - start.getTime();
        System.out.println("Duration: " + duration);
        System.out.println("Terminating ....");
                
    }

      
}
