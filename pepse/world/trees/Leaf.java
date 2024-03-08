package pepse.world.trees;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.Random;

public class Leaf extends GameObject {
    public static final int SIZE = 30;
    private static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
    private static final Random random = new Random();
    private static final float NINETY = 90f;
    private static final float ANGLE_STEP = 1f;
    private static final double PROBABILITY_TO_WIGGLE_ANGLE = 0.6;
    private static final double PROBABILITY_TO_INCRESE_SIZE = 0.5;
    private static final float MEAN_WIGGLE_ANGLE = 1.5f;
    private static final double PROBABILITY_TO_ROTATE_RIGHT = 0.5;

    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
        setTag("leaf");
    }

    public void wiggleAngle() {
        double coinFlip = random.nextDouble();
        float Angle = (float) random.nextGaussian() + MEAN_WIGGLE_ANGLE;
        if (coinFlip < PROBABILITY_TO_ROTATE_RIGHT) {
            this.renderer().setRenderableAngle(this.renderer().getRenderableAngle() + Angle);
        } else  {
            this.renderer().setRenderableAngle(this.renderer().getRenderableAngle() - Angle);
        }
    }

    public void wiggleDimensions() {
        double coinFlip = random.nextDouble();
        float wiggleSize = (float) random.nextGaussian() / 4;
        if (coinFlip < PROBABILITY_TO_INCRESE_SIZE) {
            this.setDimensions(this.getDimensions().add(Vector2.ONES.mult(wiggleSize)));
        } else {
            this.setDimensions(this.getDimensions().subtract(Vector2.ONES.mult(wiggleSize)));
        }
    }

    public void spin90() {
        new Transition<>(
                this,
                (Float angle) -> this.renderer().setRenderableAngle(angle),
                this.renderer().getRenderableAngle(),
                this.renderer().getRenderableAngle() + NINETY,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                ANGLE_STEP,
                Transition.TransitionType.TRANSITION_ONCE,
                null
        );

    }

    public void wiggle() {
        Random random = new Random();
        double coinFlip = random.nextDouble();
        if (coinFlip < PROBABILITY_TO_WIGGLE_ANGLE) {
            wiggleAngle();
        } else if (coinFlip > PROBABILITY_TO_WIGGLE_ANGLE) {
            wiggleDimensions();
        }


    }
}
