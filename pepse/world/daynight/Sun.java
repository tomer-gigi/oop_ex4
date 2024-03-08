package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {


    private static final Vector2 SUN_DIMENSIONS = new Vector2(80f, 80f);
    private static final float INITIAL_ANGLE_IN_SKY = 0f;
    private static final float FINAL_ANGLE_IN_SKY = 360f;

    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject sun = new GameObject(
                Vector2.ZERO,
                SUN_DIMENSIONS,
                new OvalRenderable(Color.YELLOW)
        );
        float MinWindowDimension = Math.min(windowDimensions.x(), windowDimensions.y());
        Vector2 halfWindow = windowDimensions.mult(0.5f);
        sun.setCenter(
                halfWindow.
                        add(Vector2.UP.
                                mult((MinWindowDimension - SUN_DIMENSIONS.y()) / 2)));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        Vector2 initialSunCenter = sun.getCenter();
        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter(
                        initialSunCenter.subtract(halfWindow)
                                .rotated(angle)
                                .add(halfWindow)
                ),
                INITIAL_ANGLE_IN_SKY,
                FINAL_ANGLE_IN_SKY,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );
        return sun;
    }
}
