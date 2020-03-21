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
    Connection connection;
    Socket socket;

    public RegisterWorker(Connection connection, Socket socket) {
        this.connection = connection;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            String id = br.readLine();
            String name = br.readLine();
            String password = br.readLine();
            System.out.println("一个客户端试图注册");
            writer.println(register(id,name,password));
            socket.close();
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
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "注册成功";
    }
}
