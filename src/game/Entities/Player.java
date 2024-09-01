package game.Entities;

import city.cs.engine.*;
import game.Items.Rock;
import game.Utility.SoundLoader;
import game.World.GameLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Represents the player entity in the game world.
 * This class extends the Entity class from the city.cs.engine package
 * to provide additional functionality and behavior specific to the player.
 * It includes methods to handle player movement, health, interactions with
 * game objects, and animation.
 *
 * @author Mahfuz Chwodhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class Player extends Entity {
    private static final Shape ENTITY_SHAPE = new PolygonShape(
            0.59f,-1.52f, 0.79f,-0.87f, 0.77f,0.7f, -0.16f,1.11f, -0.92f,0.51f, -0.92f,-0.79f, -0.83f,-1.48f
    );
    private static final float ROCK_SPEED = 20f;

    private boolean isFacingRight;
    private static final int MAX_HEALTH = 100;
    private int health;
    private int coinsCollected;
    private boolean isRunning;
    private boolean isWalking;
    private Timer rockRemovalTimer = new Timer();
    private GameLevel world;
    private boolean levelComplete;
    SoundClip playerThrowRockSound = SoundLoader.playerThrowRockSound;
    SoundClip playerCoinCollectSound = SoundLoader.playerCoinCollectSound;
    SoundClip playerDeathSound = SoundLoader.playerDeathSound;
    private int RunningSpeed;
    private int WalkingSpeed;

    public Player(GameLevel world) {
        super(world, ENTITY_SHAPE);
        this.world = world;
        this.addImage(new BodyImage("data/PlayerRes/Dude_Monster.png", 3f)); // Initial idle image
        isFacingRight = true; // Initially facing right
        isWalking = false; // Initially not walking
        health = MAX_HEALTH;
        coinsCollected = 0;
    }
    /**
     * Throws a rock projectile from the player.
     *
     * @param world        The game world.
     * @param isFacingRight True if the player is facing right, false otherwise.
     */
    public void throwRock(GameLevel world, boolean isFacingRight) {
        // Create a new rock
        Rock rock = new Rock(world);

        // Calculate the initial position of the rock relative to the player
        // Adjust these values as needed to position the rock slightly in front of the player
        float offsetX = isFacingRight ? 1.5f : -1.5f; // Offset from the player's x-coordinate
        float offsetY = 0.5f; // Offset from the player's y-coordinate

        // Get the current position of the player
        Vec2 playerPosition = getPosition();

        // Calculate the new position of the rock
        float rockX = playerPosition.x + offsetX;
        float rockY = playerPosition.y + offsetY;

        // Set the new position of the rock
        rock.setPosition(new Vec2(rockX, rockY));

        // Determine the direction and speed of the rock based on the player's facing direction
        float velocityX = isFacingRight ? ROCK_SPEED : -ROCK_SPEED;
        float velocityY = 0;
        Vec2 velocity = new Vec2(velocityX, velocityY);

        // Set the linear velocity of the rock
        rock.setLinearVelocity(velocity);
        playerThrowRockSound.play(); // Play the sound effect when the player throws a rock

        // Add collision listener to the rock to detect collisions with enemies
        rock.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                // Check if the other object involved in the collision is an enemy (e.g., Patroller)
                if (collisionEvent.getOtherBody() instanceof Patroller) {
                    // Destroy the enemy
                    Patroller enemy = (Patroller) collisionEvent.getOtherBody();
                    enemy.decreaseHealth(20);

                    // Destroy the rock when it hits the enemy
                    rock.destroy();
                }
                if (collisionEvent.getOtherBody() instanceof Jumper) {
                    // Destroy the enemy
                    Jumper enemy = (Jumper) collisionEvent.getOtherBody();
                    enemy.destroy();

                    // Destroy the rock when it hits the enemy
                    rock.destroy();
                }
            }
        });

        // Schedule the rock for removal after a certain time (e.g., 3 seconds)
        rockRemovalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                rock.destroy(); // Mark the rock for removal
            }
        }, 1000);
    }


    /**
     * Decreases the player's health by the specified amount.
     * If the player's health reaches zero or below, it triggers the reset of the level.
     *
     * @param amount The amount by which to decrease the player's health.
     */
    // Method to decrease player health
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            playerDeathSound.play();
            Map<String, Object> patrollerInfo = world.getPatrollerInfo();
            Vec2 patrollerPosition = (Vec2) patrollerInfo.get("Position");
            float leftBound = (float) patrollerInfo.get("leftBound");
            float rightBound = (float) patrollerInfo.get("rightBound");
            world.resetLevel(world.getCoinInitialPositions(),patrollerPosition,leftBound,rightBound, world.getJumperPosition()); // Implement logic to handle when player dies
        }
    }
    /**
     * Resets the player's position, health, and coins collected.
     */
    public void playerReset() {
        // Reset the player's position
        this.setPosition(new Vec2(0, -5));
        // Reset the player's health and coins
        health = MAX_HEALTH;
        coinsCollected = 0;
    }
    /**
     * Retrieves the number of coins needed to complete the level.
     *
     * @return The number of coins needed to complete the level.
     */
    public int getCoinsNeeded() {
        return world.getLevelCoins();
    }

    /**
     * Adds one to the number of coins collected by the player.
     * Also plays a sound effect for collecting coins.
     */

    // Method to add collected coins
    public void addCoins() {
        coinsCollected += 1;
        playerCoinCollectSound.play();
    }

    /**
     * Retrieves the current health of the player.
     *
     * @return The current health of the player.
     */
    // Method to get current player health
    public int getHealth() {
        return health;
    }

    // Method to get number of coins collected
    public int getCoinsCollected() {
        return coinsCollected;
    }


    /**
     * Sets the completion status of the level.
     *
     * @param levelComplete True if the level is complete, false otherwise.
     */
    public void setLevelComplete(boolean levelComplete) {
        if(levelComplete && coinsCollected == getCoinsNeeded()){
            this.levelComplete = true;
        }
    }

    /**
     * Checks if the level is complete.
     *
     * @return True if the level is complete, false otherwise.
     */
    public boolean isLevelComplete() {
        return levelComplete;
    }


    // Update the direction and animation frames
    private void updateDirection(boolean isFacingRight) {
        this.removeAllImages(); // Remove all images
        String imageName = isFacingRight ? "data/PlayerRes/Dude_Monster.gif" : "data/PlayerRes/Dude_Monster_Flipped.gif";
        this.addImage(new BodyImage(imageName, 3f)); // Set idle image
    }

    // Animate walking frames
    private void animateWalkingFrames(boolean isFacingRight) {
        String imageName = isFacingRight ? "data/PlayerRes/Dude_Monster_Walk.gif" : "data/PlayerRes/Dude_Monster_Walk_Flipped.gif";
        this.removeAllImages(); // Remove all images
        this.addImage(new BodyImage(imageName, 3f)); // Add walking frames
    }
    // Animate running frames
    private void animateRunningFrames(boolean isFacingRight) {
        String imageName = isFacingRight ? "data/PlayerRes/Dude_Monster_Run.gif" : "data/PlayerRes/Dude_Monster_Run_Flipped.gif";
        this.removeAllImages(); // Remove all images
        this.addImage(new BodyImage(imageName, 3f)); // Add running frames
    }


    // Add jumping image
    private void addJumpingImage(boolean isFacingRight) {
        String imageName = isFacingRight ? "data/PlayerRes/Dude_Monster_Jump.png" : "data/PlayerRes/Dude_Monster_Jump_Flipped.png";
        this.removeAllImages(); // Remove all images
        this.addImage(new BodyImage(imageName, 3f)); // Set jumping image
    }

    // Override the startWalking method to update direction based on speed and animate walking frames
    @Override
    public void startWalking(float speed) {
        super.startWalking(speed);
        // Update direction based on speed
        if (speed > 0) {
            isFacingRight = true;
        } else if (speed < 0) {
            isFacingRight = false;
        }
        updateDirection(isFacingRight);
        isWalking = true; // Player is walking
        animateWalkingFrames(isFacingRight);
    }
    // Start running
    public void startRunning(float speed) {
        super.startWalking(speed);
        // Update direction based on speed
        if (speed > 0) {
            isFacingRight = true;
        } else if (speed < 0) {
            isFacingRight = false;
        }
        updateDirection(isFacingRight);
        isRunning = true; // Player is running
        animateRunningFrames(isFacingRight);
    }


    // Override the stopWalking method to remove walking frames
    @Override
    public void stopWalking() {
        super.stopWalking();
        this.removeAllImages(); // Remove all images
        updateDirection(isFacingRight); // Set idle image
        isWalking = false; // Player is not walking
    }
    // Stop running
    public void stopRunning() {
        super.stopWalking(); // Stop running by stopping walking
        this.removeAllImages(); // Remove all images
        updateDirection(isFacingRight); // Set idle image
        isRunning = false; // Player is not running
    }

    // Method to handle player jumping
    public void jump(float force) {
        super.jump(force); // Call the jump method from the superclass
        addJumpingImage(isFacingRight); // Add jumping image
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }
    public static int getMaxHealth() {
        return MAX_HEALTH;
    }

    public int getRunningSpeed() {
        return RunningSpeed;
    }

    public void setRunningSpeed(int RunningSpeed) {
        this.RunningSpeed = RunningSpeed;
    }

    public void setWalkingSpeed(int WalkingSpeed) {
        this.WalkingSpeed = WalkingSpeed;
    }
    public int getWalkingSpeed() {
        return WalkingSpeed;
    }
}
