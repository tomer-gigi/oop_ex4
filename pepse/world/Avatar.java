package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * the class represents the game avatar, led by the player
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final String[] IDLE_PATHS  = new String[]{"assets/idle_0.png",
            "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png"};
    private static final String[] LEFT_TO_RIGHT_PATHS = new String[]{"assets/run_0.png",
            "assets/run_1.png", "assets/run_2.png", "assets/run_3.png", "assets/run_4.png"};
    private static final String[] UP_AND_DOWN_PATHS = new String[]{"assets/jump_0.png",
            "assets/jump_1.png", "assets/jump_2.png", "assets/jump_3.png"};
    private static final float WALK_ENERGY = -0.5f;
    private static final float IDLE_ENERGY = 1f;
    private static final float JUMP_ENERGY = -10;
    private static final float MAX_ENERGY = 100f;

    private float energy = 100;
    //    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private final AnimationRenderable idle;
    private final AnimationRenderable leftToRight;
    private final AnimationRenderable upAndDown; //used when the character goes up or down but not sideways
    private final Runnable IJumped;

    private final UserInputListener inputListener;

    /**
     * constructs a new Avatar
     * @param pos - position of the avatar
     * @param inputListener - the game's input listener object
     * @param imageReader - the game's imageReader object
     * @param jumpFunc - callback to the gameManager jump update function
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader, Runnable jumpFunc) {
        super(pos, Vector2.ONES.mult(50),
                new ImageRenderable(imageReader.readImage
                        (IDLE_PATHS[0], false).getImage()));
        this.IJumped = jumpFunc;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        idle = new AnimationRenderable(IDLE_PATHS,
                imageReader, false, 0.1f);
        upAndDown = new AnimationRenderable(UP_AND_DOWN_PATHS,
                imageReader, false, 0.1f);
        leftToRight = new AnimationRenderable(LEFT_TO_RIGHT_PATHS,
                imageReader, false, 0.1f);
        setTag("avatar");
    }

    /**
     * @return current energy of the avatar
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * increase the energy of the avatar
     * @param increase - amount to increase the energy
     */
    public void changeEnergy(float increase) {
        energy += increase;
        if (energy > MAX_ENERGY) {
            energy = MAX_ENERGY;
        }
    }

    /**
     * @return true if the avatar is not moving
     */
    private boolean isIdle() {
        return getVelocity().y() == 0 && getVelocity().x() == 0;
    }

    /**
     * @return true if the  avatar is moving to the left
     */
    private boolean isWalkingRightToLeft() {
        return getVelocity().x() > 0;
    }

    /**
     * @return true if the  avatar is moving to the right
     */
    private boolean isWalkingLeftToRight() {
        return getVelocity().x() < 0;
    }

    /**
     * @return true if the avatar goes up and down but not left to right
     */
    private boolean isUpAndDown() {
        return getVelocity().y() != 0 && getVelocity().x() == 0;
    }

    /**
     * update the avatar every deltaTime
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && getEnergy() > -WALK_ENERGY) {
            xVel -= VELOCITY_X;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && getEnergy() > -WALK_ENERGY) {
            xVel += VELOCITY_X;
        }
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 &&
                getEnergy() >= -JUMP_ENERGY) {
            transform().setVelocityY(VELOCITY_Y);
            IJumped.run();
            changeEnergy(JUMP_ENERGY);
        }
        if (isIdle()) {
            changeEnergy(IDLE_ENERGY);
            renderer().setRenderable(idle);
        }
        if (isWalkingLeftToRight()) { //including walking in the air
            changeEnergy(WALK_ENERGY);
            renderer().setRenderable(leftToRight);
            renderer().setIsFlippedHorizontally(true);
        }
        if (isWalkingRightToLeft()) {
            changeEnergy(WALK_ENERGY);
            renderer().setRenderable(leftToRight);
            renderer().setIsFlippedHorizontally(false);
        }
        if (isUpAndDown()) {
            renderer().setRenderable(upAndDown);
        }

    }
}