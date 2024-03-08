package pepse.world;
import java.util.function.Supplier;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

public class EnergyCounter extends GameObject {

    private final Supplier<Float> getEnergy;
    private static final String LIFE_BAR_FONT = "Calibri";
    private static final String INITIAL_LIFE = "100% ";


    private static final  Vector2 COUNTER_DIMENSIONS= new Vector2(60,60);



    public EnergyCounter(Supplier<Float> getEnergy ) {
        super(Vector2.ZERO,
                COUNTER_DIMENSIONS,null
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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        TextRenderable renderable = (TextRenderable) this.renderer().getRenderable();
        renderable.setString(getEnergy.get() +"%");
    }
}
