package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.*;
import java.util.List;
import danogl.components.ScheduledTask;
public class TreePlanter {

    private static final Color BASE_STUMP_COLOR = new Color(100, 50, 20);

    private static final int NOISE_FACTOR = 200;
    private static final int STUMP_HIGHT = 3;
    private static final int NUM_OF_TREES = 3;
    private static final int LEAF_RADIUS = 2;

    private final Vector2 windowDimensions;
    private final Terrain terrain;


    public TreePlanter(Vector2 windowDimensions, Terrain terrain) {
        this.windowDimensions = windowDimensions;
        this.terrain = terrain;
    }



public List<List<GameObject>> getTrees(int minX, int maxX) {
    List<GameObject> stumpBlockList = new ArrayList<>();
    List<GameObject> leafBlockList = new ArrayList<>();
    int lastTree = -100;
    for (int i = minX; i < maxX; i++) {
        double coinFlip = Math.random();
        if (coinFlip < 0.2 && i-lastTree>2) {
            List<GameObject> stump = createStump(i);
            List<GameObject> leafs = createLeafs(i);
            stumpBlockList.addAll(stump);
            leafBlockList.addAll(leafs);
            lastTree = i;
        }
    }
    List<List<GameObject>> stumpsAndLeafs = new ArrayList<List<GameObject>>();
    stumpsAndLeafs.add(stumpBlockList);
    stumpsAndLeafs.add(leafBlockList);
    return stumpsAndLeafs;

}
    private List<GameObject> createLeafs(int i) {
        List<GameObject> blockList = new ArrayList<GameObject>();
        float Xstart = (float)(i -LEAF_RADIUS+0.5)*Block.SIZE;
        int Ystart = (int)terrain.groundHeightAt((float) i)-(STUMP_HIGHT+LEAF_RADIUS+1)*Block.SIZE;
        for (int j = 0; j < LEAF_RADIUS*2; j++) {
            for (int k = 0; k <LEAF_RADIUS*2; k++) {
                double coinFlip = Math.random();
                if (coinFlip < 0.6) {
                    Flora.Leaf block = new Flora.Leaf(
                            new Vector2(Xstart+j*Block.SIZE,Ystart+k*Block.SIZE ) );
                    new ScheduledTask(
                            block,
                            (float) Math.random(),
                            true,
                            block::wiggle
                    );
                    blockList.add(block);
                } else if (coinFlip>0.9) {
                    Flora.Fruit fruit = new Flora.Fruit(
                            new Vector2(Xstart+j*Block.SIZE,Ystart+k*Block.SIZE ));
                    blockList.add(fruit);
                }
            }
        }
        return blockList;
    }

    public List<GameObject> createStump(int blockIndex) {
    List<GameObject> blockList = new ArrayList<GameObject>();
    float startingHeight = terrain.groundHeightAt((float) blockIndex);
    for (int i = 1; i < STUMP_HIGHT + 1; i++) {
        Block block = new Block(
                new Vector2(blockIndex * Block.SIZE, startingHeight - i * Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_STUMP_COLOR))
        );
        blockList.add(block);
    }
    return blockList;
}
}
