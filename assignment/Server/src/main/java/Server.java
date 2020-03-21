import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    //    ServerSocket ss;
//    ThreadPoolExecutor pool;
//    ArrayList<PrintWriter> printWriters;
//    Connection connection;
    public static void main(String[] args) {
        try {
            ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            ServerSocket ss = new ServerSocket(7000);
            HashMap<String, PrintWriter> onlines = new HashMap<>();//记录所有客户端输出流和名字
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/learn", "root", "jiangtao");
            System.out.println("服务器已启动");
            while (true) {
                Socket s = ss.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                if (br.readLine().equals("log")) {
                    pool.submit(new Worker(s,onlines,connection));
                } else if (br.readLine().equals("register")) {
                    pool.submit(new RegisterWorker(connection, s));
                }

            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
