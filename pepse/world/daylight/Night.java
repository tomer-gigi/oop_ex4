package pepse.world.daylight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {

    private static float cycleLength;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Night.cycleLength = cycleLength;
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag("night");
        return sky;
    }

    public static float getCycleLength() {
        return cycleLength;
    }
}
