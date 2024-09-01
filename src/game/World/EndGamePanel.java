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
 * Represents the panel displayed at the end of the game.
 * This panel contains a completion message and an exit button.
 * It allows the player to exit the game upon completion.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class EndGamePanel extends JPanel {
    private BufferedImage background;
    private Player player;
    private GameView gameView;
    /**
     * Initializes a new instance of the EndGamePanel class.
     *
     * @param player   The player instance.
     * @param gameView The game view instance.
     * @param game     The game instance.
     */
    public EndGamePanel(Player player, GameView gameView,Game game) {
        this.player = player;
        this.gameView = gameView;

        setLayout(null); // Set layout to null for absolute positioning
        loadBackgroundImage();

        // Set the size of the completion menu panel
        int completionMenuWidth = 200;
        int completionMenuHeight = 100;
        setSize(completionMenuWidth, completionMenuHeight);

        // Create and add components to the completion menu panel
        // Load the image
        JLabel completionLabel = new JLabel("Game Completed!");
        completionLabel.setBounds(20, 15, 200, 30);
        completionLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        completionLabel.setForeground(Color.WHITE); // Set text color to white
        add(completionLabel);

        ImageIcon exitIcon = new ImageIcon("data/PauseMenu/ExitButton.png");
        JButton exitButton = new JButton(exitIcon);
        exitButton.setBounds(70, 50, exitIcon.getIconWidth(), exitIcon.getIconHeight());
        add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.play();
                System.exit(0);
            }
        });
    }

    /**
     * Loads the background image for the end game panel.
     */
    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(new File("data/PauseMenu/Frame.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the components of the end game panel.
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
