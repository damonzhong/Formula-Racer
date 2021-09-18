package RaceGame.TrackLayout;

import RaceGame.GameObject;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;
import java.io.IOException;
import static javax.imageio.ImageIO.read;

public class PitStop extends GameObject {

    public PitStop(int x, int y){
        this.x = x;
        this.y = y;

        try {
            this.img = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("mechanic_white.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.hitBox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    public void update(){}
    public void collision(Class c){}

    public void drawImage(Graphics2D g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

}
