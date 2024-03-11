package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.*;
import java.util.List;

import danogl.components.ScheduledTask;

/**
 * the class is used to create all the flora - leaves, fruits and trees
 */
public class Flora {

    private static final Color BASE_STUMP_COLOR = new Color(100, 50, 20);

    private static final int STUMP_HIGHT = 3;
    private static final int LEAF_RADIUS = 2;
    private static final float CHANCE_FOR_FRUIT = 0.03f;
    private static final float CHANCE_FOR_LEAF = 0.6f;
    private final Terrain terrain; // the terrain on which the flora is located
    private final GameObjectCollection gameObjectCollection; // game's object collection


    /**
     * constructor for the Flora class
     * @param terrain - the terrain on which the Flora is located
     * @param gameObjectCollection - the current game object collection
     */
    public Flora(Terrain terrain, GameObjectCollection gameObjectCollection) {

        this.terrain = terrain;
        this.gameObjectCollection = gameObjectCollection;
    }


    /**
     * creates flora in the specified range
      * @param minX - minimum x position
     * @param maxX - maximum x position
     * @return - a list of size 2 where list[0] is a list of blocks that compose the stumps
     * and list[1] holds the leaves
     */
    public List<List<GameObject>> createInRange(int minX, int maxX) {
        List<GameObject> stumpBlockList = new ArrayList<>();
        List<GameObject> leafBlockList = new ArrayList<>();
        int lastTree = -10;
        for (int i = minX; i < maxX; i++) {
            double coinFlip = Math.random();
            if (coinFlip < 0.2 && i - lastTree > 2) {
                List<GameObject> stump = createStump(i);
                List<GameObject> leafs = createLeafs(i);
                stumpBlockList.addAll(stump);
                leafBlockList.addAll(leafs);
                lastTree = i;
            }
        }
        List<List<GameObject>> stumpsAndLeafs = new ArrayList<>();
        stumpsAndLeafs.add(stumpBlockList);
        stumpsAndLeafs.add(leafBlockList);
        return stumpsAndLeafs;

    }

    /**
     * creating the leaves list
     * @param i - an X location
     * @return - list of blocks composing the leaves of a single tree
     */
    private List<GameObject> createLeafs(int i) {
        List<GameObject> blockList = new ArrayList<>();
        float Xstart = (float) (i - LEAF_RADIUS + 0.5) * Block.SIZE;
        int Ystart = (int) terrain.groundHeightAt((float) i) - (STUMP_HIGHT + LEAF_RADIUS + 1) * Block.SIZE;
        for (int j = 0; j < LEAF_RADIUS * 2; j++) {
            for (int k = 0; k < LEAF_RADIUS * 2; k++) {
                double coinFlip = Math.random();
                if (coinFlip < CHANCE_FOR_LEAF) {
                    Leaf leaf = new Leaf(
                            new Vector2(Xstart + j * Block.SIZE, Ystart + k * Block.SIZE));
                    new ScheduledTask(
                            leaf,
                            (float) Math.random(),
                            true,
                            leaf::wiggle
                    );
                    blockList.add(leaf);
                }
                if (coinFlip < CHANCE_FOR_FRUIT || coinFlip > 1-CHANCE_FOR_FRUIT) {

                    Fruit fruit = new Fruit(
                            new Vector2(Xstart + j * Block.SIZE, Ystart + k * Block.SIZE),
                            gameObjectCollection);
                    blockList.add(fruit);
                }
            }
        }
        return blockList;
    }

    /**
     * creates a list of blocks composing a single tree
     * @param blockIndex - the location X for the tree to be created
     * @return - the list of blocks
     */
     private List<GameObject> createStump(int blockIndex) {
        List<GameObject> blockList = new ArrayList<>();
        float startingHeight = terrain.groundHeightAt((float) blockIndex);
        for (int i = 1; i < STUMP_HIGHT + 1; i++) {
            Block stumpBlock = new Block(
                    new Vector2(blockIndex * Block.SIZE, startingHeight - i * Block.SIZE),
                    new RectangleRenderable(ColorSupplier.approximateColor(BASE_STUMP_COLOR))
            );
            stumpBlock.setTag(Integer.toString(blockIndex));
            blockList.add(stumpBlock);
        }
        return blockList;
    }
}
