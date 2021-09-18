package RaceGame.TrackLayout;

import RaceGame.GameObject;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Track extends GameObject {

    private BufferedImage img;

    public Track(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            img = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("413Track.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    public void update(){}
    public void collision(Class c){}
    public void drawImage(Graphics2D g){
        Graphics2D g2b = (Graphics2D) g;
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2b.drawImage(img, x, y, null);
    }


}