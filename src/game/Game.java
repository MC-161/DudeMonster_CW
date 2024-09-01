package game;

import city.cs.engine.*;
import game.Controllers.PlayerCollisionListener;
import game.Controllers.PlayerController;
import game.Entities.Player;
import game.Utility.GiveFocus;
import game.Utility.SoundLoader;
import game.World.*;
import javax.swing.*;
import java.awt.*;

/**
 * The main entry point for the game application.
 * This class initializes and manages the game world, view, and controller,
 * as well as orchestrates the game loop and level transitions.
 * <p>
 * The game loop runs continuously, updating the game state and rendering
 * the game view at a consistent rate. It also handles the transition between
 * different game levels, progressing through the levels as the player advances.
 * <p>
 * This class provides methods to initialize components, run the game loop,
 * render the game view, and transition to the next game level.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class Game {

    private GameLevel currentLevel;
    private final GameView view;
    private PlayerController controller;
    private boolean endGameToggled;
    SoundClip newLevelSound = SoundLoader.NewLevelSound;

    /** Initialise a new Game. */
    public Game() {
        // Create the game world
        currentLevel = new Level1(this, "data/gameTheme.wav");
        // Create the game view
        view = new GameView(currentLevel, 1200, 800, currentLevel.getLevelPlayer(), this);
        controller = new PlayerController(currentLevel.getPlayer(), currentLevel);
        view.addKeyListener(controller);
        view.addMouseListener(new GiveFocus());

        view.setBac(currentLevel.getBackgroundImage());
        // Create the main frame
        final JFrame frame = new JFrame("Dude Monster Game");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        view.togglePauseMenu();
        initializeComponents();
        // Start the game loop
        runGameLoop();
    }
    public void initializeComponents() {
        // Initialize PlayerCollisionListener after GameView is created
        PlayerCollisionListener playerCollisionListener = new PlayerCollisionListener(view, currentLevel);
        currentLevel.getPlayer().addCollisionListener(playerCollisionListener);
    }

    /** Run the game loop. */
    private void runGameLoop(  ) {
        // Set up variables for timing
        final double GAME_HERTZ = 500.0;
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        final int MAX_UPDATES_BEFORE_RENDER = 6;
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();

        // Main game loop
        while (true) {
            double now = System.nanoTime();
            int updateCount = 0;

            // Update game state
            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                // No need to call world.step() here since it's automatically simulated
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            // Render game if needed
            if (now - lastRenderTime >= TIME_BETWEEN_UPDATES) {
                // Render game
                renderGame();
                lastRenderTime = now - ((now - lastRenderTime) % TIME_BETWEEN_UPDATES);
            }

            if (currentLevel instanceof GameEndLevel && !endGameToggled) {
                view.toggleEndGame();
                endGameToggled = true; // Set the flag to true to indicate that end game has been toggled
            }

            Thread.yield();
        }
    }

    /** Render the game. */
    private void renderGame() {
        view.updateView();
        view.repaint();
    }

    public GameView getView() {
        return view;
    }

    /**
     * Progresses the game to the next level.
     * This method stops the current level, creates the next level based on the current level,
     * adds collision listeners, updates the game view, sets the background image, updates the player controller,
     * starts the new level, and toggles the completion menu.
     */
    public void goToNextLevel() {
        if (currentLevel instanceof Level1) {
            currentLevel.stop();
            currentLevel = new Level2(this, "data/Level_Res/Level2_Res/Level2Music.wav");
        } else if (currentLevel instanceof Level2) {
            currentLevel.stop();
            currentLevel = new Level3(this, "data/Level_Res/Level3_Res/Level3Music.wav");
        } else if (currentLevel instanceof Level3) {
            currentLevel.stop();
            currentLevel = new GameEndLevel(this, "data/Level_Res/Level3_Res/Level3Music.wav");
        }

        Player newPlayer = currentLevel.getPlayer();
        PlayerCollisionListener playerCollisionListener = new PlayerCollisionListener(view, currentLevel);
        newPlayer.addCollisionListener(playerCollisionListener);

        view.setWorld(currentLevel);
        view.setPlayer(newPlayer);
        view.setBac(currentLevel.getBackgroundImage());

        controller.updatePlayer(newPlayer, currentLevel);
        newLevelSound.play();
        currentLevel.start(); // Start the new level

        view.toggleCompletion();
    }

    /**
     * The main entry point for the application.
     * This method creates an instance of the Game class, initializing and starting the game.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        new Game();
    }
}