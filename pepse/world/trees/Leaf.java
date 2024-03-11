package pepse.world.trees;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.Random;

/**
 * a leaf object on a given tree
 */
public class Leaf extends GameObject {
    private static final int SIZE = 30;
    private static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
    private static final Random random = new Random();
    private static final float RIGHT_ANGLE = 90f;
    private static final float ANGLE_STEP = 1f;
    private static final double PROBABILITY_TO_WIGGLE_ANGLE = 0.6;
    private static final double PROBABILITY_TO_INCREASE_SIZE = 0.5;
    private static final float MEAN_WIGGLE_ANGLE = 1.5f;
    private static final double PROBABILITY_TO_ROTATE_RIGHT = 0.5;

    /**
     * constructs a new leaf object
     * @param topLeftCorner - top left corner of the leaf block
     */
    Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
        setTag("leaf");
    }

    /**
     * sets a random angle to wiggle the leaf to
     */
    private void wiggleAngle() {
        double coinFlip = random.nextDouble();
        float Angle = (float) random.nextGaussian() + MEAN_WIGGLE_ANGLE;
        if (coinFlip < PROBABILITY_TO_ROTATE_RIGHT) {
            this.renderer().setRenderableAngle(this.renderer().getRenderableAngle() + Angle);
        } else {
            this.renderer().setRenderableAngle(this.renderer().getRenderableAngle() - Angle);
        }
    }

    /**
     * increases or decreases leaf dimensions in a random manner
     */
    private void wiggleDimensions() {
        double coinFlip = random.nextDouble();
        float wiggleSize = (float) random.nextGaussian() / 4;
        if (coinFlip < PROBABILITY_TO_INCREASE_SIZE) {
            this.setDimensions(this.getDimensions().add(Vector2.ONES.mult(wiggleSize)));
        } else {
            this.setDimensions(this.getDimensions().subtract(Vector2.ONES.mult(wiggleSize)));
        }
    }

    /**
     * sets the transition to be applied while jumping - 90 degrees angle spin
     */
    public void spin90() {
        new Transition<>(
                this,
                (Float angle) -> this.renderer().setRenderableAngle(angle),
                this.renderer().getRenderableAngle(),
                this.renderer().getRenderableAngle() + RIGHT_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                ANGLE_STEP,
                Transition.TransitionType.TRANSITION_ONCE,
                null
        );

    }

    /**
     * performs the wiggle procedure: randomly chooses to change dimension or perform a small rotation
     *
     */
     void wiggle() {
        Random random = new Random();
        double coinFlip = random.nextDouble();
        if (coinFlip < PROBABILITY_TO_WIGGLE_ANGLE) {
            wiggleAngle();
        } else if (coinFlip > PROBABILITY_TO_WIGGLE_ANGLE) {
            wiggleDimensions();
        }


    }
}
