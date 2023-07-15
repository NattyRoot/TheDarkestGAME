package tdg.entity;

import tdg.system.KeyHandler;
import tdg.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private final GamePanel gp;
    private final KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.keyHandler = kh;

        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);

        solidArea = new Rectangle(8, 24, gp.getTileSize() - 16, gp.getTileSize() - 16);

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_up_run_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_up_run_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_down_run_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_down_run_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_left_run_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_left_run_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_right_run_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/bonhomme_right_run_2.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.isKeyPressed()) {
            if (keyHandler.isUpPressed()) {
                direction = "up";
            }
            if (keyHandler.isDownPressed()) {
                direction = "down";
            }
            if (keyHandler.isLeftPressed()) {
                direction = "left";
            }
            if (keyHandler.isRightPressed()) {
                direction = "right";
            }

            // Check collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // If collision is false, then player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;

            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    yield up1;
                } else {
                    yield up2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    yield left1;
                } else {
                    yield left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    yield right1;
                } else {
                    yield right2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    yield down1;
                } else {
                    yield down2;
                }
            }
            default -> null;
        };

        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }

    private void setDefaultValues() {
        worldX = gp.getTileSize()* 8;
        worldY = gp.getTileSize()* 8;
        speed = 4;
    }

    public final int screenX;
    public final int screenY;

}
