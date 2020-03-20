

import javax.swing.*;
import java.io.*;

public class Listener implements Runnable {
    private BufferedReader br;
    private Boolean flag;
    private JTextArea record;

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    Listener(BufferedReader br, JTextArea record) {
        this.br = br;
        this.record = record;
        flag = true;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                String string = br.readLine();
                System.out.println(string);
                record.append(string);
                record.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
