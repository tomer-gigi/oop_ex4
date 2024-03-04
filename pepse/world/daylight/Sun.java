package pepse.world.daylight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {


    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject sun = new GameObject(
                Vector2.ZERO,
                new Vector2(80f,80f),
                new OvalRenderable(Color.YELLOW)
        );
        float MinWindowDimension = Math.min(windowDimensions.x(), windowDimensions.y());
        sun.setCenter(windowDimensions.mult(0.5f).add(Vector2.UP.mult((MinWindowDimension-80f)/2)));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        return sun;
    }
}
