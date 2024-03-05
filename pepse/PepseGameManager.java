package pepse;

import danogl.gui.rendering.TextRenderable;
import pepse.world.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daylight.Night;
import pepse.world.daylight.Sun;
import pepse.world.daylight.SunHalo;

public class PepseGameManager extends GameManager {
    //todo: full screen?
    private static final float MIDNIGHT_OPACITY = 0.9f;
    private static final float MORNING_OPACITY = 0f;
    private static final float DAY_CYCLE_LENGTH = 30f;

    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }
    public PepseGameManager(String windowTitle) {
        super (windowTitle);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 dimensions =windowController.getWindowDimensions();
        GameObject sky = Sky.create(dimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        Terrain terrain = new Terrain(dimensions,1);
        for (Block block : terrain.createInRange(0,(int)dimensions.x())){
            gameObjects().addGameObject(block,Layer.STATIC_OBJECTS);
        }
        GameObject night = Night.create(dimensions, DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.FOREGROUND);
        new Transition<>(
                night,//thegameobjectbeingchanged
                night.renderer()::setOpaqueness, //themethodtocall
                MORNING_OPACITY, //initialtransitionvalue
                MIDNIGHT_OPACITY, //finaltransitionvalue
                Transition.CUBIC_INTERPOLATOR_FLOAT,//useacubicinterpolator
                DAY_CYCLE_LENGTH, //transitionfullyoverhalfaday
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,//ChooseappropriateENUMvalue
                null//nothingfurthertoexecuteuponreachingfinalvalu
        );

        GameObject sun = Sun.create(dimensions,DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);
        Vector2 initialSunCenter = sun.getCenter();
        new Transition<>(
                sun,//thegameobjectbeingchanged
                (Float angle)-> sun.setCenter(
                                initialSunCenter.subtract(dimensions.mult(0.5f))
                                .rotated(angle)
                                .add(dimensions.mult(0.5f)
                                )
                        ), //themethodtocall
                0f, //initialtransitionvalue
                360f, //finaltransitionvalue
                Transition.LINEAR_INTERPOLATOR_FLOAT,//useacubicinterpolator
                DAY_CYCLE_LENGTH*2f, //transitionfullyoverhalfaday
                Transition.TransitionType.TRANSITION_LOOP,//ChooseappropriateENUMvalue
                null//nothingfurthertoexecuteuponreachingfinalvalu
        );
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);
        Avatar avatar = new Avatar(windowController.getWindowDimensions().mult(0.5f), inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        TextRenderable energyText = new TextRenderable("100% ", "Calibri", false, true);
        EnergyCounter energyCounter = new EnergyCounter(Vector2.ZERO, new Vector2(60,60),
                energyText,avatar::getEnergy);
        gameObjects().addGameObject(energyCounter);
    }

    public static void main(String[] args){
        new PepseGameManager("pepse",new Vector2(600f,800f)).run();

    }
}
