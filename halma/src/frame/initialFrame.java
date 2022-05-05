package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class initialFrame extends JFrame{

    static boolean isClicked;
    private int width;
    private int height;
    private static Image image;
    private JButton button2;
    private JButton button4;
    private JButton button5;
    private JLabel pic;

    public class PanelBackground extends JPanel{
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon backgroundicon = new ImageIcon("src\\frame\\SharedScreenshot.jpg");
            Image backgroungimage = backgroundicon.getImage();
            g.drawImage(backgroungimage, 0, 0,this.getWidth(), this.getHeight(), this);
        }
    }

    public initialFrame(String title, int width, int height) {
        super(title);
        this.width = width;
        this.height = height;
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置初始化界面大小，设置图标
        Image image = new ImageIcon("src\\frame\\f0fcd4e90d41ac412bf918b14d31077b.png").getImage();
        this.setIconImage(image);
        this.setSize(width, height);
        JMenuBar menuBar=new JMenuBar();
        menuBar.add(new JMenu("文件"));
        menuBar.setVisible(true);


        //设置面板按钮
        button2 = new JButton("  2 Players  ");
        button4 = new JButton("  4 Players  ");
        button5 = new JButton("  加载游戏  ");

        button2.setBackground(new Color(0xFFF8B7));
        button4.setBackground(new Color(0xFFF8B7));
        button5.setBackground(new Color(0xFFF8B7));

        JPanel contentPanel = new PanelBackground();
        SpringLayout layout=new SpringLayout();
        contentPanel.setLayout(layout);
        contentPanel.add(button2);
        contentPanel.add(button4);
        contentPanel.add(button5);
        

        layout.putConstraint(SpringLayout.WEST,button4,342,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.WEST,button2,342,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.WEST,button5,342,SpringLayout.WEST,this);

        layout.putConstraint(SpringLayout.NORTH,button2,350,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.NORTH,button4,35,SpringLayout.SOUTH,button2);
        layout.putConstraint(SpringLayout.NORTH,button5,35,SpringLayout.SOUTH,button4);

        //layout.putConstraint(SpringLayout.SOUTH,button5,-10,SpringLayout.NORTH,button4);

        contentPanel.setVisible(true);


        //contentPanel.add(menuBar);
        this.add(contentPanel);

    }



    public void setWidth(int width) {
        this.width = width;
    }


    public JButton getButton2() {
        return button2;
    }

    public void setButton2(JButton button2) {
        this.button2 = button2;
    }

    public JButton getButton4() {
        return button4;
    }

    public void setButton4(JButton button4) {
        this.button4 = button4;
    }

    public JButton getButton5() {
        return button5;
    }

    public void setButton5(JButton button5) {
        this.button5 = button5;
    }
}
