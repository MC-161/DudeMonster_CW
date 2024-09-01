package game.Items;

import city.cs.engine.*;
import game.World.GameLevel;

public class Rock extends DynamicBody {
    private static final Shape ROCK_SHAPE = new CircleShape(0.5f);

    public Rock(GameLevel world) {
        super(world, ROCK_SHAPE);
        addImage(new BodyImage("data/Level_Res/Rock2.png", 1));
    }
}
