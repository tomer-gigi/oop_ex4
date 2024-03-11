package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;


import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * a block is a rectangle game object - that is special in the sense that it has a fixed size,
 * and no object can pass it over
 */
public class Block extends GameObject {
    /**
     * the size of every block of the game
     */
    public static final int SIZE = 30;

    /**
     * constructor of a new block instance - a game object with special physical properties
     * @param topLeftCorner - topLeftCorner of the block
     * @param renderable - renderable fo the object
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

}

