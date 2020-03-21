
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private JFrame frame;
    private JTextArea record;
    private JTextArea message;
    private Socket socket;

    private String host;
    private String name;
    private PrintWriter writer;
    private BufferedReader brNet;
    private Listener listener;//用于监听服务器是否发了消息

    public static void main(String[] args) {
        Client client = new Client();
    }


    public Client() {
        logPanel();
    }

    public void logPanel() {

        frame = new JFrame("登录界面");
        JPanel panel = new JPanel();
        Font font = new Font ("宋体",Font.PLAIN, 20);

        JLabel hostLabel = new JLabel("地址:");
        JLabel idLabel = new JLabel("账号:");
        JLabel passwordLabel = new JLabel("密码:");
        hostLabel.setFont(font);
        idLabel.setFont(font);
        passwordLabel.setFont(font);

        JTextField id = new JTextField(22);
        id.setFont(font);
        JPasswordField password = new JPasswordField(22);
        password.setFont(font);
        JTextField host = new JTextField(22);
        host.setFont(font);
        host.setText("127.0.0.1");//默认本地

        JButton log = new JButton("登录");
        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //先在本地检查一下
                    String logHost = host.getText();
                    String logId = id.getText();
                    String logPw = password.getText();
                    if ( logId.length() == 0 || logPw.length() == 0 || logHost.length() == 0) {
                        JOptionPane.showMessageDialog(null, "密码或账号或地址为空", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
//                        socket = new Socket(InetAddress.getByName("127.0.0.1"), 7000);
                        socket = new Socket(InetAddress.getByName(logHost), 7000);
                        brNet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer = new PrintWriter(socket.getOutputStream(), true);
                        writer.println("log");
                        writer.println(logId);
                        System.out.println(logId);
                        writer.println(logPw);
                        System.out.println(logPw);
                        String response[] = brNet.readLine().split(" ",2);
                        if (response[0].equals("ok")) {
                            frame.dispose();//顺序不能错
                            name = response[1];
                            talkPanel();
                        } else {
                            JOptionPane.showMessageDialog(null, "密码或账号错误", "错误", JOptionPane.ERROR_MESSAGE);
                            socket.close();
                        }
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "无法连接服务器", "错误", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton register = new JButton("注册");
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                registerPanel();
            }
        });

        panel.add(hostLabel);
        panel.add(host);
        panel.add(idLabel);
        panel.add(id);
        panel.add(passwordLabel);
        panel.add(password);
        panel.add(register);
        panel.add(log);

        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(300,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void registerPanel() {
        frame = new JFrame("注册界面");
        JPanel panel = new JPanel();
        Font font = new Font ("宋体",Font.PLAIN, 20);

        JLabel hostLabel = new JLabel("    地址:");
        JLabel idLabel = new JLabel("    账号:");
        JLabel nameLabel = new JLabel("    昵称:");
        JLabel passwordLabel = new JLabel("    密码:");
        JLabel passwordLabel1 = new JLabel("确认密码:");
        hostLabel.setFont(font);
        idLabel.setFont(font);
        nameLabel.setFont(font);
        passwordLabel.setFont(font);
        passwordLabel1.setFont(font);

        JTextField host = new JTextField(22);
        host.setFont(font);
        host.setText("127.0.0.1");//默认本地
        JTextField id = new JTextField(22);
        id.setFont(font);
        JTextField name = new JTextField(22);
        name.setFont(font);
        JPasswordField password = new JPasswordField(22);
        password.setFont(font);
        JPasswordField password1 = new JPasswordField(22);
        password1.setFont(font);


        JButton register = new JButton("注册");
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rPassword = password.getText();
                String rPassword1 = password1.getText();
                String rName = name.getText();
                String rId = id.getText();
                String rHost = host.getText();
                if (rPassword.length()*rName.length()*rId.length()*rHost.length() == 0 ) {
                    JOptionPane.showMessageDialog(null,"密码或昵称或地址或账号为空","错误",JOptionPane.ERROR_MESSAGE);
                } else if (!rPassword.equals(rPassword1)) {
                    JOptionPane.showMessageDialog(null,"两次密码不一样","错误",JOptionPane.ERROR_MESSAGE);
                } else if (hasSpace(rPassword) || hasSpace(rName) || hasSpace(rId) || hasSpace(rHost)){
                    JOptionPane.showMessageDialog(null,"密码或昵称或地址或账号有空格","错误", JOptionPane.ERROR_MESSAGE);
                } else if(rPassword.length() < 6 || rId.length() < 6) {
                    JOptionPane.showMessageDialog(null,"密码或账号少于六位","错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        socket = new Socket(rHost, 7000);
                        BufferedReader brNet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
                        writer.println("register");
                        writer.println(rId + " " + rName + " " + rPassword);
                        System.out.println(rId + " " + rName + " " + rPassword);
//                        writer.println(rId);
//                        System.out.println(rId);
//                        writer.println(rName);
//                        System.out.println(rName);
//                        writer.println(rPassword);
//                        System.out.println(rPassword);
                        String response = brNet.readLine();
                        if (response.equals("注册成功")) {
                            JOptionPane.showMessageDialog(null,response,"成功", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,response,"重复", JOptionPane.ERROR_MESSAGE);
                        }
                        socket.close();
                        System.out.println("注册断开");
                    } catch (UnknownHostException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,"连接服务器失败","错误", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
        JButton back = new JButton("返回登录界面");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                logPanel();
            }
        });

        panel.add(hostLabel);
        panel.add(host);
        panel.add(idLabel);
        panel.add(id);
        panel.add(nameLabel);
        panel.add(name);
        panel.add(passwordLabel);
        panel.add(password);
        panel.add(passwordLabel1);
        panel.add(password1);
        panel.add(back);
        panel.add(register);

        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(350,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void talkPanel() {
        frame = new JFrame(name);
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

        listener = new Listener(brNet, record);//到时候断开要用
        new Thread(listener).start();
    }

    public void connect() {
        try {
            BufferedReader brNet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            listener = new Listener(brNet, record);
            new Thread(listener).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean hasSpace(String string) {
        for (int i = 0 ;i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                return true;
            }
        }
        return false;
    }

    public class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String sMessage = message.getText();
            //去除首尾空格，如果不全是空格才输出
            if (sMessage.trim().length() != 0) {
                message.setText("");
                writer.println(sMessage);
            }
        }
    }

    //退出时的提示框
    public class WindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int buttonValue = JOptionPane.showConfirmDialog(null, "确定要退出？", "系统提示", JOptionPane.YES_NO_OPTION);
            if (buttonValue == JOptionPane.YES_OPTION) {
                frame.dispose();
                listener.setFlag(false);//关闭listener
                writer.println(" ");//向服务器发出断开请求
                try {
                    socket.close();//关掉socket
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        }
    }
}

