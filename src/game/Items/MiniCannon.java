package game.Items;

import city.cs.engine.*;
import game.Entities.Player;
import game.World.GameLevel;
import org.jbox2d.common.Vec2;
import java.util.Timer;
import java.util.TimerTask;

public class MiniCannon{
    private final GameLevel world;
    private final StaticBody cannonBody;
    private Timer bulletShootingTimer;
    private boolean isFacingRight;

    public MiniCannon(GameLevel world, Vec2 position) {
        this.world = world;
        // Create the cannon body at the specified position
        cannonBody = new StaticBody(world);
        // Set the position and shape of the cannon
        cannonBody.setPosition(position);
        // Add any desired shape or appearance to the cannon
        // Define the shape of the cannon here
        Shape shape = new PolygonShape(-1.329f,0.926f, 1.311f,0.914f, 1.575f,0.502f, 1.563f,-0.655f, 0.72f,-1.098f, -1.354f,-1.025f, -1.385f,0.926f);
        Fixture fixture = new SolidFixture(cannonBody, shape);
    }

    public void setFacingRight(boolean facingRight) {
        // Flip the shape horizontally if facing left
        isFacingRight = facingRight;
        cannonBody.removeAllImages(); // Remove any existing images
        if (!facingRight) {
            cannonBody.addImage(new BodyImage("data/Level_Res/Level3_Res/MiniCannon_Flipped.gif",1f * 2)); // Add left-facing image
        } else {
            cannonBody.addImage(new BodyImage("data/Level_Res/Level3_Res/MiniCannon.gif",1f * 2)); // Add right-facing image
        }
    }

    public void shootBullet() {
        // Create a new bullet
        Bullet bullet = new Bullet(world);
        // Calculate the position of the cannon
        Vec2 cannonPosition = cannonBody.getPosition();
        // Calculate the position of the bullet slightly in front of the cannon
        float offsetX = 1f;
        float offsetY = 1f;

        Vec2 bulletPosition = new Vec2(cannonPosition.x + (isFacingRight ? offsetX : -offsetX), cannonPosition.y + offsetY);
        // Set the position of the bullet
        bullet.setPosition(bulletPosition);
        // Set the velocity of the bullet (adjust as needed)
        float velocityX = isFacingRight ? 65 : -65; // Adjust the velocity based on the cannon's orientation
        float velocityY = 0; // No vertical component
        bullet.setLinearVelocity(new Vec2(velocityX, velocityY));
        // Schedule task to destroy bullet after a certain time
        bullet.addCollisionListener(collisionEvent -> {
            // Check if the other object involved in the collision is the player
            if (collisionEvent.getOtherBody() instanceof Player) {
                // Decrease the player's health
                ((Player) collisionEvent.getOtherBody()).decreaseHealth(10);
                // Destroy the rock
                bullet.destroy();
            }
        });
        Timer bulletRemovalTimer = new Timer();
        bulletRemovalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                bullet.destroy(); //Destroy bullet after 3 seconds
            }
        }, 3000); //Destroy bullet after 5 seconds
    }
    public void startBulletShooting() {
        bulletShootingTimer = new Timer();
        bulletShootingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                shootBullet(); // Shoot a bullet
            }
        }, 0, 500); // Shoot a bullet every 2 seconds (adjust the interval as needed)
    }

    public void stopShootingBullets() {
        // Cancel the bullet shooting timer
        bulletShootingTimer.cancel();
    }
}
