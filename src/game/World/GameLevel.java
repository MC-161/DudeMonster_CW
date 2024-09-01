package game.World;

import city.cs.engine.*;
import game.Controllers.JumperController;
import game.Controllers.PatrollerController;
import game.Controllers.PlayerCollisionListener;
import game.Entities.Jumper;
import game.Entities.Patroller;
import game.Entities.Player;
import game.Game;
import game.Items.Coins;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;

/**
 * Represents a level in the game world.
 * This abstract class extends the World class from the city.cs.engine package
 * to define the structure and behavior of individual levels within the game.
 * It contains methods for initializing game objects, handling player respawn,
 * resetting the level state, managing game music, and pausing/resuming the level.
 *
 * <p>Subclasses of GameLevel implement specific levels by overriding abstract methods
 * such as {@code getLevelCoins}, {@code isComplete}, {@code getLevelPlayer}, etc.
 * Each subclass defines its own level layout, objectives, and completion conditions.</p>
 *
 * <p>The GameLevel class also provides methods for respawning objects such as coins,
 * patrollers, and jumpers, as well as resetting the level state to its initial state.</p>
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public abstract class GameLevel extends World {
    protected Patroller patroller;
    protected Jumper jumper;
    private Map<String, Object> patrollerInfo = new HashMap<>();
    Player player;
    private Map<String, Object> jumperInfo = new HashMap<>();
    private SoundClip gameMusic;
    public GameLevel(Game game , String musicPath) {
        super();
        player = new Player(this);
        player.setRunningSpeed(12);
        player.setWalkingSpeed(5);
        initializeGameMusic(musicPath);
        initializePatroller();
        initializeJumper();

    }

    /**
     * Initializes the background music for the level.
     *
     * @param musicPath The path to the audio file for the background music.
     */

    public void initializeGameMusic(String musicPath) {
        try {
            gameMusic = new SoundClip(musicPath);
            gameMusic.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }


    /**
     * Retrieves the player object associated with the level.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Respawns coins at their initial positions.
     *
     * @param coinInitialPositions The array of initial positions for the coins.
     */
    public void respawnObjects(Vec2[] coinInitialPositions){
        respawnCoins(coinInitialPositions);
    }
    private void respawnCoins(Vec2[] coinInitialPositions) {
        destroyAllCoins();
        // Loop through the array of initial positions and respawn coins at each position
        for (Vec2 position : coinInitialPositions) {
            Coins coin = new Coins(this);
            coin.setPosition(position); // Set the initial position of the coin
        }
    }
    /**
     * Respawns the jumper object at a specified position.
     *
     * @param pos The position where the jumper should be respawned.
     */
    public void respawnJumper(Vec2 pos) {
        if (jumper.isDestroyed()) {
            jumper = new Jumper(this, jumper.getLeftBound(), jumper.getRightBound());
            jumper.setPosition(pos);
            jumper.respawned();
            addStepListener(new JumperController(jumper));
        }
    }

    /**
     * Respawns the patroller object at a specified position.
     *
     * @param pos The position where the patroller should be respawned.
     * @param leftBound The left bound of the patroller's movement range.
     * @param rightBound The right bound of the patroller's movement range.
     */
    public void respawnPatroller(Vec2 pos, float leftBound, float rightBound){
        if (patroller.isDestroyed()){
            patroller = new Patroller(this, leftBound, rightBound);
            patroller.setPosition(pos);
            patroller.respawned();
            addStepListener(new PatrollerController(patroller));
        }
    }

    /**
     * Destroys all coins in the level.
     */
    private void destroyAllCoins() {
        for (StaticBody body : getStaticBodies()) {
            if (body instanceof Coins) {
                body.destroy(); // Destroy all coins in the level
            }
        }
    }


    /**
     * Resets the level to its initial state.
     *
     * @param coinInitialPositions The array of initial positions for the coins.
     * @param Patrollerpos The position where the patroller should be respawned, or {@code null} if not applicable.
     * @param leftBound The left bound of the patroller's movement range.
     * @param rightBound The right bound of the patroller's movement range.
     * @param jumperPos The position where the jumper should be respawned, or {@code null} if not applicable.
     */
    public void resetLevel(Vec2[] coinInitialPositions, Vec2 Patrollerpos , float leftBound, float rightBound, Vec2 jumperPos) {
        // Respawn the coins
        respawnObjects(coinInitialPositions);
        if(jumperPos != null){
            respawnJumper(jumperPos);
        }
        if (Patrollerpos != null){
            respawnPatroller(Patrollerpos, leftBound, rightBound);
            patroller.setHealth(100);
        }
        player.playerReset();
        player.setLevelComplete(false);
    }

    /**
     * Sets the information for respawning the patroller object.
     *
     * @param position The position where the patroller should respawn.
     * @param leftBound The left bound of the patroller's movement range.
     * @param rightBound The right bound of the patroller's movement range.
     */

    public void setPatrollerInfo(Vec2 position, float leftBound, float rightBound) {
        patrollerInfo.put("Position", position);
        patrollerInfo.put("leftBound", leftBound);
        patrollerInfo.put("rightBound", rightBound);
    }
    /**
     * Sets the information for respawning the jumper object.
     *
     * @param position The position where the jumper should respawn.
     * @param leftBound The left bound of the jumper's movement range.
     * @param rightBound The right bound of the jumper's movement range.
     */
    public void setJumperInfo(Vec2 position, float leftBound, float rightBound) {
        jumperInfo.put("Position", position);
        jumperInfo.put("leftBound", leftBound);
        jumperInfo.put("rightBound", rightBound);
    }
    public void pauseWorld(){
        this.stop();
    }
    public void resumeWorld(){
        this.start();
    }

    public Map<String, Object> getPatrollerInfo() {
        return patrollerInfo;
    }
    public Map<String, Object> getJumperInfo() {
        return jumperInfo;
    }

    public abstract int getLevelCoins();
    public SoundClip getGameMusic() {
        return gameMusic;
    }

    public abstract Vec2[] getCoinInitialPositions();
    public abstract Vec2 getJumperPosition();
    public abstract boolean isComplete();
    public abstract Player getLevelPlayer();

    public abstract Image getBackgroundImage();
    protected abstract void initializePatroller();
    protected abstract void initializeJumper();

}

