package game.Controllers;

import city.cs.engine.*;
import game.Entities.Jumper;
/**
 * A controller that manages the movement behavior of a Jumper entity.
 * The Jumper will continuously move back and forth between its specified left and right bounds.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class JumperController implements StepListener {
    private Jumper walkingJumper;

    /**
     * Constructs a JumperController with the specified Jumper entity.
     *
     * @param walkingJumper The Jumper entity to be controlled.
     */
    public JumperController(Jumper walkingJumper) {
        this.walkingJumper = walkingJumper;
    }

    /**
     * Called before each simulation step.
     * Updates the movement of the Jumper based on its current orientation and bounds.
     *
     * @param stepEvent The event corresponding to the simulation step.
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        // Check the WalkingJumper's orientation to determine the direction of movement
        if (walkingJumper.isFacingRight()) {
            if (walkingJumper.getPosition().x >= walkingJumper.getRightBound()) {
                walkingJumper.setFacingRight(false);
            }
        } else {
            if (walkingJumper.getPosition().x <= walkingJumper.getLeftBound()) {
                walkingJumper.setFacingRight(true);
            }
        }
        if (walkingJumper.isFacingRight()) {
            walkingJumper.startWalking(5);
        } else {
            walkingJumper.startWalking(-5);
        }
    }

    /**
     * Called after each simulation step.
     * No post-step logic is needed for this controller.
     *
     * @param stepEvent The event corresponding to the simulation step.
     */
    @Override
    public void postStep(StepEvent stepEvent) {
        // No post-step logic needed
    }
}
