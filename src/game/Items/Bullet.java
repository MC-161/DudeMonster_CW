package game.Items;

import city.cs.engine.*;
import game.World.GameLevel;
/**
 * Represents a bullet entity in the game world.
 * Bullets are fired from cannons and move in a straight trajectory until they collide with another object.
 * This class extends the DynamicBody class from the city.cs.engine package.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class Bullet extends DynamicBody {
    private static final Shape ROCK_SHAPE = new CircleShape(0.5f);
    /**
     * Constructs a new Bullet object and initializes its properties.
     *
     * @param world The GameLevel object representing the game world in which the bullet exists.
     */
    public Bullet(GameLevel world) {
        super(world, ROCK_SHAPE);
        addImage(new BodyImage("data/Level_Res/CannonBall.png", 1));
        setBullet(true);
    }
}
