package game.World;

import city.cs.engine.SoundClip;
import game.Entities.Player;
import game.Game;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents the panel displayed when the level is completed.
 * This panel contains buttons for proceeding to the next level and exiting the game,
 * as well as displaying the number of coins collected by the player.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */

public class CompletionMenuPanel extends JPanel {
    private BufferedImage background;
    private Player player;
    private GameView gameView;
    private int coinsCollected;

    /**
     * Initializes a new instance of the CompletionMenuPanel class.
     *
     * @param player         The player instance.
     * @param gameView       The game view instance.
     * @param coinsCollected The number of coins collected by the player.
     * @param game           The game instance.
     */
    public CompletionMenuPanel(Player player, GameView gameView, int coinsCollected, Game game) {
        this.player = player;
        this.gameView = gameView;
        this.coinsCollected = coinsCollected;

        setLayout(null); // Set layout to null for absolute positioning
        loadBackgroundImage();

        // Set the size of the completion menu panel
        int completionMenuWidth = 350;
        int completionMenuHeight = 250;
        setSize(completionMenuWidth, completionMenuHeight);

        // Create and add components to the completion menu panel
        // Load the image
        JLabel completionLabel = new JLabel("Level Completed!");
        completionLabel.setBounds(100, 15, 200, 50);
        completionLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        completionLabel.setForeground(Color.WHITE); // Set text color to white
        add(completionLabel);

        // Display coins collected
        // Load the coin image
        ImageIcon coinIcon = new ImageIcon("data/Level_Res/Coin.gif");
        int coinSpacing = 10; // Adjust the spacing as needed
        int totalCoinsWidth = coinsCollected * coinIcon.getIconWidth(); // Total width of all coins
        int totalSpacingWidth = (coinsCollected - 1) * coinSpacing; // Total width of spacing between coins
        int totalWidth = totalCoinsWidth + totalSpacingWidth; // Total width including spacing
        // Calculate the starting x-position to center the coins
        int startX = getWidth() / 2 - totalWidth / 2;
        for (int i = 0; i < coinsCollected; i++) {
            JLabel coinLabel = new JLabel(coinIcon);
            coinLabel.setBounds(startX + (i * (coinIcon.getIconWidth() + coinSpacing)), 90, coinIcon.getIconWidth(), coinIcon.getIconHeight());
            add(coinLabel);
        }



        // Create buttons with images
        ImageIcon nextLevelIcon = new ImageIcon("data/PauseMenu/NextButton.png");
        JButton nextLevelButton = new JButton(nextLevelIcon);
        nextLevelButton.setBounds(140, 130, nextLevelIcon.getIconWidth(), nextLevelIcon.getIconHeight()); // Adjust position as needed
        add(nextLevelButton);

        ImageIcon exitIcon = new ImageIcon("data/PauseMenu/ExitButton.png");
        JButton exitButton = new JButton(exitIcon);
        exitButton.setBounds(140, 180, exitIcon.getIconWidth(), exitIcon.getIconHeight());
        add(exitButton);

        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.play();
                if (player.isLevelComplete()) {
                    game.goToNextLevel();
                }
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.play();
                System.exit(0);
            }
        });
    }

    /**
     * Loads the background image for the completion menu panel.
     */
    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(new File("data/PauseMenu/Frame.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the components of the completion menu panel.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static SoundClip buttonClickSound;

    static {
        try {
            buttonClickSound = new SoundClip("data/PauseMenu/ButtonPress.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
