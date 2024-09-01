package game.Items;

import city.cs.engine.*;
import game.Entities.Player;
import game.Items.Bullet;
import game.World.GameLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Cannon {
    private final GameLevel world;
    private final StaticBody cannonBody;
    private Timer bulletShootingTimer;
    private boolean isFacingRight;

    public Cannon(GameLevel world, Vec2 position) {
        this.world = world;
        // Create the cannon body at the specified position
        cannonBody = new StaticBody(world);
        // Set the position and shape of the cannon
        cannonBody.setPosition(position);
        // Add any desired shape or appearance to the cannon
        // Define the shape of the cannon here
        Shape shape = new PolygonShape(-2.22f,0.35f, -1.56f,1.4f, 1.99f,1.41f, 2.4f,0.25f, 2.12f,-1.51f, -1.98f,-1.5f, -2.22f,0.02f);
        Fixture fixture = new SolidFixture(cannonBody, shape);
    }

    public void setFacingRight(boolean facingRight) {
        // Flip the shape horizontally if facing left
        isFacingRight = facingRight;
        cannonBody.removeAllImages(); // Remove any existing images
        if (!facingRight) {
            cannonBody.addImage(new BodyImage("data/Level_Res/Level3_Res/Cannon_Flipped.gif",1.5f * 2)); // Add left-facing image
        } else {
            cannonBody.addImage(new BodyImage("data/Level_Res/Level3_Res/Cannon.gif",1.5f * 2)); // Add right-facing image
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
        // Set the velocity of the bullet
        float velocityX = isFacingRight ? 65 : -65; // Adjust the velocity based on the cannon's orientation
        float velocityY = 0; // No vertical component
        bullet.setLinearVelocity(new Vec2(velocityX, velocityY));
        shootSound.play(); // Play the shooting sound
        // Schedule task to destroy bullet after a certain time
        bullet.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                // Check if the other object involved in the collision is the player
                if (collisionEvent.getOtherBody() instanceof Player) {
                    // Decrease the player's health
                    ((Player) collisionEvent.getOtherBody()).decreaseHealth(10);
                    // Destroy the rock
                    bullet.destroy();
                }
            }
        });
        Timer bulletRemovalTimer = new Timer();
        bulletRemovalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                bullet.destroy(); //Destroy bullet after 5 seconds
            }
        }, 600); //Destroy bullet after 5 seconds
    }
    public void startBulletShooting() {
        bulletShootingTimer = new Timer();
        bulletShootingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                shootBullet(); // Shoot a bullet
            }
        }, 0, 1300); // Shoot a bullet every 2 seconds (adjust the interval as needed)
    }

    public void stopShootingBullets() {
        // Cancel the bullet shooting timer
        bulletShootingTimer.cancel();
    }

    private static SoundClip shootSound;

    static {
        try {
             shootSound = new SoundClip("data/Level_Res/CannonShot.wav");
            System.out.println("Loading books sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
