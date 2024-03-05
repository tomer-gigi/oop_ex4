package pepse.world;
import java.util.function.Supplier;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

public class EnergyCounter extends GameObject {

    private final TextRenderable renderable;
    private final Supplier<Float> getEnergy;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */


    public EnergyCounter(Vector2 topLeftCorner, Vector2 dimensions,
                         TextRenderable renderable,Supplier<Float> getEnergy ) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = renderable;
        this.getEnergy = getEnergy;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderable.setString(Float.toString(getEnergy.get())+"%");
    }
}
