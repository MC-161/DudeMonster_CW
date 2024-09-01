package game.Controllers;

import city.cs.engine.SoundClip;
import game.Entities.Player;
import game.Utility.SoundLoader;
import game.World.GameLevel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    private static  float WALKING_SPEED; // Walking speed in meters per second
    private static  float RUNNING_SPEED; // Running speed in meters per second
    private GameLevel world;

    private Player player; // Initialize player here
    private boolean isShiftPressed = false; // Flag to track if shift key is pressed
    SoundClip playerJumpSound = SoundLoader.playerJumpSound;

    public PlayerController(Player player, GameLevel world) {
        this.player = player;
        this.world = world;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();


        // Check if shift key is pressed
        if (keyCode == KeyEvent.VK_SHIFT) {
            isShiftPressed = true;
            return;
        }
        if (keyCode == KeyEvent.VK_H) {
            // Get the position of the player
            double playerX = player.getPosition().x;

            // Determine the direction the player is facing (assuming facing right if no specific direction is determined)
            boolean isFacingRight = ((Player) player).isFacingRight();

            // Trigger the player to throw a rock, passing the direction information
            ((Player) player).throwRock(world, isFacingRight); // Pass 'world' parameter
        }

        // Check if movement key is pressed
        float speed = isShiftPressed ? player.getRunningSpeed() : player.getWalkingSpeed();
        switch (keyCode) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (isShiftPressed) {
                    ((Player) player).startRunning(-speed); // Run left
                } else {
                    player.startWalking(-speed); // Walk left
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (isShiftPressed) {
                    ((Player) player).startRunning(speed); // Run right
                } else {
                    player.startWalking(speed); // Walk right
                }
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_W:
                if (isShiftPressed) {
                    player.jump((float) (8 * 1.8)); // Jump
                    playerJumpSound.play();
                } else {
                    player.jump(8); // Jump
                    playerJumpSound.play();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Check if shift key is released
        if (keyCode == KeyEvent.VK_SHIFT) {
            isShiftPressed = false;
            return;
        }

        // Stop walking or running when left or right key is released
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT ||
                keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            if (isShiftPressed) {
                ((Player) player).stopRunning();
            } else {
                player.stopWalking();
            }
        }
    }

    public void updatePlayer(Player newPlayer,GameLevel world){
        this.player = newPlayer;
        this.world = world;
    }
}
