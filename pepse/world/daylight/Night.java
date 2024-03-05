package pepse.world.daylight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    private static final float MIDNIGHT_OPACITY = 0.1f;
    private static final float MORNING_OPACITY = 0f;
    private static float cycleLength;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        Night.cycleLength = cycleLength;
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");

        new Transition<>(
                night,//thegameobjectbeingchanged
                night.renderer()::setOpaqueness, //themethodtocall
                MORNING_OPACITY, //initialtransitionvalue
                MIDNIGHT_OPACITY, //finaltransitionvalue
                Transition.CUBIC_INTERPOLATOR_FLOAT,//useacubicinterpolator
                cycleLength, //transitionfullyoverhalfaday
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,//ChooseappropriateENUMvalue
                null//nothingfurthertoexecuteuponreachingfinalvalu
        );

        return night;
    }

    public static float getCycleLength() {
        return cycleLength;
    }
}
