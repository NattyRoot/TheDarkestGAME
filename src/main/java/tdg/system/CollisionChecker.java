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

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.objects.length; i++) {
            // If object is null do nothing
            if (gp.objects[i] == null) {
                continue;
            }

            // Get entity solid area position
            entity.solidArea.x = entity.getWorldX() + entity.solidArea.x;
            entity.solidArea.y = entity.getWorldY() + entity.solidArea.y;

            // Get object solid area position
            gp.objects[i].solidArea.x = gp.objects[i].worldX + gp.objects[i].solidArea.x;
            gp.objects[i].solidArea.y = gp.objects[i].worldY + gp.objects[i].solidArea.y;

            // Get solid area position after moving
            if (entity.directions.contains("up")) {
                entity.solidArea.y -= entity.speed;

                if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                    if (gp.objects[i].collision) {
                        entity.collisionUp = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
            if (entity.directions.contains("down")) {
                entity.solidArea.y += entity.speed;

                if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                    if (gp.objects[i].collision) {
                        entity.collisionDown = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
            if (entity.directions.contains("left")) {
                entity.solidArea.x -= entity.speed;

                if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                    if (gp.objects[i].collision) {
                        entity.collisionLeft = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
            if (entity.directions.contains("right")) {
                entity.solidArea.x += entity.speed;

                if (entity.solidArea.intersects(gp.objects[i].solidArea)) {
                    if (gp.objects[i].collision) {
                        entity.collisionRight = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }

            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;

            gp.objects[i].solidArea.x = gp.objects[i].solidAreaDefaultX;
            gp.objects[i].solidArea.y = gp.objects[i].solidAreaDefaultY;
        }

        return index;
    }
}
