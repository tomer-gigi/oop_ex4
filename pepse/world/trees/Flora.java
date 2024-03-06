package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Flora {
    public static class Fruit extends GameObject {
        private final GameObjectCollection gameObjectCollection;

        @Override
        public void onCollisionEnter(GameObject other, Collision collision) {
            super.onCollisionEnter(other, collision);
            if(other.getTag().equals("avatar")){
                ((Avatar)other).changeEnergy(10f);
                new ScheduledTask(
                        other,
                        30f,
                        false,
                        () ->gameObjectCollection.addGameObject(this)
                );
                this.gameObjectCollection.removeGameObject(this);
            }

        }

        public Fruit(Vector2 topLeftCorner, GameObjectCollection gameObjectCollection) {
            super(topLeftCorner, Vector2.ONES.mult(Block.SIZE),
                    new OvalRenderable(Color.RED));
            this.gameObjectCollection = gameObjectCollection;
            setTag("fruit");
        }
    }

    public static class Leaf extends GameObject {
        public static final int SIZE = 30;
        private static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
        public Leaf(Vector2 topLeftCorner) {
            super(topLeftCorner,Vector2.ONES.mult(SIZE), new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
            setTag("leaf");
        }
    
        public void wiggleAngle(){
            Random random = new Random();
            double coinFlip = random.nextDouble();
            if (coinFlip<0.2){
                this.renderer().setRenderableAngle(this.renderer().getRenderableAngle()+1.5f);}
            else if (coinFlip>0.8) {
                this.renderer().setRenderableAngle(this.renderer().getRenderableAngle()-1.5f);
            }
        }
        public void wiggleDimensions(){
            Random random = new Random();
            double coinFlip = random.nextDouble();
            if (coinFlip<0.5){
                this.setDimensions(this.getDimensions().add(Vector2.ONES.mult(0.001F)));
            }
            else if (coinFlip>0.5) {
                this.setDimensions(this.getDimensions().subtract(Vector2.ONES.mult(0.001f)));
            }
        }
        public void spin90(){
            new Transition<>(
                    this,//thegameobjectbeingchanged
                    (Float angle)-> this.renderer().setRenderableAngle(angle), //themethodtocall
                    this.renderer().getRenderableAngle(), //initialtransitionvalue
                    this.renderer().getRenderableAngle()+90f, //finaltransitionvalue
                    Transition.LINEAR_INTERPOLATOR_FLOAT,//useacubicinterpolator
                    1f, //transitionfullyoverhalfaday
                    Transition.TransitionType.TRANSITION_ONCE,//ChooseappropriateENUMvalue
                    null//nothingfurthertoexecuteuponreachingfinalvalu
            );

        }
        public void wiggle(){
            Random random = new Random();
            double coinFlip = random.nextDouble();
            if (coinFlip<0.5){
                wiggleAngle();
            }
            else if (coinFlip>0.5) {
                wiggleDimensions();
            }
    
    
        }
    }
}
