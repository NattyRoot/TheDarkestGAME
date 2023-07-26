package tdg.system;

import tdg.objects.BootsObject;
import tdg.objects.DoorObject;
import tdg.objects.KeyObject;
import tdg.ui.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objects[0] = new KeyObject();
        gp.objects[0].worldX = gp.getTileSize() * 10;
        gp.objects[0].worldY = gp.getTileSize() * 6;

        gp.objects[1] = new KeyObject();
        gp.objects[1].worldX = gp.getTileSize() * 13;
        gp.objects[1].worldY = gp.getTileSize() * 13;

        gp.objects[2] = new DoorObject();
        gp.objects[2].worldX = gp.getTileSize() * 15;
        gp.objects[2].worldY = gp.getTileSize() * 7;

        gp.objects[3] = new BootsObject();
        gp.objects[3].worldX = gp.getTileSize() * 18;
        gp.objects[3].worldY = gp.getTileSize() * 7;
    }
}
