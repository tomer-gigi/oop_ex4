package pepse;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.RendererComponent;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daylight.Night;
import pepse.world.daylight.Sun;
import pepse.world.daylight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.TreePlanter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class PepseGameManager extends GameManager {

    private static final float DAY_CYCLE_LENGTH = 30f;
    private static List<GameObject> flora;
    static int jumps = 0;
    static private final Color[] appleColors = {Color.YELLOW,Color.RED};
    private static List<GameObject> stumps;
    private static Random rand = new Random();

    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }
    static void jumpUpdate(){
        for(var leafs  :flora){
            if (leafs.getTag().equals("leaf")){
                Flora.Leaf a = (Flora.Leaf) leafs;
                a.spin90();
            }
            if( leafs.getTag().equals("fruit")){
                Flora.Fruit a = (Flora.Fruit) leafs;
                Color color= appleColors[jumps%2];
                a.renderer().setRenderable(new OvalRenderable(color));
            }
        }
        String currentStump = "";
        int colorChange = rand.nextInt(7)-3;
        for (GameObject block  :stumps){
            RendererComponent r= block.renderer();
            if (!block.getTag().equals(currentStump)){
                currentStump = block.getTag();
                colorChange = rand.nextInt(7)-3;
                block.renderer().setRenderable(new RectangleRenderable(
                        new Color(100+colorChange, 50+colorChange, 20+colorChange)));
            }else{
                block.renderer().setRenderable(new RectangleRenderable(
                        new Color(100+colorChange, 50+colorChange, 20+colorChange)));
            }
            currentStump = currentStump;
        }
        jumps++;
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
        TreePlanter planter = new TreePlanter(dimensions,terrain,this.gameObjects());
        var trees = planter.getTrees(0,(int)(dimensions.x()/Block.SIZE));
        for(var stumps  :trees.get(0)){
                gameObjects().addGameObject(stumps,Layer.STATIC_OBJECTS);
        }

        for(var leafs  :trees.get(1)){
                gameObjects().addGameObject(leafs);

        }
        flora = trees.get(1);
        stumps = trees.get(0);


        // create avatar
        Avatar avatar = new Avatar(windowController.getWindowDimensions().mult(0.5f),
                inputListener,
                imageReader,PepseGameManager::jumpUpdate);
        gameObjects().addGameObject(avatar);
        TextRenderable energyText = new TextRenderable("100% ", "Calibri", false, true);
        EnergyCounter energyCounter = new EnergyCounter(Vector2.ZERO, new Vector2(60,60),
                energyText,avatar::getEnergy);
        gameObjects().addGameObject(energyCounter);



    }

    public static void main(String[] args){
        new PepseGameManager("pepse",new Vector2(1200f,800f)).run();
    }
}
