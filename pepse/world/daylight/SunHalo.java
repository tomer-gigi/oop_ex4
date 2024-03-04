package pepse.world.daylight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {

    public static GameObject create(GameObject sun){
        GameObject sunHalo = new GameObject(
                Vector2.ZERO,
                new Vector2(100f,100f),
                new OvalRenderable(new Color(255, 255, 0, 20))
        );
        sunHalo.addComponent(
                (float deltaTime)-> sunHalo.setCenter(sun.getCenter())

        );
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
        return sunHalo;
    }

}
