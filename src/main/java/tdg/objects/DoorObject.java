package tdg.objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends SuperObject {
    public DoorObject() {
        name = "door";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
