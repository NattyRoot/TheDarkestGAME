package tdg.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected int worldX, worldY;
    public int speed;
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public List<String> directions = new ArrayList<>();

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    public Rectangle solidArea;

    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionUp, collisionDown, collisionLeft, collisionRight;

    /*
     * Getters ("Accesseur" en français) allows code from other packages to access private values but they can't update them
     * Setters ("Mutateurs" en français) allows code from other packages to update private values
     *
     * As a famous French rapper once said :
     *      "J'vais te mettre 3 set si tu fait pas tes setters
     *              Fait gaffe à toi je te guette grâce à mes accesseurs"
     *
     * French is so beautiful
     *
     * Il est vachement drôle le Félix de 2023 non ?
     *
     * De ouf, j'ai ri, j'avoue ! :')
     */
    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
}
