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

    BufferedImage image = null;

    public final int screenX;
    public final int screenY;

    public int hasKeys = 0;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.keyHandler = kh;

        screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
        screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);

        solidArea = new Rectangle(8, 20, gp.getTileSize() - 16, gp.getTileSize() - 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

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
            // Update player directions according to the current pressed buttons
            updateDirections();

            // Set all collisions back to false
            resetCollision();

            // Check tile collision
            gp.collisionChecker.checkTile(this);

            // Check object collision
            int objectIndex = gp.collisionChecker.checkObject(this, true);

            pickupObject(objectIndex);

            // If there is no collision, player can move
            if (!collisionUp && keyHandler.isUpPressed()) {
                worldY -= speed;
            }
            if (!collisionDown && keyHandler.isDownPressed()) {
                worldY += speed;
            }
            if (!collisionLeft && keyHandler.isLeftPressed()) {
                worldX -= speed;
            }
            if (!collisionRight && keyHandler.isRightPressed()) {
                worldX += speed;
            }

            // Sprite animation
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
        if (directions.contains("right")) {
            if (spriteNum == 1) {
                image = right1;
            } else {
                image = right2;
            }
        }

        if (directions.contains("left")) {
            if (spriteNum == 1) {
                image = left1;
            } else {
                image = left2;
            }
        }

        if (directions.contains("up")) {
            if (spriteNum == 1) {
                image = up1;
            } else {
                image = up2;
            }
        }

         if (directions.contains("down")) {
            if (spriteNum == 1) {
                image = down1;
            } else {
                image = down2;
            }
        }

        // If there is no current directions, set the image to "down" by default
        if (image == null) {
            image = down1;
        }

        // Draw player
        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);

        // Draw hitbox
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

    private void setDefaultValues() {
        worldX = gp.getTileSize() * 8;
        worldY = gp.getTileSize() * 8;
        speed = 4;
    }

    private void resetCollision() {
        collisionUp = false;
        collisionDown = false;
        collisionLeft = false;
        collisionRight = false;
    }

    private void updateDirections() {
        // Reset all directions
        directions.remove("up");
        directions.remove("down");
        directions.remove("left");
        directions.remove("right");

        // Add directions according to the current pressed buttons
        if (keyHandler.isUpPressed()) {
            directions.add("up");
        }
        if (keyHandler.isDownPressed()) {
            directions.add("down");
        }
        if (keyHandler.isLeftPressed()) {
            directions.add("left");
        }
        if (keyHandler.isRightPressed()) {
            directions.add("right");
        }
    }

    public void pickupObject(int index) {
        if(index == 999) {
            return;
        }

        String objectName = gp.objects[index].name;

        switch (objectName) {
            case "key":
                hasKeys++;
                gp.objects[index] = null;
                break;
            case "door":
                if (hasKeys > 0) {
                    hasKeys--;
                    gp.objects[index] = null;
                }
                break;
        }
    }
}
