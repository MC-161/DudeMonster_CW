package game.Items;

import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.World.GameLevel;

import java.awt.*;

public class DeathBlock extends StaticBody {
    private static final Shape DeathBlockShape = new BoxShape(120f, 0); // Adjust the height multiplier as needed

    public DeathBlock(GameLevel w) {
        super(w, DeathBlockShape);
    }
}
