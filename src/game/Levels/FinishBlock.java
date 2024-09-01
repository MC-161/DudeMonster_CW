package game.Levels;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class FinishBlock extends StaticBody {
    private static final BodyImage FinishImage = new BodyImage("data/Level_Res/Finish.png", 2f * 2); // Adjust the height multiplier as needed
    private static final Shape FinishShape = new BoxShape(8f,2f); // Adjust the height multiplier as needed
    public FinishBlock(World world, Vec2 position) {
        super(world, FinishShape);
        this.setPosition(position);
        this.addImage(FinishImage);
    }
}
