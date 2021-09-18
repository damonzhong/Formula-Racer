package RaceGame.TrackLayout;

import RaceGame.GameObject;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;



public class Gravel extends GameObject {

    private BufferedImage img;

    public Gravel(int x, int y) {
        this.x = x;
        this.y = y;
        this.angle = 0;

        try{
            this.img = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("gravel.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.hitBox = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());

    }

   public void drawImage(Graphics2D g2) {
        Graphics2D g2b = (Graphics2D) g2;
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2b.drawImage(img, x, y, null);

    }

    public void update(){
//        if (currLives <= 0){
//            hasCollided();
//            hitBox.setLocation(x, y);
//        }
    }
    public void collision(Class c){
        //*** use this later to handle deceleration for kart ****//
//
//        if (c.equals(Bullet.class)){
//            if (this.type == 2) {
//                currLives = currLives - 6;
//            }
//        }
    }



}
