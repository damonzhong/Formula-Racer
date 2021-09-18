package RaceGame.LapChecker;

import RaceGame.GameObject;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class CheckPoint2 extends GameObject {

    private BufferedImage CPLeft;
    private BufferedImage CPRight;
    private int type;

    public CheckPoint2(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;

        try {

            this.CPLeft = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("CPLeft.png")));
            this.CPRight = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("CPRight.png")));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.hitBox = new Rectangle(x, y, CPLeft.getWidth(), 5);

    }

    public void update(){}
    public void collision(Class c){

    }

    public void drawImage(Graphics2D g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        rotation.rotate(Math.toRadians(angle), this.CPLeft.getWidth() / 2.0, this.CPLeft.getHeight() / 2.0);
        switch (type) {
            case 7:
                g2d.drawImage(this.CPLeft, rotation, null);
                break;
            case 8:
                g2d.drawImage(this.CPRight, rotation, null);
                break;
        }
    }

}
