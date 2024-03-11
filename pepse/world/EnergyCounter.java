package pepse.world;

import java.util.function.Supplier;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/**
 * Shows the energy the avatar has in the top left corner of the screen
 */
public class EnergyCounter extends GameObject {

    private final Supplier<Float> getEnergy;
    private static final String LIFE_BAR_FONT = "Calibri";
    private static final String INITIAL_LIFE = "100% ";
    private static final Vector2 COUNTER_DIMENSIONS = new Vector2(60, 60);

    /**
     * constructs the energy counter using the avatar's getEnergy method
     * @param getEnergy - the avatar's getEnergy method
     */
    public EnergyCounter(Supplier<Float> getEnergy) {
        super(Vector2.ZERO,
                COUNTER_DIMENSIONS, null
        );
        this.renderer().setRenderable(
                new TextRenderable(
                        INITIAL_LIFE,
                        LIFE_BAR_FONT,
                        false,
                        true
                )
        );
        this.getEnergy = getEnergy;

    }

    /**
     * updates the text every deltaTime according to the current energy
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        TextRenderable renderable = (TextRenderable) this.renderer().getRenderable();
        renderable.setString(getEnergy.get() + "%");
    }
}
