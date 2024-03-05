package pepse;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daylight.Night;
import pepse.world.daylight.Sun;
import pepse.world.daylight.SunHalo;
import pepse.world.trees.TreePlanter;

public class PepseGameManager extends GameManager {
    //todo: full screen?

    private static final float DAY_CYCLE_LENGTH = 30f;

    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 dimensions =windowController.getWindowDimensions();
        // create sky
        GameObject sky = Sky.create(dimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        // create ground
        Terrain terrain = new Terrain(dimensions,1);
        for (Block block : terrain.createInRange(0,(int)(dimensions.x()/Block.SIZE))){
            gameObjects().addGameObject(block,Layer.STATIC_OBJECTS);
        }
        //create night
        GameObject night = Night.create(dimensions, DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.FOREGROUND);

        // create sun and Halo
        GameObject sun = Sun.create(dimensions,DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);

        //create trees leafs fruits and stumps
        TreePlanter planter = new TreePlanter(dimensions,terrain);
        var trees = planter.getTrees(0,(int)(dimensions.x()/Block.SIZE));
        for(var stumps  :trees.get(0)){
                gameObjects().addGameObject(stumps,Layer.STATIC_OBJECTS);
        }
        for(var leafs  :trees.get(1)){
                gameObjects().addGameObject(leafs,Layer.FOREGROUND-1);
        }

        // create avatar
        Avatar avatar = new Avatar(windowController.getWindowDimensions().mult(0.5f), inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        TextRenderable energyText = new TextRenderable("100% ", "Calibri", false, true);
        EnergyCounter energyCounter = new EnergyCounter(Vector2.ZERO, new Vector2(60,60),
                energyText,avatar::getEnergy);
        gameObjects().addGameObject(energyCounter);



    }

    public static void main(String[] args){
        new PepseGameManager("pepse",new Vector2(800f,500f)).run();
    }
}
