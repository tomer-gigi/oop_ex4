package pepse;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;

import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Fruit;
import pepse.world.trees.Leaf;
import pepse.world.trees.Flora;

import java.awt.*;
import java.util.List;


/**
 * creates the components of the game, runs it and updates it
 */
public class PepseGameManager extends GameManager {
    private static final Color BASE_STUMP_COLOR = new Color(100, 50, 20);
    private static final float DAY_CYCLE_LENGTH = 30f;
    private static List<GameObject> leafs_N_fruits;
    static int jumps = 0;
    static private final Color[] FRUITS_COLORS = {Color.YELLOW, Color.RED, Color.MAGENTA};
    private static List<GameObject> stumps;

    /**
     * opens the window
     * @param windowTitle - title of the new window
     * @param windowDimensions - dimensions of the new window
     */
    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * calls all the updates related to a jump
     */
    static void jumpUpdate() {
        for (var leafFruit : leafs_N_fruits) {
            if (leafFruit.getTag().equals("leaf")) {
                Leaf leaf = (Leaf) leafFruit;
                leaf.spin90();
            }
            if (leafFruit.getTag().equals("fruit")) {
                Fruit fruit = (Fruit) leafFruit;
                Color color = FRUITS_COLORS[jumps % FRUITS_COLORS.length];
                fruit.renderer().setRenderable(new OvalRenderable(color));
            }
        }
        for (GameObject stump : stumps) {
            stump.renderer().setRenderable(new RectangleRenderable(
                    ColorSupplier.approximateColor(BASE_STUMP_COLOR)));
        }
        jumps++;

    }

    /**
     * creates all the components of the game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader,
                soundReader,
                inputListener,
                windowController);

        Vector2 dimensions = windowController.getWindowDimensions();
        int maxBlocks = (int) (dimensions.x() / Block.SIZE);

        // create sky
        GameObject sky = Sky.create(dimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);


        // create ground
        Terrain terrain = new Terrain(dimensions, 1);
        for (Block block : terrain.createInRange(0, maxBlocks)) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        //create night
        GameObject night = Night.create(dimensions, DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        // create sun and Halo
        GameObject sun = Sun.create(dimensions, DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);

        //create trees -leafs fruits and stumps
        Flora flora1 = new Flora(terrain, this.gameObjects());
        var trees = flora1.createInRange(0, maxBlocks);
        leafs_N_fruits = trees.get(1);
        stumps = trees.get(0);
        for (var stump : stumps) {
            gameObjects().addGameObject(stump, Layer.STATIC_OBJECTS);
        }

        for (var leafFruit : leafs_N_fruits) {
            gameObjects().addGameObject(leafFruit);

        }

        // create avatar
        Avatar avatar = new Avatar(windowController.getWindowDimensions().mult(0.3f),
                inputListener,
                imageReader, PepseGameManager::jumpUpdate);
        gameObjects().addGameObject(avatar);
        EnergyCounter energyCounter = new EnergyCounter(avatar::getEnergy);
        gameObjects().addGameObject(energyCounter);


    }

    /**
     * calls run which calls initializeGame and then updates the game
     * @param args - not in  use
     */
    public static void main(String[] args) {

        new PepseGameManager("pepse", new Vector2(1200f, 800f)).run();
    }
}
