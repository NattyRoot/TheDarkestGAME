package tdg.objects;

import tdg.ui.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision;

    public int worldX, worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
        int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

        if (
            worldX + gp.getTileSize() > gp.player.getWorldX() - gp.player.screenX &&
            worldX - gp.getTileSize() < gp.player.getWorldX() + gp.player.screenX &&
            worldY + gp.getTileSize() > gp.player.getWorldY() - gp.player.screenY &&
            worldY - gp.getTileSize() < gp.player.getWorldY() + gp.player.screenY
        ) {
            g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
        }
    }
}
