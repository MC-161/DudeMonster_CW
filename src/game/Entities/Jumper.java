package game.Entities;

import city.cs.engine.*;
import game.Utility.SoundLoader;
import game.World.GameLevel;
import org.jbox2d.common.Vec2;

import java.util.Timer;
import java.util.TimerTask;
/**
 * A class representing an entity that jumps periodically within a specified range.
 * It extends the Entity class similar to Patroller but implements the behavior of jumping.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class Jumper extends Entity {
    private boolean destroyed = false;
    private float leftBound;
    private float rightBound;
    private boolean isFacingRight;
    SoundClip enemyJumpSound = SoundLoader.enemyJumpSound;
    private static final Shape ENTITY_SHAPE = new PolygonShape(
            0.59f,-1.52f, 0.79f,-0.87f, 0.77f,0.7f, -0.16f,1.11f, -0.92f,0.51f, -0.92f,-0.79f, -0.83f,-1.48f
    );
    private final Timer moveTimer = new Timer();
    private final Timer jumpTimer = new Timer();
    private GameLevel world;

    private boolean onGround;

    /**
     * Constructs a Jumper with the specified game world, left bound, and right bound.
     *
     * @param world      The game world in which the Jumper exists.
     * @param leftBound  The left boundary within which the Jumper moves.
     * @param rightBound The right boundary within which the Jumper moves.
     */
    public Jumper(GameLevel world, float leftBound, float rightBound) {
        super(world, ENTITY_SHAPE);
        this.addImage(new BodyImage("data/EnemyRes/Jumper.gif", 3f));
        this.world = world;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.isFacingRight = true; // Initially facing right
        startJumping(); // Start jumping
        onGround = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public float getLeftBound() {
        return leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }

    public void setLeftBound(float nleftBound) {
        leftBound = nleftBound;
    }

    public void setRightBound(float nrightBound) {
        rightBound = nrightBound;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Sets the direction the Jumper is facing.
     *
     * @param facingRight true if the Jumper is facing right, otherwise false.
     */
    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
        updateDirection(isFacingRight);
    }

    /**
     * Updates the image of the Jumper based on the direction it is facing.
     *
     * @param isFacingRight true if the Jumper is facing right, otherwise false.
     */
    private void updateDirection(boolean isFacingRight) {
        this.removeAllImages(); // Remove all images
        String imageName = isFacingRight ? "data/EnemyRes/Jumper.gif" : "data/EnemyRes/Jumper_Flipped.gif";
        this.addImage(new BodyImage(imageName, 3f));
    }

    /**
     * Initiates a jump by applying an impulse to the Jumper entity.
     */
    private void jump() {
        float impulse = 25;
        Vec2 jumpForce = new Vec2(0, impulse);
        this.applyImpulse(jumpForce);
    }


    /**
     * Starts the periodic jumping behavior of the Jumper entity.
     * The Jumper will jump at regular intervals.
     */
    private void startJumping() {
        jumpTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                enemyJumpSound.play();
                jump(); // Jump
            }
        }, 0, 3000); // Jump every second (adjust the interval as needed)
    }

    /**
     * Destroys the Jumper entity, canceling any active timers.
     */
    @Override
    public void destroy() {
        // Cancel the move and jump timers when the WalkingJumper is destroyed
        moveTimer.cancel();
        jumpTimer.cancel();
        super.destroy();
        destroyed = true;
    }
    /**
     * Restarts the periodic jumping behavior of the Jumper entity after it has been respawned.
     */
    public void respawned() {
        destroyed = false; // Reset destroyed flag
        startJumping(); // Restart jumping
    }
}
