package game.Levels;

import city.cs.engine.*;
import game.World.GameLevel;

public class Ground extends StaticBody {
    public Ground(GameLevel world, Shape shape, BodyImage image) {
        super(world, shape);
        addImage(image);
    }
}