package pepse;


import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Sky;

public class PepseGameManager extends GameManager {

    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 dimensions =windowController.getWindowDimensions();
        GameObject sky = Sky.create(dimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    public static void main(String[] args){
        new PepseGameManager("pepse",new Vector2(500f,500f)).run();
    }
}
