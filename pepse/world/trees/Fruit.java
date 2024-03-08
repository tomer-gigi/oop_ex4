package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;

import java.awt.*;

public class Fruit extends GameObject {
    private static final float GROW_TIME = 30f;
    private final GameObjectCollection gameObjectCollection;

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("avatar")) {
            ((Avatar) other).changeEnergy(10f);
            new ScheduledTask(
                    other,
                    GROW_TIME,
                    false,
                    () -> gameObjectCollection.addGameObject(this)
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
