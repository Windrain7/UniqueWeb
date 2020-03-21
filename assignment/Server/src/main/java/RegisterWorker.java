import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterWorker implements Runnable{
    Socket socket;
    BufferedReader br;
    Connection connection;

    public RegisterWorker(Socket socket, BufferedReader br, Connection connection) {
        this.socket = socket;
        this.br = br;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            System.out.println("一个客户端试图注册");
            String clientRequest = br.readLine();
            System.out.println(clientRequest);
            String strings[] = clientRequest.split(" ",3);
            String id = strings[0];
            System.out.println(id);
            String name = strings[1];
            System.out.println(name);
            String password = strings[2];
            System.out.println(password);
            writer.println(register(id,name,password));
            socket.close();
            System.out.println("注册断开");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String register(String id, String name, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where id = " + id);
            ResultSet set = ps.executeQuery();
            if (set.next())  return "账号重复";
            ps = connection.prepareStatement("select * from users where name = " + name);
            set = ps.executeQuery();
            if(set.next()) return  "昵称重复";
            ps = connection.prepareStatement("insert into users values(?,?,?)");
            ps.setString(1,id);
            ps.setString(2,name);
            ps.setString(3,password);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("插入数据失败");
            e.printStackTrace();
            return "注册失败";
        }
        return "注册成功";
    }
}
