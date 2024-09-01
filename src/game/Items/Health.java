package game.Items;

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.Shape;
import game.World.GameLevel;

public class Health extends Collectible {
    private static final Shape HealthShape = new CircleShape(1);
    private static final BodyImage HealthImage = new BodyImage("data/Level_Res/FastPotion.gif", 1f * 2);

    public Health(GameLevel w) {
        super(w, HealthShape, HealthImage);
    }
}
