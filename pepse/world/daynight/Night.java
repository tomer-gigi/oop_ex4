package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A class that represents the night cycle in the game.
 */
public class Night {
    /**
     * The opacity of the sky during the midnight hours.
     */
    private static final float MIDNIGHT_OPACITY = 0.5f;
    /**
     * The opacity of the sky during the morning hours.
     */
    private static final float MORNING_OPACITY = 0f;


    /**
     * Creates a new night cycle
     * @param windowDimensions the dimensions of the game window
     * @param cycleLength the length of one day/night cycle in seconds
     * @return the new night object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");

        new Transition<>(
                night,
                night.renderer()::setOpaqueness,
                MORNING_OPACITY,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

        return night;
    }

}
