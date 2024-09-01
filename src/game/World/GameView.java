package game.World;

import city.cs.engine.UserView;
import game.Entities.Player;
import game.Game;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents the graphical view of the game world.
 * This class extends the UserView class from the city.cs.engine package
 * to provide a custom view for the game world. It handles rendering the
 * background, foreground, and player-related information on the screen,
 * as well as managing interactive elements such as the pause menu, completion menu,
 * and end game panel.
 *
 * <p>The GameView class also includes methods to toggle the display of various menus,
 * update the view to follow the player's position, and handle mouse events for
 * interactive elements like the pause menu button.</p>
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */

public class GameView extends UserView {
    private final Game game;
    private Player player;
    private GameLevel currentLevel;
    private Image bac ;
    private boolean isPauseMenuOpen;
    private BufferedImage menuButtonIcon;
    private PauseMenuPanel pauseMenuPanel;
    private boolean isCompletionMenuOpen;
    private CompletionMenuPanel completionMenuPanel;
    private boolean isEndGameOpen;
    private EndGamePanel endGamePanel;

    /**
     * Initializes a new instance of the GameView class.
     *
     * @param world  The game world associated with this view.
     * @param width  The width of the view.
     * @param height The height of the view.
     * @param player The player object in the game world.
     * @param game   The main game object.
     */

    public GameView(GameLevel world, int width, int height, Player player, Game game) {
        super(world, width, height);
        this.player = player;
        this.currentLevel = world;
        this.game = game;
        addMouseListener(new PauseMenuMouseListener());
        try {
            menuButtonIcon = ImageIO.read(new File("data/PauseMenu/Settings.png")); // Adjust the path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Toggles the display of the pause menu.
     * If the pause menu is currently open, it will be closed, and vice versa.
     * This method also pauses or resumes the game world accordingly.
     */
    public void togglePauseMenu() {
        isPauseMenuOpen = !isPauseMenuOpen;
        if (isPauseMenuOpen) {
            // Create a new instance of PauseMenuPanel and add it to the GameView
            currentLevel.pauseWorld();
            pauseMenuPanel = new PauseMenuPanel(player, currentLevel, this);
            int pauseMenuX = getWidth() / 2 - pauseMenuPanel.getWidth() / 2;
            int pauseMenuY = getHeight() / 2 - pauseMenuPanel.getHeight() / 2;
            pauseMenuPanel.setLocation(pauseMenuX, pauseMenuY);
            add(pauseMenuPanel);
        } else {
            // Remove the existing PauseMenuPanel instance from the GameView
            remove(pauseMenuPanel);
            currentLevel.resumeWorld();
        }
        // Repaint the GameView to reflect the changes
        repaint();
    }

    /**
     * Toggles the display of the completion menu.
     * If the completion menu is currently open, it will be closed, and vice versa.
     * This method also pauses or resumes the game world accordingly.
     */
    public void toggleCompletion() {
        currentLevel.stop();
        isCompletionMenuOpen = !isCompletionMenuOpen;
        if (isCompletionMenuOpen) {
            // Create a new instance of PauseMenuPanel and add it to the GameView
            currentLevel.pauseWorld();
            completionMenuPanel = new CompletionMenuPanel(player, this , player.getCoinsCollected(),game);
            int completionMenuX = getWidth() / 2 - completionMenuPanel.getWidth() / 2;
            int completionMenuY = getHeight() / 2 - completionMenuPanel.getHeight() / 2;
            completionMenuPanel.setLocation(completionMenuX, completionMenuY);
            add(completionMenuPanel);
        } else {
            // Remove the existing PauseMenuPanel instance from the GameView
            remove(completionMenuPanel);
            currentLevel.resumeWorld();
        }
        // Repaint the GameView to reflect the changes
        repaint();
    }


    /**
     * Toggles the display of the end game panel.
     * If the end game panel is currently open, it will be closed, and vice versa.
     * This method also pauses or resumes the game world accordingly.
     */
    public void toggleEndGame() {
        isEndGameOpen = !isEndGameOpen;
        if (isEndGameOpen) {
            // Create a new instance of PauseMenuPanel and add it to the GameView
            currentLevel.pauseWorld();
            endGamePanel = new EndGamePanel(player, this ,game);
            int endGamePanelX = getWidth() / 2 - endGamePanel.getWidth() / 2;
            int endGamePanelY = getHeight() * 3 / 4 - endGamePanel.getHeight() / 2; // Adjusted calculation
            endGamePanel.setLocation(endGamePanelX, endGamePanelY);
            add(endGamePanel);
        } else {
            // Remove the existing PauseMenuPanel instance from the GameView
            remove(endGamePanel);
            currentLevel.resumeWorld();
        }
        // Repaint the GameView to reflect the changes
        repaint();
    }


    /**
     * Handles mouse events for the pause menu button.
     * This method toggles the pause menu when the menu button is clicked.
     */
    private class PauseMenuMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int clickX = e.getX();
            int clickY = e.getY();

            // Check if the click is within the menu button area
            int menuButtonX = getWidth() - menuButtonIcon.getWidth() - 32;
            int menuButtonY = 32;
            if (clickX >= menuButtonX && clickY <= menuButtonY + menuButtonIcon.getHeight()) {
                togglePauseMenu();
            }
        }
    }

