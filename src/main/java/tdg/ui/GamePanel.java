package tdg.ui;

import tdg.config.ConfigurationReader;
import tdg.entity.Player;
import tdg.system.KeyHandler;
import tdg.tiles.TileManager;

import javax.swing.*;
import java.awt.*;

import static tdg.config.ConfigurationKey.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = ConfigurationReader.getInt(WORLD_TILE_PIXEL); // 16x16 tile
    final int scale = ConfigurationReader.getInt(WORLD_TILE_SCALE);

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = ConfigurationReader.getInt(WORLD_MAP_COLUMN);
    final int maxScreenRow = ConfigurationReader.getInt(WORLD_MAP_ROW);
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidht = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyHandler);

    // FPS
    final int FPS = ConfigurationReader.getInt(GAME_FPS);;
    TileManager tileM = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // With 60 FPS -> 0.016 sec
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            // Count time to know when to draw to match FPS count
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                /*
                 * 1. UPDATE: update the information such as character positions
                 */
                update();

                /*
                 * 2. DRAW: draw the screen with the updated informations
                 */
                repaint();

                /*
                 * 3. RESET DELTA: reset delta after drawing to know when newt image should be drawn
                 */
                delta--;

                // Count +1 frame drawn
                drawCount++;
            }

            // Print total images drawn in 1 sec (should be equal to FPS)
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol(){
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
