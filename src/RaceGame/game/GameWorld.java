package RaceGame.game;


import RaceGame.Collision.Collision;
import RaceGame.GameConstants;
import RaceGame.GameObject;
import RaceGame.KartBuild.Kart;
import RaceGame.KartBuild.KartControl;
import RaceGame.LaunchApp;
import RaceGame.TrackLayout.Track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

/**
 *
 * Damon Zhong
 * 920613600
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Kart k1;
    private Kart k2;
    ArrayList<GameObject> gameObjects = new ArrayList<>();
    private LaunchApp lf;
    static long tickCount = 0;
    private MapLoader newMap;
    private JFrame jf;


    public GameWorld(LaunchApp lf){
        this.lf = lf;
        newMap = new MapLoader(this);
    }


    @Override
    public void run(){
        try {
            while(true) {
                int i = 0;
                tickCount++;

                while (i < this.gameObjects.size()) {
                    this.gameObjects.get(i).update();
                    this.repaint();
                    i++;
                }
                this.repaint();

                for (i = 0; i < this.gameObjects.size(); i++) {
                    for (int j = i; j < this.gameObjects.size(); j++) {
                        GameObject obj1 = this.gameObjects.get(i);
                        GameObject obj2 = this.gameObjects.get(j);
                        Collision collision = new Collision(obj1, obj2);
                        collision.checkForCollision(); // Also looks ahead if its a Tank and  Wall or  Wall and Tank
                    }
                }
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                if (k1.resetRound() || k2.resetRound()) {
                    k1.respawnTank();
                    k2.respawnTank();
                    k1.setRound();
                    k2.setRound();
                }
                if(k1.getLaps() == 3 || k2.getLaps() == 3){
                    k1.setLaps();
                    k2.setLaps();
                    resetGame();
                    this.lf.setFrame("end");
                    System.out.println("GG Game Over xDDD");
                    return;
                }

            } //while statement close
        }
        catch(InterruptedException | IOException ignored){
            System.out.println(ignored);
        }

    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() throws IOException {
        k1.resetTank();
        k2.resetTank();

    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() throws IOException {
        this.jf = new JFrame("Formula Racer");
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);


        //load Map
        gameObjects.add(new Track(0, 0));
        newMap = new MapLoader(this);
        newMap.loadMap();

        int t1SpawnX = 640;
        int t1SpawnY = 30;
        k1 = new Kart(t1SpawnX, t1SpawnY, 0, 1);
        int t2SpawnX = 640;
        int t2SpawnY = 80;
        k2 = new Kart(t2SpawnX, t2SpawnY, 0, 2);

        KartControl tc1 = new KartControl(k1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        KartControl tc2 = new KartControl(k2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D); //adding control
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);

        gameObjects.add(k1);
        gameObjects.add(k2);
    }



    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        Graphics2D buffer = world.createGraphics();


        buffer.setColor(Color.LIGHT_GRAY);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        for (GameObject gameObject : gameObjects) {
            gameObject.drawImage(buffer);
        }

        int t1LocationX = k1.getX();
        int t2LocationX = k2.getX();
        int t1LocationY = k1.getY();
        int t2LocationY = k2.getY();


        if (t1LocationX < GameConstants.SCREEN_WIDTH / 4) {
            t1LocationX = GameConstants.SCREEN_WIDTH / 4;
        }
        if (t2LocationX < GameConstants.SCREEN_WIDTH / 4) {
            t2LocationX = GameConstants.SCREEN_WIDTH / 4;
        }
        if (t1LocationX > GameConstants.WORLD_WIDTH - GameConstants.SCREEN_WIDTH / 4) {
            t1LocationX = GameConstants.WORLD_WIDTH - GameConstants.SCREEN_WIDTH / 4;
        }
        if (t2LocationX > GameConstants.WORLD_WIDTH - GameConstants.SCREEN_WIDTH / 4) {
            t2LocationX = GameConstants.WORLD_WIDTH - GameConstants.SCREEN_WIDTH / 4;
        }
        if (t1LocationY < GameConstants.SCREEN_HEIGHT / 2) {
            t1LocationY = GameConstants.SCREEN_HEIGHT / 2;
        }
        if (t2LocationY < GameConstants.SCREEN_HEIGHT / 2) {
            t2LocationY = GameConstants.SCREEN_HEIGHT / 2;
        }
        if (t1LocationY > GameConstants.WORLD_HEIGHT - GameConstants.SCREEN_HEIGHT / 2) {
            t1LocationY = GameConstants.WORLD_HEIGHT - GameConstants.SCREEN_HEIGHT / 2;
        }
        if (t2LocationY > GameConstants.WORLD_HEIGHT - GameConstants.SCREEN_HEIGHT / 2) {
            t2LocationY = GameConstants.WORLD_HEIGHT - GameConstants.SCREEN_HEIGHT / 2;
        }

        //draw split screen and mini map
        BufferedImage left_Half = world.getSubimage(t2LocationX - GameConstants.SCREEN_WIDTH / 4, t2LocationY - GameConstants.SCREEN_HEIGHT / 2, 480, 672);
        BufferedImage right_Half = world.getSubimage(t1LocationX - GameConstants.SCREEN_WIDTH / 4, t1LocationY - GameConstants.SCREEN_HEIGHT / 2, 480, 672);
        BufferedImage miniMap = this.world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        g2.drawImage(left_Half, 0, 0, null);
        g2.drawImage(right_Half, GameConstants.SCREEN_WIDTH / 2 + 4, 0, null);
        g2.drawImage(miniMap.getScaledInstance(GameConstants.SCREEN_WIDTH / 8, GameConstants.SCREEN_HEIGHT / 5, BufferedImage.TYPE_INT_RGB), 432, GameConstants.SCREEN_HEIGHT / 2 - 60, null);

        //Show current lap on screen for each kart
        g2.setFont(new Font("Courier", Font.BOLD, 25));
        g2.setColor(Color.WHITE);
        g2.drawString("Lap " + k2.getLaps() + "/3", 30, GameConstants.SCREEN_HEIGHT / 10);
        g2.drawString("Lap " + k1.getLaps() + "/3", GameConstants.SCREEN_WIDTH / 2 + 30, GameConstants.SCREEN_HEIGHT / 10);
        if (k2.getCheckPoints() != 2) {
            g2.setColor((Color.WHITE));
        } else {
            g2.setColor((Color.CYAN));

        }

        //Show total check points for each kart
        g2.drawString("CP " + k2.getCheckPoints() + "/2", 30, GameConstants.SCREEN_HEIGHT / 10 + 30);
        if (k1.getCheckPoints() != 2) {
            g2.setColor((Color.WHITE));
        } else {
            g2.setColor((Color.CYAN));
        }
        g2.drawString("CP " + k1.getCheckPoints() + "/2", GameConstants.SCREEN_WIDTH / 2 + 30, GameConstants.SCREEN_HEIGHT / 10 + 30);

        // Displays red indicator to pit for each kart
        if (k2.getPitStopStatus()) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Courier", Font.BOLD, 22));
            g2.drawString("Speed Reduced", 30, GameConstants.SCREEN_HEIGHT / 10 + 50);

        }
        if (k1.getPitStopStatus()) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Courier", Font.BOLD, 22));
            g2.drawString("Speed Reduced", GameConstants.SCREEN_WIDTH / 2 + 30, GameConstants.SCREEN_HEIGHT / 10 + 50);

        }


    }

}
