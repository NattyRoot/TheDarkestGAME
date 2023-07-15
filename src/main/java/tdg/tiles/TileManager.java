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

        tile = new Tile[50];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        loadTileImage();
        loadMap();
    }

    public void loadMap() {

        try (InputStream is = getClass().getResourceAsStream("/maps/bigMapTest.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // On remplit toutes les lignes
            while (row < gp.maxWorldRow) {
                // Lecture de la ligne suivante
                String line = br.readLine();

                // On remplit toutes les colonnes de la ligne
                while (col < gp.maxWorldCol) {
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
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/outmapTiles/sewer_outmap_void.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/outmapTiles/sewer_outmap_top.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/outmapTiles/sewer_outmap_right.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/outmapTiles/sewer_outmap_bottom.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/outmapTiles/sewer_outmap_left.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/floorTiles/sewer_ground.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/sewer_wall_floor.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/wallTiles/sewer_wall.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/floorTiles/sewer_water.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/floorTiles/sewer_down_water.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
            int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

            if (
                worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.screenX &&
                worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.screenX &&
                worldY + gp.getTileSize() > gp.player.getWorldY() - gp.player.screenY &&
                worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.screenY
            ) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }
            worldCol++;


            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
