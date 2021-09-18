package RaceGame.game;

import RaceGame.LapChecker.CheckPoint;
import RaceGame.LapChecker.CheckPoint2;
import RaceGame.LapChecker.FinishLine;
import RaceGame.TrackLayout.Gravel;
import RaceGame.TrackLayout.PitStop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

//Load imgs from based on "Map" file onto game

public class MapLoader {
    private BufferedReader mapReader;
    private InputStreamReader inMap;
    private int numCols;
    private int numRows;
    private String row;
    private String[] mapInfo;
    private GameWorld gw;


    public MapLoader(GameWorld gw) {
        this.gw = gw;

        inMap = new InputStreamReader(Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("Map")));
        mapReader = new BufferedReader(inMap);

    }

    public void loadMap() throws IOException {

        row = mapReader.readLine();
        if (row == null) {
            throw new IOException("no data found in file");
        }
        mapInfo = row.split(",");
        numRows = Integer.parseInt(mapInfo[0]);
        numCols = Integer.parseInt(mapInfo[1]);

        for (int i = 0; i < numRows; i++) { //loops up and down(entire horizontal row)

            row = mapReader.readLine();
            mapInfo = row.split("\t");

            for (int j = 0; j < numCols; j++) {
                switch (mapInfo[0].charAt(j)) {
                    case '1':
                        gw.gameObjects.add(new Gravel(j * 128, i * 128));
                        break;
                    case '2':
                        gw.gameObjects.add(new PitStop(j * 128, i * 128));
                        break;
                    case '3':
                        gw.gameObjects.add(new CheckPoint(j * 128, i * 128, 3));
                        break;
                    case '4':
                        gw.gameObjects.add(new CheckPoint(j * 128, i * 128, 4));
                        break;
                    case '5':
                        gw.gameObjects.add(new FinishLine(j * 128, i * 128, 5));
                        break;
                    case '6':
                        gw.gameObjects.add(new FinishLine(j * 128, i * 128, 6));
                        break;
                    case '7':
                        gw.gameObjects.add(new CheckPoint2(j * 128, i * 128, 7));
                        break;
                    case '8':
                        gw.gameObjects.add(new CheckPoint2(j * 128, i * 128, 8));
                        break;

                }
            }
        }
    }
}
