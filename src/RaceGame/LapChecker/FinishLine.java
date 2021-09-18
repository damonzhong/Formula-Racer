package RaceGame.LapChecker;

import RaceGame.GameObject;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class FinishLine extends GameObject {

    private BufferedImage img;
    private BufferedImage FinishTop;
    private int type;


    public FinishLine(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.angle = 0;

        try {
            this.img = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("FinishBottom.png")));
            FinishTop = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("FinishTop.png")));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (type == 5) {
            this.hitBox = new Rectangle(x, y, FinishTop.getWidth(), FinishTop.getHeight());
        } else {
            this.hitBox = new Rectangle(x, y, img.getWidth(), img.getHeight());
        }
    }

    public void update(){}

    public void collision(Class c){

    }

    public void drawImage(Graphics2D g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        switch (type) {
            case 5:
                g2d.drawImage(FinishTop, rotation, null);
            break;
            case 6:
                g2d.drawImage(this.img, rotation, null);
                break;
        }
    }
}