    /**
     * Sets the background image of the game view.
     *
     * @param bac The background image to be set.
     */
    public void setBac(Image bac) {
        this.bac = bac;
    }
    @Override
    protected void paintBackground(Graphics2D g) {
        super.paintBackground(g);
        g.drawImage(bac, 0, 0, getWidth(), getHeight(), this);
    }
    /**
     * Updates the view's position to follow the player.
     * This method adjusts the view's center based on the player's position,
     * allowing the view to follow the player's movements.
     */
    public void updateView() {
        Vec2 playerPosition = player.getPosition();

        float viewCenterX = playerPosition.x;
        float viewCenterY = playerPosition.y + 10;
        // Set the view's position
        setView(new Vec2(viewCenterX, viewCenterY), 10);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);
        // Render the menu button icon
        int menuButtonX = getWidth() - menuButtonIcon.getWidth() - 32;
        int menuButtonY = 32;
        g.drawImage(menuButtonIcon, menuButtonX, menuButtonY, null);

        // Retrieve the maximum health from the Player class
        int maxHealth = Player.getMaxHealth();

        // Define the font type, style, and size
        Font font = new Font("ARIAL", Font.PLAIN, 18); // Change the font type, style, and size as desired

        // Set the font
        g.setFont(font);


        // Render player stats
        g.setColor(Color.WHITE);
        g.drawString("Health: " + player.getHealth(), 20, 30);
        g.drawString("Coins: " + player.getCoinsCollected() + "/" + player.getCoinsNeeded(), 20, 60);

        // Define the dimensions and position of the health bar
        int barWidth = 100; // Adjust the width of the health bar
        int barHeight = 20; // Adjust the height of the health bar
        int barX = 20; // Adjust the X position of the health bar
        int barY = 80; // Adjust the Y position of the health bar

        // Calculate the width of the health bar based on the player's health
        int healthWidth = (int) (((double) player.getHealth() / maxHealth) * barWidth);

        // Draw the outline of the health bar
        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barWidth, barHeight);

        // Fill the health bar with a color based on the player's health
        Color healthColor = Color.GREEN; // Default color is green
        if (player.getHealth() < maxHealth / 2) {
            healthColor = Color.YELLOW; // Change color to yellow if health is below 50%
        }
        if (player.getHealth() < maxHealth / 4) {
            healthColor = Color.RED; // Change color to red if health is below 25%
        }
        g.setColor(healthColor);
        g.fillRect(barX, barY, healthWidth, barHeight);

    }

    public void setPlayer (Player newPlayer){
        player = newPlayer;
    }
    public void unPause(){
        currentLevel.start();
    }
}
