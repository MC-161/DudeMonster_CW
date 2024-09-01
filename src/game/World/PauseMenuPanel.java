package game.World;
import city.cs.engine.SoundClip;
import game.Entities.Player;

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
 * Represents the panel displayed when the game is paused.
 * This panel contains buttons for resuming the game, restarting the level, and exiting the game.
 * It also displays an image for the menu background.
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */
public class PauseMenuPanel extends JPanel {
    private BufferedImage background;
    private static SoundClip buttonClickSound;


    /**
     * Initializes a new instance of the PauseMenuPanel class.
     *
     * @param player       The player instance.
     * @param currentLevel The current level instance.
     * @param gameView     The game view instance.
     */
    public PauseMenuPanel(Player player, GameLevel currentLevel, GameView gameView) {
        setLayout(null); // Set layout to null for absolute positioning
        loadBackgroundImage();

        // Set the size of the pause menu panel
        int pauseMenuWidth = 350;
        int pauseMenuHeight = 200;
        setSize(pauseMenuWidth, pauseMenuHeight);

        // Create and add components to the pause menu panel
        // Load the image
        ImageIcon menuIcon = new ImageIcon("data/PauseMenu/Menu.png");
        JLabel menuLabel = new JLabel(menuIcon);
        menuLabel.setBounds(130, 20, menuIcon.getIconWidth(), menuIcon.getIconHeight());
        add(menuLabel);

        // Create buttons with images
        ImageIcon PlayIcon = new ImageIcon("data/PauseMenu/PlayButton.png");
        JButton PlayButton = new JButton(PlayIcon);
        PlayButton.setBounds(50, 65, PlayIcon.getIconWidth(), PlayIcon.getIconHeight());
        add(PlayButton);

        ImageIcon restartIcon = new ImageIcon("data/PauseMenu/restart_button.png");
        JButton restartButton = new JButton(restartIcon);
        restartButton.setBounds(50, 105, restartIcon.getIconWidth(), restartIcon.getIconHeight());
        add(restartButton);

        ImageIcon exitIcon = new ImageIcon("data/PauseMenu/ExitButton.png");
        JButton exitButton = new JButton(exitIcon);
        exitButton.setBounds(50, 140, exitIcon.getIconWidth(), exitIcon.getIconHeight());
        add(exitButton);

        ImageIcon controlsIcon = new ImageIcon("data/PauseMenu/Controls.png");
        JLabel controlsLabel = new JLabel(controlsIcon);
        controlsLabel.setBounds(125, 55, controlsIcon.getIconWidth(), controlsIcon.getIconHeight());
        add(controlsLabel);
        PlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel.resumeWorld();
                gameView.togglePauseMenu();
                buttonClickSound.play();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClickSound.play();
                player.decreaseHealth(100);
                gameView.togglePauseMenu();
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
     * Loads the background image for the pause menu panel.
     */
    private void loadBackgroundImage() {
        try {
            background = ImageIO.read(new File("data/PauseMenu/Frame.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Paints the components of the pause menu panel.
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

    static {
        try {
            buttonClickSound = new SoundClip("data/PauseMenu/ButtonPress.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}