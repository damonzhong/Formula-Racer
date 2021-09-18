package RaceGame.LapChecker;

import RaceGame.GameObject;
import RaceGame.game.GameWorld;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class CheckPoint extends GameObject {

    private BufferedImage CPBottom;
    private BufferedImage CPTop;
    private BufferedImage CPLeft;
    private BufferedImage CPRight;
    private int type;


    public CheckPoint(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;

        try {
            this.CPBottom = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("CPBottom.png")));
            this.CPTop = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("CPTop.png")));
            this.CPLeft = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("CPLeft.png")));
            this.CPRight = read(Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("CPRight.png")));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            this.hitBox = new Rectangle(x, y, 5, CPTop.getHeight());

    }

    public void update(){}
    public void collision(Class c){

    }

    public void drawImage(Graphics2D g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        rotation.rotate(Math.toRadians(angle), this.CPTop.getWidth() / 2.0, this.CPTop.getHeight() / 2.0);
        switch (type) {
            case 3:
                g2d.setColor(Color.BLACK);
                g2d.drawRect(this.x, this.y, 15, CPTop.getHeight());
                g2d.drawImage(this.CPTop, rotation, null);
                break;
            case 4:
                g2d.setColor(Color.BLACK);
                g2d.drawRect(this.x, this.y, 15, CPTop.getHeight());
                g2d.drawImage(this.CPBottom, rotation, null);
                break;

        }
    }

}
