package game.Utility;

import city.cs.engine.*;
import game.Levels.Ground;
import game.World.GameLevel;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.PrismaticJoint;

import java.util.Timer;
import java.util.TimerTask;
/**
 * A utility class for creating various items in the game world.
 * It provides static methods to create blocks and ground elements with specified positions, shapes, and images.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */

public class ItemFactory {

    /**
     * Creates a static body (block) in the game world with the specified position, image, and shape.
     *
     * @param world    The game level in which the block will be created.
     * @param position The position of the block in the game world.
     * @param image    The image to be displayed on the block.
     * @param shape    The shape of the block.
     * @return The created static body (block).
     */
    public static StaticBody createBlock(GameLevel world, Vec2 position, BodyImage image, Shape shape) { // Adjust the height multiplier as needed // Assuming GroundShape is a BoxShape with width 10 and height 1
        StaticBody ground = new StaticBody(world, shape);
        ground.setPosition(position);
        if (image != null) {
            ground.addImage(image);
        }
        return ground;
    }

    /**
     * Creates ground elements in the game world with the specified image, shape, and positions.
     *
     * @param world       The game level in which the ground elements will be created.
     * @param image       The image to be displayed on the ground elements.
     * @param groundShape The shape of the ground elements.
     * @param positions   The positions of the ground elements in the game world.
     */
    public static void createGrounds(GameLevel world, BodyImage image,Shape groundShape, Vec2... positions) {
        for (Vec2 position : positions) {
            new Ground(world, groundShape, image).setPosition(position);
        }
    }
}
