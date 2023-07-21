package tdg.system;

import tdg.entity.Entity;
import tdg.ui.GamePanel;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Coordonnées en pixels de la hitbox de l'entity
        int entityLeftWorldX = entity.getWorldX() + entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldY() + entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

        // Coordonnées en colonnes et lignes de la histbox de l'entity
        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        // If entity is going up, check upward collisions
        if (entity.directions.contains("up")) {
            entityTopRow = (entityTopWorldY - entity.speed) / gp.getTileSize();
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionUp = true;
            }
        }

        // If entity is going down, check downward collisions
        if (entity.directions.contains("down")) {
            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.getTileSize();
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionDown = true;
            }
        }

        // If entity is going left, check left collisions
        if (entity.directions.contains("left")) {
            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.getTileSize();
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionLeft = true;
            }
        }

        // If entity is going right, check right collisions
        if (entity.directions.contains("right")) {
            entityRightCol = (entityRightWorldX + entity.speed) / gp.getTileSize();
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }
    }
}
