package game.World;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.Controllers.JumperController;
import game.Controllers.PatrollerController;
import game.Entities.Jumper;
import game.Entities.Patroller;
import game.Entities.Player;
import game.Game;
import game.Items.Coins;
import game.Items.DeathBlock;
import game.Levels.FinishBlock;
import game.Utility.ItemFactory;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the first level of the game.
 * This class extends the GameLevel class to define the specific layout,
 * obstacles, enemies, and collectibles for the first level.
 * It includes methods to initialize the level environment, such as creating platforms,
 * setting up the player's starting position, adding coins, defining the finish block,
 * and setting up enemies (e.g., patrollers). Can be Carried over to all 3 levels
 *
 * @author Mahfuz Chowdhury Mohammed.Chowdhury.10@city.ac.uk
 * @version 1.0
 * @since 1.0
 */

public class Level1 extends GameLevel{
    private static final BodyImage GroundImage = new BodyImage("data/Level_Res/Level1_Res/Ground.png", 4f * 2); // Adjust the height multiplier as needed
    private static final BodyImage PlatformImage = new BodyImage("data/Level_Res/Level1_Res/platform_2.png", 1.5f * 2); // Adjust the height multiplier as needed
    private static final BodyImage TitleImage = new BodyImage("data/Level_Res/Title.png", 4f * 2); // Adjust the height multiplier as needed
    private static final Shape PLatformShape = new BoxShape(4.6f,1.5f); // Adjust the height multiplier as needed
    private static final Shape GroundShape = new BoxShape(15f, 3f); // Adjust the height multiplier as needed
    private final Vec2[] coinInitialPositions = {new Vec2(48, 1f), new Vec2(90, 1f)};
    private static Image background = new ImageIcon("data/background.jpg").getImage();
    private int levelIndex = 1;

    /**
     * Initializes a new instance of the Level1 class.
     *
     * @param game      The game instance.
     * @param musicPath The path to the background music for the level.
     */
    public Level1(Game game, String musicPath) {
        super(game , musicPath);

        player = getPlayer();
        player.setPosition(new Vec2(0, -5));

        // GAME TITLE
        StaticBody Title = ItemFactory.createBlock(this, new Vec2(0f, 18f), TitleImage,GroundShape );

        //Level Grounds
        Vec2[] groundPositions = {new Vec2(-26f, -11.5f), new Vec2(0f, -11.5f), new Vec2(25.7f, -11.5f), new Vec2(70f, -11.5f), new Vec2(130f, 5f)};
        ItemFactory.createGrounds(this, GroundImage, GroundShape, groundPositions);

        // Finish Block
        new FinishBlock(this, new Vec2(130f, 20f));
        new DeathBlock(this).setPosition(new Vec2(40f, -25.5f));

        // Platforms and Coins
        StaticBody platform1 = new StaticBody(this, PLatformShape);
        platform1.setPosition(new Vec2(48, -3f));
        platform1.addImage(PlatformImage);

        new Coins(this).setPosition(new Vec2(48, 1f));

        StaticBody platform2 = new StaticBody(this, PLatformShape);
        platform2.setPosition(new Vec2(90f, -3f));
        platform2.addImage(PlatformImage);

        new Coins(this).setPosition(new Vec2(90, 1f));

        StaticBody platform3 = new StaticBody(this, PLatformShape);
        platform3.setPosition(new Vec2(105f, 1f));
        platform3.addImage(PlatformImage);
    }

    public int getLevelCoins() {
        return coinInitialPositions.length;
    }
    @Override
    public Vec2[] getCoinInitialPositions() {
        return coinInitialPositions;
    }

    @Override
    public Vec2 getJumperPosition() {
        return null;
    }

    @Override
    public boolean isComplete() {
        return getPlayer().isLevelComplete();
    }

    @Override
    public Player getLevelPlayer() {
        return player;
    }

    @Override
    public Image getBackgroundImage() {
        return new ImageIcon("data/background.jpg").getImage();
    }

    /**
     * Initializes the patroller enemy for the level.
     * This method creates and initializes a new instance of the Patroller class,
     * sets its position and movement boundaries, and adds a step listener to control its behavior.
     */
    @Override
    protected void initializePatroller() {
        patroller = new Patroller(this, 60, 80);
        patroller.setPosition(new Vec2(70, -5));
        setPatrollerInfo(new Vec2(70, -5), 60, 80);
        addStepListener(new PatrollerController(patroller));
    }

    /**
     * Initializes the jumper enemy for the level.
     * This method creates and initializes a new instance of the Jumper class,
     * sets its position, and adds a step listener to control its behavior.
     */
    @Override
    protected void initializeJumper() {
    }


}
