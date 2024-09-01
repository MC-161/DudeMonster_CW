package game.Controllers;
import city.cs.engine.*;
import game.Entities.Patroller;
import game.Entities.Player;
import game.Game;
import game.Items.Coins;
import game.Items.DeathBlock;
import game.Items.Health;
import game.Levels.FinishBlock;
import game.Utility.SoundLoader;
import game.World.GameLevel;
import game.World.GameView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;
/**
 * A collision listener responsible for handling collisions involving the player.
 * This class manages interactions between the player and various game entities such as coins, health pickups,
 * death blocks, enemies (Patrollers), and level completion blocks.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class PlayerCollisionListener implements CollisionListener{
    private final Player player;
    private final GameView gameView;
    private GameLevel currentLevel;
    private static final int DEFAULT_RUNNING_SPEED = 12;
    private static final int INCREASED_RUNNING_SPEED = 16;
    private static final long SPEED_INCREASE_DURATION = 10000; // 5 seconds
    SoundClip superModeSound = SoundLoader.SuperModeSound;
    /**
     * Constructs a PlayerCollisionListener with the specified game view and current game level.
     *
     * @param gameView     The game view associated with the current game.
     * @param currentLevel The current game level.
     */
    public PlayerCollisionListener(GameView gameView, GameLevel currentLevel) {
        this.player = currentLevel.getPlayer();
        this.gameView = gameView;
        this.currentLevel = currentLevel;
    }
    /**
     * Handles collision events between the player and other bodies in the game world.
     *
     * @param e The collision event.
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Coins) {
            player.addCoins();
            e.getOtherBody().destroy();
        }if (e.getOtherBody() instanceof Health) {
            // Increase player's running speed temporarily
            player.setRunningSpeed(INCREASED_RUNNING_SPEED);
            e.getOtherBody().destroy();
            currentLevel.getGameMusic().pause();
            superModeSound.play();


            // Schedule a task to revert the speed back to default after a certain duration
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    player.setRunningSpeed(DEFAULT_RUNNING_SPEED);
                    superModeSound.stop();
                    currentLevel.getGameMusic().resume();
                }
            }, SPEED_INCREASE_DURATION);

        }
        if (e.getOtherBody() instanceof DeathBlock) {
            player.decreaseHealth(100);
        }
        if (e.getOtherBody() instanceof Patroller) {
            player.decreaseHealth(20);
        }if (e.getOtherBody() instanceof FinishBlock) {
            if (player.getCoinsCollected() == currentLevel.getLevelCoins()) {
                player.setLevelComplete(true);
                currentLevel.getGameMusic().stop();
                completeSound.play();
                gameView.toggleCompletion();
            }else{
                JOptionPane.showMessageDialog(null, "You need to collect all the coins to complete the level", "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    private static SoundClip completeSound;

    static {
        try {
            completeSound = new SoundClip("data/Level_Res/LevelComplete.wav");
            System.out.println("Loading books sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
