package game.Controllers;

import city.cs.engine.*;
import game.Entities.Patroller;
/**
 * A controller that manages the movement behavior of a Patroller entity.
 * The Patroller will continuously move back and forth between its specified left and right bounds.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class PatrollerController implements StepListener {
    private Patroller patroller;

    /**
     * Constructs a PatrollerController with the specified Patroller entity.
     *
     * @param patroller The Patroller entity to be controlled.
     */
    public PatrollerController(Patroller patroller) {
        this.patroller = patroller;
    }
    /**
     * Called before each simulation step.
     * Updates the movement of the Patroller based on its current orientation and bounds.
     *
     * @param stepEvent The event corresponding to the simulation step.
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        float x = patroller.getPosition().x;

        // Check the patroller's orientation to determine the direction of movement
        if (patroller.isFacingRight()) {
            if (x >= patroller.getRightBound()) {
                patroller.setFacingRight(false);
            }
        } else {
            if (x <= patroller.getLeftBound()) {
                patroller.setFacingRight(true);
            }
        }

        // Move the patroller based on its orientation
        if (patroller.isFacingRight()) {
            patroller.startWalking(3);
        } else {
            patroller.startWalking(-3);
        }
    }

    /**
     * Called after each simulation step.
     * You can implement any post-step logic needed for this controller.
     *
     * @param stepEvent The event corresponding to the simulation step.
     */
    @Override
    public void postStep(StepEvent stepEvent) {
        // Any post-step logic you might need
    }
}

