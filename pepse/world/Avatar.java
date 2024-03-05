package pepse.world;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private final ImageReader imageReader;
    private float energy = 100;
//    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private final AnimationRenderable idle;
    private final AnimationRenderable leftToRight;
    private final AnimationRenderable upAndDown; //used when the character goes up or down but not sideways


    private final UserInputListener inputListener;

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        //todo: make sure image rendering is done properly
        super(pos, Vector2.ONES.mult(50),
                new ImageRenderable(imageReader.readImage
                        ("assets/idle_0.png", false).getImage()));
        this.imageReader = imageReader;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        idle  = new AnimationRenderable(new String[] {"assets/idle_0.png",
                "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png"},
                imageReader, false, 0.1f);
        upAndDown = new AnimationRenderable(new String[]
                {"assets/jump_0.png","assets/jump_1.png", "assets/jump_2.png",   "assets/jump_3.png"},
                imageReader, false, 0.1f);
        leftToRight = new AnimationRenderable(new String[]
                {"assets/run_0.png","assets/run_1.png", "assets/run_2.png",
                "assets/run_3.png","assets/run_4.png"},imageReader, false, 0.1f);
    }
    public float getEnergy(){
        return energy;
    }

    public void changeEnergy(float increase){
        energy+=increase;
        if(energy>100){
            energy=100;
        }
    }
    private boolean isIdle(){
        return getVelocity().y() == 0&&getVelocity().x() == 0;
    }
    private boolean isWalking(){
        return getVelocity().x()!= 0;
    }

    /**
     * @return true if the avatar goes up and down but not left to right
     */
    private boolean isUpAndDown(){
        return getVelocity().y()!= 0 && getVelocity().x()==0;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)&&getEnergy()>0.5) {
            xVel -= VELOCITY_X;
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)&&getEnergy()>0.5) {
            xVel += VELOCITY_X;
        }
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && getEnergy()>=10){
            transform().setVelocityY(VELOCITY_Y);
            changeEnergy(-10);
        }if (isIdle()){
            changeEnergy(1);
            renderer().setRenderable(idle);
        }if (isWalking()){ //including walking in the air
            changeEnergy(-0.5f);
            renderer().setRenderable(leftToRight);
        }if (isUpAndDown()){
            renderer().setRenderable(upAndDown);
        }

    }
}