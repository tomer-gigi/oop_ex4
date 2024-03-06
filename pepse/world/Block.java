package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.components.RendererComponent;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Block extends GameObject {
    private final RectangleRenderable renderable;
    int r;
    int g;
    int b;
    public static final int SIZE = 30;
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.renderable = (RectangleRenderable) renderable;
    }
//    public void changeColor(){
//        this.renderer().setRenderable(new RectangleRenderable());
//    }
}

