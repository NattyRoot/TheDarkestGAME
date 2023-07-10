package tdg.entity;

import tdg.system.KeyHandler;
import tdg.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private final GamePanel gp;
    private final KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.keyHandler = kh;

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.isKeyPressed()) {
            if (keyHandler.isUpPressed()) {
                direction = "up";
                y -= speed;
            }
            if (keyHandler.isDownPressed()) {
                direction = "down";
                y += speed;
            }
            if (keyHandler.isLeftPressed()) {
                direction = "left";
                x -= speed;
            }
            if (keyHandler.isRightPressed()) {
                direction = "right";
                x += speed;
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
            default -> {
                if (spriteNum == 1) {
                    yield down1;
                } else {
                    yield down2;
                }
            }
        };

        g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }

    private void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }
}
