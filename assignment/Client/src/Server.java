

import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    public static void main(String[] args) {
        try {
            ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            ServerSocket ss = new ServerSocket(7000);
            ArrayList<PrintWriter> printWriters = new ArrayList<>();//记录所有客户端输出流
            System.out.println("服务器已启动");
            while (true) {
                Socket s = ss.accept();
                pool.submit(new Worker(s,printWriters));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
