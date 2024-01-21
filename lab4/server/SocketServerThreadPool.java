package socketexamples;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SocketServerThreadPool {
    public static void main(String args[]) throws Exception{
        try (ServerSocket listener = new ServerSocket(12031)) {
            ActiveCount activeCount = new ActiveCount();
            Executor pool = Executors.newFixedThreadPool(20);
            while(true) {
                Socket s = listener.accept();
                pool.execute(new SocketHandlerThread(s, activeCount));
            }
        }
    }
}
