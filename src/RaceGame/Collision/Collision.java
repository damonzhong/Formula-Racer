package RaceGame.Collision;


import RaceGame.GameObject;
import RaceGame.KartBuild.Kart;
import RaceGame.LapChecker.CheckPoint;
import RaceGame.LapChecker.CheckPoint2;
import RaceGame.LapChecker.FinishLine;
import RaceGame.TrackLayout.Gravel;
import RaceGame.TrackLayout.PitStop;

import java.awt.*;

public class Collision {
    private GameObject obj1, obj2;


     public Collision(GameObject obj1, GameObject obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public void checkForCollision() throws InterruptedException {

        //Handles Tank on Tank Collision
        if (obj1 instanceof Kart && obj2 instanceof Kart && !(((Kart) obj1).equals(((Kart) obj2))) ) {
            if (((Kart) obj1).hitBox.intersects(((Kart) obj2).hitBox)) {
                obj1.collision(obj2.getClass());
                obj2.collision(obj1.getClass());
            }
        }


        if (obj1 instanceof Gravel && obj2 instanceof Kart) {
            Rectangle r2 = ((Kart) obj2).getHitBoxx();
            if (r2.intersects(obj1.hitBox)) {
               ((Kart) obj2).setOnGravel(true);
            }
        }

        if (obj1 instanceof PitStop && obj2 instanceof Kart) {
            Rectangle tankPUP = ((Kart) obj2).getHitBoxx();
            if (tankPUP.intersects(obj1.hitBox)) {
                obj2.collision(obj1.getClass());
            }
        }

        if (obj1 instanceof CheckPoint && obj2 instanceof Kart) {
            Rectangle tankCP = ((Kart) obj2).getHitBoxx();
            if (tankCP.intersects(obj1.hitBox)) {
                obj2.collision(obj1.getClass());
            }
        }

        if (obj1 instanceof CheckPoint2 && obj2 instanceof Kart) {
            Rectangle tankCP = ((Kart) obj2).getHitBoxx();
            if (tankCP.intersects(obj1.hitBox)) {
                obj2.collision(obj1.getClass());
            }
        }

        if (obj1 instanceof FinishLine && obj2 instanceof Kart) {
            Rectangle tankCP = ((Kart) obj2).getHitBoxx();
            if (tankCP.intersects(obj1.hitBox)) {
                obj2.collision(obj1.getClass());
            }
        }

    }
}
