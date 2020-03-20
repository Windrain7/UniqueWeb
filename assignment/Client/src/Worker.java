import org.junit.Test;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Worker implements Runnable {
    Socket s;
    ArrayList<PrintWriter> printWriters;//记录所有客户端输出流

    Worker(Socket socket, ArrayList<PrintWriter> ps) {
        s = socket;
        printWriters = ps;
    }
    @Override
    public void run() {
        try {
            InputStream is = s.getInputStream();
            PrintWriter os = new PrintWriter(s.getOutputStream(),true);
            printWriters.add(os);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println("一个客户端连接成功");
            while (true) {
                String string = br.readLine();
                if (string.equals(" ")) {
                    System.out.println("一个客户端断开连接");
                    break;
                } else {
                    System.out.println(string);
                    tellOthers(string,os);
                }
            }
            printWriters.remove(os);
            is.close();
            os.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellOthers(String message,PrintWriter ps) throws IOException {
        for (PrintWriter printWriter : printWriters) {
            //输出不满一行则右对齐
            if (ps == printWriter) {
                int len = length(message);
                if ( len < 54) {
                    for (int i = 0; i < 54 - len; i++) {
                        printWriter.print(" ");
                    }
                }
            }
            printWriter.println(message);
        }
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
