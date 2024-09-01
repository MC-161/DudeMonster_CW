package game.Items;

import city.cs.engine.*;
import game.World.GameLevel;
/**
 * Represents a collectible item in the game world.
 * Collectible items are static bodies that can be collected by the player.
 * This class extends the StaticBody class from the city.cs.engine package.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public abstract class Collectible extends StaticBody {
    /**
     * Constructs a new Collectible object with the specified shape and image.
     *
     * @param w     The GameLevel object representing the game world in which the collectible exists.
     * @param shape The shape of the collectible.
     * @param image The image to be displayed for the collectible.
     */
    public Collectible(GameLevel w, Shape shape, BodyImage image) {
        super(w, shape);
        addImage(image);
    }
}
