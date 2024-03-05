package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    float groundHeightAtX0;
    private final Vector2 windowDimensions;

    public Terrain( Vector2 windowDimensions, int seed){
        this.groundHeightAtX0 = windowDimensions.y()*(2f/3f);
        this.windowDimensions = windowDimensions;
    }
    public float groundHeightAt(float x) {
        return groundHeightAtX0; }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();

        for (int i = minX-2; i < maxX+2; i++) {
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                Block block = new Block(
                        new Vector2(
                                i*Block.SIZE,
                                groundHeightAtX0+j*Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(
                                BASE_GROUND_COLOR))
                );
                blocks.add(block);
            }

        }

         return blocks;
    }
}
