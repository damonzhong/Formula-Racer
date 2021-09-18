package RaceGame.menus;

import RaceGame.LaunchApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel{
    private String panel;
    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private LaunchApp lf;


    public GamePanel(LaunchApp lf, String panel) {
        this.lf = lf;
        this.panel = panel;

        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("FormulaTitle.jpg"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        if (panel == "start") {
            start = new JButton("Start");
            start.setFont(new Font("Courier New", Font.BOLD, 24));
            start.setBounds(150, 300, 150, 50);
            start.addActionListener((actionEvent -> {
                this.lf.setFrame("game");
            }));
        } else if (panel == "end") {
            start = new JButton("Restart Game");
            start.setFont(new Font("Courier New", Font.BOLD, 24));
            start.setBounds(150, 300, 175, 50);
            start.addActionListener((actionEvent -> {
                this.lf.setFrame("game");
            }));
        }

        exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 150, 50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(start);
        this.add(exit);
    }
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);

    }
}
