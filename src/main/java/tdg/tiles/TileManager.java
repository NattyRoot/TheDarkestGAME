package tdg.tiles;

import tdg.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];

        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];

        loadTileImage();
        loadMap();
    }

    public void loadMap() {

        try (InputStream is = getClass().getResourceAsStream("/maps/mapTest.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // On remplit toutes les lignes
            while (row < gp.getMaxScreenRow()) {
                // Lecture de la ligne suivante
                String line = br.readLine();

                // On remplit toutes les colonnes de la ligne
                while (col < gp.getMaxScreenCol()) {
                    // On récupère le numéro de la ligne et colonne en cours
                    int num = Integer.parseInt(line.split(" ")[col]);

                    // On inscrit ce numéro dans notre mapTileNum
                    mapTileNum[col][row] = num;

                    // On passe à la colonne suivante
                    col++;
                }

                // On retourne à la première colonne et on passe à la ligne suivante
                col = 0;
                row++;
            }

            // Quand on a fini de lire le fichier, on ferme le BufferedReader pour rendre la mémoire alloué !
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/florTiles/wooden.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/florTiles/florRock.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/rock.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.getTileSize(), gp.getTileSize(), null);
            col++;
            x += gp.getTileSize();

            if (col == gp.getMaxScreenCol()) {
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();
            }

        }
    }
}
