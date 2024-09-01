package game.Utility;
import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * A utility class for loading sound clips used in the game.
 * It provides static fields to access various sound clips such as player actions, enemy actions, level completion, etc.
 * Each sound clip is loaded statically and exceptions are caught and printed to the console if loading fails.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class SoundLoader {
    /**
     * The sound clip for when the player throws a rock.
     */
    public static SoundClip playerThrowRockSound;
    /**
     * The sound clip for when the player collects a coin.
     */
    public static SoundClip playerCoinCollectSound;
    /**
     * The sound clip for when the level is completed.
     */
    public static SoundClip completeSound;
    /**
     * The sound clip for when an enemy shoots.
     */
    public static SoundClip ShotSound;
    public static SoundClip playerJumpSound;
    public static SoundClip enemyJumpSound;
    public static SoundClip playerDeathSound;
    public static SoundClip enemyDeathSound;
    public static  SoundClip SuperModeSound;
    public static  SoundClip NewLevelSound;

    static {
        try {
            playerThrowRockSound = new SoundClip("data/PlayerRes/rockThrowSound.wav");
            System.out.println("Loading rock throw sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            playerCoinCollectSound = new SoundClip("data/Level_Res/CoinCollected.wav");
            System.out.println("Loading coin collected sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            completeSound = new SoundClip("data/Level_Res/LevelComplete.wav");
            System.out.println("Loading Complete");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            ShotSound = new SoundClip("data/EnemyRes/EnemyShot.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            playerJumpSound = new SoundClip("data/PlayerRes/PlayerJump.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            enemyJumpSound = new SoundClip("data/EnemyRes/EnemyJump.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    static {
        try {
            playerDeathSound = new SoundClip("data/PlayerRes/PlayerDeath.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    static {
        try {
            enemyDeathSound = new SoundClip("data/EnemyRes/EnemyDeath.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            SuperModeSound = new SoundClip("data/Level_Res/SuperMode.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    static {
        try {
            NewLevelSound = new SoundClip("data/NewLevel.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }


}
