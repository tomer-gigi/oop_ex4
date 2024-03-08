package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {

    private static final Vector2 HALO_DIMENSION = new Vector2(100f, 100f);
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);

    public static GameObject create(GameObject sun) {
        GameObject sunHalo = new GameObject(
                Vector2.ZERO,
                HALO_DIMENSION,
                new OvalRenderable(HALO_COLOR)
        );
        sunHalo.addComponent(
                (float deltaTime) -> sunHalo.setCenter(sun.getCenter())

        );
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
        return sunHalo;
    }

}
