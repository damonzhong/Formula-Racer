package RaceGame.KartBuild;



import RaceGame.GameConstants;
import RaceGame.GameObject;
import RaceGame.TrackLayout.PitStop;
import RaceGame.LapChecker.CheckPoint;
import RaceGame.LapChecker.CheckPoint2;
import RaceGame.LapChecker.FinishLine;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 * added on by Damon Zhong
 */
public class Kart extends GameObject {


    private boolean onGravel;
    private boolean resetRound;
    private boolean needPitStop;
    private boolean checkPoint1 = false;
    private boolean checkPoint2 = false;
    private final int type;
    private int lapsCompleted;
    private int totalCheckPoints;
    private long lastCheckPoint;

    private final float horsePower = 350;
    private final float ROTATIONSPEED = 2.5f;

    private BufferedImage img;
    private BufferedImage kart2;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;



    public Kart(int x, int y, int angle, int type) {
        this.x = x;
        this.y = y;
        this.spawnX = x;
        this.spawnY = y;
        this.spawnAngle = angle;
        this.angle = angle;
        this.type = type;
        this.onGravel = true;
        this.resetRound = false;
        this.needPitStop = false;
        this.lapsCompleted = 0;
        this.totalCheckPoints = 0;

        try{
            this.img = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("ToroRoso_Kart.png")));
            this.kart2 = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("Mclaren_Kart.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public Rectangle getHitBoxx() {
        return new Rectangle(x + vx, y + vy, img.getWidth(), img.getHeight());
    }

    public int getLaps() {
        return this.lapsCompleted;
    }
    public void setLaps() {this.lapsCompleted = 0;}
    public int getCheckPoints() { return this.totalCheckPoints; }
    public boolean getPitStopStatus() {
        return needPitStop;
    }
    public void setOnGravel(boolean onGravel) {
        this.onGravel = onGravel;
    }

    public boolean resetRound() { return resetRound;}
    public void setRound(){ resetRound = false; }

    // Called after a tank dies
    public void respawnTank(){
        x = spawnX;
        y = spawnY;
        angle = spawnAngle;
    }
    public void resetTank(){
        this.x = spawnX;
        this.y = spawnY;

    }

    public void update() {

        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.turnLeft();
        }
        if (this.RightPressed) {
            this.turnRight();
        }

        onGravel = false;
        hitBox.setLocation(x, y);
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    private void moveBackwards() {
        vx = (int) Math.round((horsePower/150) * Math.cos(Math.toRadians(angle))) * -1; //multiplying by -1 because we are using x+vx and y+vy to test for offset bounds (thus, we don't have to deal with subtraction and addition of vx,vy to x separately)
        vy = (int) Math.round((horsePower/150) * Math.sin(Math.toRadians(angle))) * -1; //multiplying by -1 because we are using x+vx and y+vy to test for offset bounds (thus, we don't have to deal with subtraction and addition of vx,vy to x separately)
        if (onGravel) {
            vx = (int) Math.round((horsePower / 520) * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round((horsePower / 520) * Math.sin(Math.toRadians(angle)));
        }
        if (needPitStop) {
            vx = (int) Math.round((horsePower / 180) * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round((horsePower / 180) * Math.sin(Math.toRadians(angle)));
        }
        x += vx;
        y += vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round((horsePower/100) * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round((horsePower/100) * Math.sin(Math.toRadians(angle)));
        if (onGravel) {
            vx = (int) Math.round((horsePower / 500) * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round((horsePower / 500) * Math.sin(Math.toRadians(angle)));
        }
        if (needPitStop) {
            vx = (int) Math.round((horsePower / 135) * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round((horsePower / 135) * Math.sin(Math.toRadians(angle)));
        }
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);

    }

    private void turnLeft() {

        if (needPitStop) {
            this.angle -= this.ROTATIONSPEED - 0.3;
        } else {
            this.angle -= this.ROTATIONSPEED;
        }

    }
    private void turnRight() {

        if (needPitStop) {
            this.angle += this.ROTATIONSPEED - 0.3;
        } else {
            this.angle += this.ROTATIONSPEED;
        }

    }

    public void collision(Class c) {
        if (c.equals(Kart.class)) {
            x = x - 35 * vx;
            y = y - 35 * vy;
            vx = 0;
            vy = 0;
            needPitStop = true;
        }
        if (c.equals(PitStop.class)) {
            needPitStop = false;
        }
        if (c.equals(CheckPoint.class)) {
            if (totalCheckPoints == 0) {
                System.out.println("cp 1");
                this.totalCheckPoints = 1;
                checkPoint1 = true;
            }
        }
        if (c.equals(CheckPoint2.class)) {
            if (totalCheckPoints == 1) {
                System.out.println("cp 2");
                this.totalCheckPoints = 2;
                checkPoint2 = true;
            }
        }
        if (c.equals(FinishLine.class)) {
            if (checkPoint1 && checkPoint2) {
                switch (lapsCompleted) {
                    case 0:
                        lastCheckPoint = System.currentTimeMillis();
                        this.lapsCompleted = 1;
                        this.checkPoint1 = false;
                        this.checkPoint2 = false;
                        System.out.println("lap 1");
                        break;
                    case 1:
                        if (System.currentTimeMillis() - lastCheckPoint > 1800) {
                            lastCheckPoint = System.currentTimeMillis();
                            this.lapsCompleted = 2;
                            this.checkPoint1 = false;
                            this.checkPoint2 = false;
                            System.out.println("lap 2");

                        }
                        break;
                    case 2:
                        if (System.currentTimeMillis() - lastCheckPoint > 1800) {
                            lastCheckPoint = System.currentTimeMillis();
                            this.lapsCompleted = 3;
                            this.checkPoint1 = false;
                            this.checkPoint2 = false;
                            System.out.println("lap 3");
                        }
                        break;
                }
                totalCheckPoints = 0;
                System.out.println(lapsCompleted);
            }
        }

    }

    public void drawImage (Graphics2D g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 10.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        switch (type) {
            case 1:
               g2d.drawImage(this.img, rotation, null);
               break;
            case 2:
                g2d.drawImage(this.kart2, rotation, null);
                break;
        }
        g2d.setColor(Color.CYAN);
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }
    void toggleDownPressed() {
        this.DownPressed = true;
    }
    void toggleRightPressed() {
        this.RightPressed = true;
    }
    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() { this.UpPressed = false; }
    void unToggleDownPressed() { this.DownPressed = false; }
    void unToggleRightPressed() { this.RightPressed = false; }
    void unToggleLeftPressed() { this.LeftPressed = false; }
}

