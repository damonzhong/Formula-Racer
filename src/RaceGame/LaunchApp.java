package RaceGame;
import RaceGame.game.GameWorld;
import RaceGame.menus.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LaunchApp {
    private JPanel mainPanel;
    private JPanel startPanel;
    private GameWorld gamePanel;
    private JPanel endPanel;
    private JFrame jf;
    private CardLayout cl;


    public LaunchApp(){
        this.jf = new JFrame();
        this.jf.setTitle("FormulaRacer Game");
        this.jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void initStart() throws IOException {
        String startP = "start";
        String endP = "end";
        this.mainPanel = new JPanel();
        this.startPanel = new GamePanel(this, startP);
        this.gamePanel = new GameWorld(this);
        this.gamePanel.gameInitialize();
        this.endPanel = new GamePanel(this, endP);
        cl = new CardLayout();
        this.jf.setResizable(false);
        this.mainPanel.setLayout(cl);
        this.mainPanel.add(startPanel, "start");
        this.mainPanel.add(gamePanel, "game");
        this.mainPanel.add(endPanel, "end");
        this.jf.add(mainPanel);
        this.setFrame("start");
    }

    public void setFrame(String type){
        this.jf.setVisible(false); // hide the JFrame
        switch(type){
            case "start":
            case "end":
                this.jf.setSize(GameConstants.MENU_SCREEN_WIDTH,GameConstants.MENU_SCREEN_HEIGHT);
                break;
            case "game":
                this.jf.setSize(GameConstants.SCREEN_WIDTH,GameConstants.SCREEN_HEIGHT);
                (new Thread(this.gamePanel)).start();
                break;
        }
        this.cl.show(mainPanel, type);
        this.jf.setVisible(true);
    }


    public JFrame getJf() {
        return jf;
    }

    public void closeGame(){
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }

    public static void main( String[] args ) throws IOException {
        LaunchApp app = new LaunchApp();
        app.initStart();

    }
}
