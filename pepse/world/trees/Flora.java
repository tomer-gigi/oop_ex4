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

public class Flora {

    private static final Color BASE_STUMP_COLOR = new Color(100, 50, 20);

    private static final int STUMP_HIGHT = 3;
    private static final int LEAF_RADIUS = 2;


    private final Terrain terrain;
    private final GameObjectCollection gameObjectCollection;


    public Flora(Terrain terrain, GameObjectCollection gameObjectCollection) {

        this.terrain = terrain;
        this.gameObjectCollection = gameObjectCollection;
    }


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

    private List<GameObject> createLeafs(int i) {
        List<GameObject> blockList = new ArrayList<>();
        float Xstart = (float) (i - LEAF_RADIUS + 0.5) * Block.SIZE;
        int Ystart = (int) terrain.groundHeightAt((float) i) - (STUMP_HIGHT + LEAF_RADIUS + 1) * Block.SIZE;
        for (int j = 0; j < LEAF_RADIUS * 2; j++) {
            for (int k = 0; k < LEAF_RADIUS * 2; k++) {
                double coinFlip = Math.random();
                if (coinFlip < 0.6) {
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
                if (coinFlip < 0.015 || coinFlip > 0.985) {

                    Fruit fruit = new Fruit(
                            new Vector2(Xstart + j * Block.SIZE, Ystart + k * Block.SIZE),
                            gameObjectCollection);
                    blockList.add(fruit);
                }
            }
        }
        return blockList;
    }

    public List<GameObject> createStump(int blockIndex) {
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
