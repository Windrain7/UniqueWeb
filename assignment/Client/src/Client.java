
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private JFrame frame;
    private JTextArea record;
    private JTextArea message;
    private Socket socket;
    private PrintWriter writer;
    private Listener listener;//用于监听服务器是否发了消息

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }

    public Client() {
        frame = new JFrame("客户");
        JPanel panel = new JPanel();
        Font font = new Font ("宋体",Font.PLAIN, 16);//设定字体
        record = new JTextArea(18,55);
        record.setLineWrap(true);//自动换行
        record.setWrapStyleWord(true);//单词过长自动换行
        record.setFont(font);
        record.setEditable(false);
        JScrollPane rScroller = new JScrollPane(record);
        rScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//垂直滚动
        rScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//禁止水平滚动

        message = new JTextArea(6,55);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(font);
        JScrollPane mScroller = new JScrollPane(message);
        mScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(new SendButtonListener());

        JLabel rLabel = new JLabel("聊天");
        panel.add(rLabel);
        panel.add(rScroller);
        panel.add(mScroller);
        panel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(500,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener());
    }

    public void connect() {
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 7000);
            InputStream is = socket.getInputStream();
            BufferedReader brNet = new BufferedReader(new InputStreamReader(is));
            writer = new PrintWriter(socket.getOutputStream(), true);
            listener = new Listener(brNet, record);
            new Thread(listener).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String sMessage = message.getText();
//            System.out.print(sMessage);
//            System.out.print("---");
            //去除首位空格，如果不全是空格才输出
            if (sMessage.trim().length() != 0) {
                message.setText("");
                writer.println(sMessage);
            }
        }
    }

    public class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            String title = "系统提示";
            String message = "确定要退出？";
            int option = JOptionPane.YES_NO_OPTION;
            int buttonValue = JOptionPane.showConfirmDialog(frame, message, title, option);
            if (buttonValue == JOptionPane.YES_OPTION) {
                listener.setFlag(false);
                writer.println(" ");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.exit(0);
            }
        }
    }

}

