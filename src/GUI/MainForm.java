package GUI;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    public MainForm(){
        setTitle("数据库GUI0");
        setLocation(500, 200);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 2));
        jPanel.setBorder(BorderFactory.createEmptyBorder()); // 设置无边框以消除默认边距

        JButton btn1 = new JButton("btn1");
        jPanel.add(btn1);

        add(jPanel); // 将面板添加到 MainForm 窗口中

        setVisible(true);
    }
    public static void main(String[] args) {
        new MainForm();
    }
}
