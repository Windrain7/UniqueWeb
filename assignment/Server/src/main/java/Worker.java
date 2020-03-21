import javax.xml.namespace.QName;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Worker implements Runnable {
    Socket s;
    BufferedReader br;
    HashMap<String, PrintWriter> onlines = new HashMap<>();//记录所有客户端输出流和名字
    Connection connection;

    Worker(Socket socket, BufferedReader br, HashMap<String, PrintWriter> os, Connection connection) {
        s = socket;
        this.br = br;
        this.onlines = os;
        this.connection = connection;
    }
    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(s.getOutputStream(),true);
            System.out.println("一个客户端试图登录");
            String id = br.readLine();
            System.out.println(id);
            String password = br.readLine();
            System.out.println(password);
            String name;
            //如果成功，进行服务
            if ((name = check(id,password,writer)) != null) {
                onlines.put(name,writer);//加入在线人员表
                while (true) {
                    String string = br.readLine();
                    if (string.equals(" ")) {
                        System.out.println("一个客户端断开连接");
                        break;
                    } else {
                        System.out.println(string);
                        tellOthers(string,name);
                    }
                }
            }
            onlines.remove(name);
            br.close();
            writer.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //向其他人广播
    public void tellOthers(String message,String name) throws IOException {
        for (String key : onlines.keySet()) {
            //对自己输出不满一行则右对齐
            if (key.equals(name)) {
                int len = length(message);
                if ( len < 54) {
                    for (int i = 0; i < 54 - len; i++) {
                        onlines.get(name).print(" ");
                    }
                }
                onlines.get(name).println(message);
            } else {
                onlines.get(key).println(name + ": " + message);
            }
        }
    }

    public String check(String id, String password, PrintWriter writer)  {
        String name = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from users where id = " + id);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                name = set.getString(2);
                String truePassword = set.getString(3);
                if (password.equals(truePassword)) {
                    System.out.println(name + "登录成功");
                    writer.println("ok " + name);
                } else {
                    System.out.println(name + "登录失败");
                    writer.println("error ");
                    return null;
                }
            } else {
                System.out.println("无效的账号");
                writer.println("error ");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("服务器查询失败");
            e.printStackTrace();
        }
        return name;
    }
    //计算有汉字的字符串长度
    public int length(String string) {
        int length = 0;
        String chinese = "[\u0391-\uFFE5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < string.length(); i++) {
            String temp = string.substring(i, i + 1);
            if (temp.matches(chinese)) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

}
