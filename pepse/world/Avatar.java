package pepse.world;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private float energy = 100;
//    private static final Color AVATAR_COLOR = Color.DARK_GRAY;

    private final UserInputListener inputListener;

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        //todo: make sure image rendering is done properly
        super(pos, Vector2.ONES.mult(50),
                new ImageRenderable(imageReader.readImage
                        ("assets/idle_0.png", false).getImage()));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
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
        }if (isWalking()){ //including walking in the air
            changeEnergy(-0.5f);
        }

    }
}