package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;



public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final int NOISE_FACTOR = 200;
    private static final int SCREEN_BUFFER = 2;
    float groundHeightAtX0;
    private final Vector2 windowDimensions;
    private final NoiseGenerator noiseGenerator;


    public Terrain( Vector2 windowDimensions, int seed){
        this.groundHeightAtX0 = windowDimensions.y()* (2f/3f);
        this.windowDimensions = windowDimensions;
        this.noiseGenerator = new NoiseGenerator(seed,(int)groundHeightAtX0);


    }
    public float groundHeightAt(float x) {        float noise = (float) noiseGenerator.noise(x, Block.SIZE * NOISE_FACTOR);
               return groundHeightAtX0 + noise;
    }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        for (int blockIndex = minX- SCREEN_BUFFER; blockIndex <= maxX+SCREEN_BUFFER; blockIndex++) {
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                Block block = new Block(
                        new Vector2(
                                blockIndex*Block.SIZE,
                                groundHeightAt((float)blockIndex)+j*Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(
                                BASE_GROUND_COLOR))
                );
                blocks.add(block);
            }

        }
         return blocks;
    }
}
