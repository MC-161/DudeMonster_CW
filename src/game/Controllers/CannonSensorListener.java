package game.Controllers;

import city.cs.engine.*;
import game.Entities.Player;
import game.Items.Cannon;
import game.Items.MiniCannon;

/**
 * A sensor listener that detects contact with the player and controls the behavior of a cannon accordingly.
 * When the player enters the sensor area, the cannon starts shooting bullets, and when the player exits the sensor area, shooting stops.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class CannonSensorListener implements SensorListener {
    private Cannon cannon1;

    /**
     * Constructs a CannonSensorListener with the specified cannon.
     *
     * @param cannon The cannon to be controlled by this sensor listener.
     */
    public CannonSensorListener(Cannon cannon) {
        this.cannon1 = cannon;
    }
    @Override
    public void beginContact(SensorEvent e) {
        // Check if the contact is with the player
        if (e.getContactBody() instanceof Player) {
            // Start shooting the cannons when the player enters the sensor area
            cannon1.startBulletShooting();
        }
    }

    /**
     * Called when the sensor starts to overlap with another sensor.
     *
     * @param e The event corresponding to the sensor contact.
     */
    @Override
    public void endContact(SensorEvent e) {
        // Check if the contact is with the player
        if (e.getContactBody() instanceof Player) {
            // Stop shooting the cannons when the player exits the sensor area
            cannon1.stopShootingBullets();
        }
    }
}
