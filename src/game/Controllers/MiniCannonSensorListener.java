package game.Controllers;

import city.cs.engine.*;
import game.Entities.Player;
import game.Items.MiniCannon;

public class MiniCannonSensorListener implements SensorListener {
    private MiniCannon cannon1;

    public MiniCannonSensorListener(MiniCannon cannon) {
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

    @Override
    public void endContact(SensorEvent e) {
        // Check if the contact is with the player
        if (e.getContactBody() instanceof Player) {
            // Stop shooting the cannons when the player exits the sensor area
            cannon1.stopShootingBullets();
        }
    }
}
