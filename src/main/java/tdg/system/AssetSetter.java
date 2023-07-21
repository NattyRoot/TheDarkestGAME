package tdg.system;

import tdg.objects.KeyObject;
import tdg.ui.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objects[0] = new KeyObject();
        gp.objects[0].worldX = gp.getTileSize() * 18;
        gp.objects[0].worldY = gp.getTileSize() * 6;

        gp.objects[1] = new KeyObject();
        gp.objects[1].worldX = gp.getTileSize() * 13;
        gp.objects[1].worldY = gp.getTileSize() * 13;
    }
}
