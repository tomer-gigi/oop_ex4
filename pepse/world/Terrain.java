package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * the terrain is composed of blocks and is te ground on which the avatar walks and the trees are found
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final int NOISE_FACTOR = 200;
    private static final int SCREEN_BUFFER = 2;
    float groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;

    /**
     * creates a terrain which will allow us to create blocks of terrain
     * @param windowDimensions - dimension of the current game's window
     * @param seed - to enable pseudorandomality
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = windowDimensions.y() * (2f / 3f);
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);


    }

    /**
     * returns the ground height at a given X point
     * @param x - X position to see height of
     * @return - height in this point
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * NOISE_FACTOR);
        return groundHeightAtX0 + noise;
    }

    /**
     * creates a list of blocks - where the blocks are the components of the terrain
     * @param minX  - minimal X to start from
     * @param maxX - maximal X to finish in
     * @return  list of blocks composing the terrain
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        for (int blockIndex = minX - SCREEN_BUFFER; blockIndex <= maxX + SCREEN_BUFFER; blockIndex++) {
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                Block block = new Block(
                        new Vector2(
                                blockIndex * Block.SIZE,
                                groundHeightAt((float) blockIndex) + j * Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(
                                BASE_GROUND_COLOR))
                );
                blocks.add(block);
            }

        }
        return blocks;
    }
}
