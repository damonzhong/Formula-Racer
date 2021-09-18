package RaceGame;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static javax.imageio.ImageIO.read;

public abstract class GameObject {


    protected int x;
    protected int y;
    protected int angle;
    public Rectangle hitBox;
    protected BufferedImage img;
    protected int spawnX;
    protected int spawnY;
    protected int spawnAngle;
    protected int vx;
    protected int vy;
    protected boolean collided;
    int height;
    int width;

    boolean active;
//    Rectangle hitBox;
//    BufferedImage img;

    public void setX(int x_to_set) {
        this.x = x_to_set;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y_to_set) {
        this.y = y_to_set;
    }

    public int getY() {
        return this.y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    public Rectangle getHitBox(){
        return (new Rectangle(this.x, this.y, img.getWidth(), img.getHeight()));
    }

    public boolean isActive() {
        return active;
    }

    public abstract void update();

    public abstract void drawImage(Graphics2D g);

    public abstract void collision(Class c) throws InterruptedException;

}
