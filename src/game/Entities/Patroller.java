package game.Entities;
import city.cs.engine.*;
import game.Items.Rock;
import game.Utility.SoundLoader;
import game.World.GameLevel;
import org.jbox2d.common.Vec2;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Represents an enemy entity that patrols within a specified boundary and throws rocks at the player.
 * This class extends the Entity class from the city.cs.engine package
 * to provide additional functionality and behavior specific to the patroller.
 * It includes methods to handle patroller movement, rock throwing, health, and interactions with
 * the player.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class Patroller extends Entity {
    private boolean destroyed = false;
    private final float leftBound;
    private final float rightBound;
    private boolean isFacingRight;
    private int health;

    private static final Shape ENTITY_SHAPE = new PolygonShape(
            0.59f,-1.52f, 0.79f,-0.87f, 0.77f,0.7f, -0.16f,1.11f, -0.92f,0.51f, -0.92f,-0.79f, -0.83f,-1.48f
    );
    private final Timer rockThrowingTimer = new Timer();
    private final Timer rockRemovalTimer = new Timer();
    private GameLevel world;

    SoundClip ShotSound = SoundLoader.ShotSound;
    SoundClip enemyDeathSound = SoundLoader.enemyDeathSound;

    /**
     * Initializes a new instance of the Patroller class.
     *
     * @param world      The game world associated with this patroller.
     * @param leftBound  The left boundary within which the patroller can move.
     * @param rightBound The right boundary within which the patroller can move.
     */

    public Patroller(GameLevel world, float leftBound, float rightBound) {
        super(world, ENTITY_SHAPE);
        this.addImage(new BodyImage("data/EnemyRes/Pink_Enemy_Walk.gif", 3f));
        this.world = world;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.isFacingRight = true; // Initially facing right
        health = 100;
        startRockThrowing(); // Start throwing rocks
    }

    /**
     * Checks if the patroller has been destroyed.
     *
     * @return True if the patroller has been destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }


    public float getLeftBound() {
        return leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }
    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Sets the direction the patroller is facing.
     *
     * @param facingRight True if the patroller is facing right, false otherwise.
     */
    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
        updateDirection(isFacingRight);
    }



    private void updateDirection(boolean isFacingRight) {
        this.removeAllImages(); // Remove all images
        String imageName = isFacingRight ? "data/EnemyRes/Pink_Enemy_Walk.gif" : "data/EnemyRes/Pink_Enemy_Walk_Flipped.gif";
        this.addImage(new BodyImage(imageName, 3f)); // Set image based on orientation
    }

    /**
     * Throws a rock projectile from the patroller towards the player.
     *
     * @param world The game world.
     */
    private void throwRock(GameLevel world) {
        // Create a new rock
        Rock rock = new Rock(world);

        // Calculate the initial position of the rock relative to the patroller
        // Adjust these values as needed to position the rock slightly in front of the patroller
        float offsetX = isFacingRight() ? 1.5f : -1.5f; // Offset from the patroller's x-coordinate
        float offsetY = 0.5f; // Offset from the patroller's y-coordinate

        // Get the current position of the patroller
        Vec2 patrollerPosition = getPosition();

        // Calculate the new position of the rock
        float rockX = patrollerPosition.x + offsetX;
        float rockY = patrollerPosition.y + offsetY;

        // Set the new position of the rock
        rock.setPosition(new Vec2(rockX, rockY));

        // Set the velocity of the rock (adjust as needed)
        float velocityX = isFacingRight() ? 20 : -20; // Adjust the velocity based on the patroller's orientation
        float velocityY = 0; // No vertical component
        rock.setLinearVelocity(new Vec2(velocityX, velocityY));
        if (!destroyed) {
            ShotSound.play();
        }
        rock.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                // Check if the other object involved in the collision is the player
                if (collisionEvent.getOtherBody() instanceof Player) {
                    // Decrease the player's health
                    ((Player) collisionEvent.getOtherBody()).decreaseHealth(10);
                    // Destroy the rock
                    rock.destroy();
                }
            }
        });
        rockRemovalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                rock.destroy(); // Mark the rock for removal
            }
        }, 5000);
    }
    /**
     * Starts the timer for throwing rocks at regular intervals.
     */
    private void startRockThrowing() {
        rockThrowingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                throwRock(world); // Automatically throw rocks
            }
        }, 0, 2000); // Throw rocks every 2 seconds (adjust the interval as needed)
    }
    /**
     * Destroys the patroller and cancels the rock throwing timer.
     */
    @Override
    public void destroy() {
        // Cancel the rock throwing timer when the Patroller is destroyed
        rockThrowingTimer.cancel();
        super.destroy();
        destroyed = true;
    }
    /**
     * The patroller and restarts the rock throwing timer.
     */
    public void respawned() {
        destroyed = false; // Reset destroyed flag
        startRockThrowing(); // Restart throwing rocks
    }


    private void playHurtAnimation() {
        removeAllImages(); // Remove all previous images
        addImage(new BodyImage("data/EnemyRes/Pink_Enemy_Walk.gif", 3f));

        // Schedule a task to switch back to the walking animation after a delay
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Switch back to the walking animation after the delay
                removeAllImages();
                addImage(new BodyImage("data/EnemyRes/hurt.gif", 3f));
            }
        }, 100); // Adjust the delay as needed
    }

    /**
     * Decreases the patroller's health by the specified amount.
     * If the patroller's health reaches zero or below, it is destroyed.
     *
     * @param amount The amount by which to decrease the patroller's health.
     */
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            enemyDeathSound.play();
            destroy();
        } else if (health <= 50) {
            enemyDeathSound.play();
            playHurtAnimation();
        }
    }

    /**
     * Sets the health of the patroller to the specified value.
     *
     * @param health The new health value for the patroller.
     */
    public void setHealth(int health) {
        this.health = health;
    }

}
