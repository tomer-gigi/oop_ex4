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

/**
 * this class represents the fruit, that are hanging on the trees and give energy to the avatar
 */
public class Fruit extends GameObject {
    private static final float GROW_TIME = 30f;
    private static final float ENERGY_INCREASE = 10f;
    private final GameObjectCollection gameObjectCollection;

    /**
     * specifies the behavior on collision - in this case - increase
     * Avatar's energy level by 10
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("avatar")) {
            ((Avatar) other).changeEnergy(ENERGY_INCREASE);
            new ScheduledTask(
                    other,
                    GROW_TIME,
                    false,
                    () -> gameObjectCollection.addGameObject(this)
            );
            this.gameObjectCollection.removeGameObject(this);
        }

    }

    /**
     * Construct a new collision a new Fruit instance
     * @param topLeftCorner - top left corner of the fruit
     * @param gameObjectCollection - the game's object collection
     */
    Fruit(Vector2 topLeftCorner, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, Vector2.ONES.mult(Block.SIZE),
                new OvalRenderable(Color.RED));
        this.gameObjectCollection = gameObjectCollection;
        setTag("fruit");
    }
}
