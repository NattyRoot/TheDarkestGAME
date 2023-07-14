package tdg.map;

import tdg.ui.GamePanel;

import java.awt.*;
import java.util.Random;

public class MapGenerator {

    GamePanel gp;

    Map map;

    public MapGenerator(GamePanel gp, int maxCol, int maxRow) {
        this.gp = gp;
        generateMap(maxCol, maxRow);
    }

    private void generateMap(int maxCol, int maxRow) {
        Random random = new Random();
        int startX = random.nextInt(10);
        int startY = random.nextInt(10);
        int endX = random.nextInt(10);
        int endY = random.nextInt(10);

        // FIXME: C'est naze cette rectification
        // If start and end are at the same place, move endX by one
        if (endX == startX && endY == startY) {
            if (endX == maxCol) {
                endX--;
            } else {
                endX++;
            }
        }

        map = new Map(maxCol, maxRow, startX, startY, endX, endY);
    }

    /*
     * TEST PURPOSE ONLY
     *
     * Dessine la map visuellement sur le game panel
     */
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < map.map.length && row < map.map[0].length) {

            if (col == map.startX && row == map.startY) {
                g2.setColor(Color.RED);
            } else if (col == map.endX && row == map.endY) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.WHITE);
            }

            g2.fillRect(x, y, gp.getTileSize(), gp.getTileSize());
            col++;
            x += gp.getTileSize();

            if (col == map.map.length) {
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();
            }

        }
    }
}
