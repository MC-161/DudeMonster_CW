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
import game.Items.Health;
import game.Levels.FinishBlock;
import game.Utility.ItemFactory;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class Level2 extends GameLevel{
    private static final BodyImage GroundImage = new BodyImage("data/Level_Res/Level2_Res/Ground1.png", 1.8f * 2); // Adjust the height multiplier as needed
    private static final BodyImage PlatformImage = new BodyImage("data/Level_Res/Level2_Res/Platform1.png", 1.5f * 2); // Adjust the height multiplier as needed
    private static final BodyImage PlatformImage2 = new BodyImage("data/Level_Res/Level2_Res/Platform2.png", 1f * 2); // Adjust the height multiplier as needed
    private static final BodyImage TitleImage = new BodyImage("data/Level_Res/Level2Title.png", 4f * 2); // Adjust the height multiplier as needed
    private static final Shape PLatformShape = new BoxShape(2f,1.2f); // Adjust the height multiplier as needed
    private static final Shape PLatformShape2 = new BoxShape(9f,1.2f); // Adjust the height multiplier as needed
    private static final Shape GroundShape = new BoxShape(25.5f, 2.2f); // Adjust the height multiplier as needed
    private final Vec2[] coinInitialPositions = {new Vec2(38f, -2f), new Vec2(98f, 8f), new Vec2(118f, 23f), new Vec2(95f, -16f)};


    public Level2(Game game, String musicPath) {
        super(game, musicPath);
        player = getPlayer();
        player.setPosition(new Vec2(0, -5));

        // GAME TITLE
        StaticBody Title = ItemFactory.createBlock(this, new Vec2(0f, 15f), TitleImage,GroundShape );

        Vec2[] groundPositions = {new Vec2(0f, -11.5f),new Vec2(180f, -9.5f)};
        Vec2[] platformShapePositions = {new Vec2(38f, -8f),new Vec2(48f, -18f),new Vec2(95f, -20f),new Vec2(55f, -2.5f), new Vec2(98f, 4f)};
        Vec2[] platformShape2Positions = {new Vec2(73f, -24f),new Vec2(118f, -19f),new Vec2(75f, 1.5f),new Vec2(118f, 8f)};

        ItemFactory.createGrounds(this, GroundImage, GroundShape, groundPositions);
        ItemFactory.createGrounds(this, PlatformImage, PLatformShape, platformShapePositions);
        ItemFactory.createGrounds(this, PlatformImage2, PLatformShape2, platformShape2Positions);;

        // Coins
        new Coins(this).setPosition(new Vec2(38f, -2f));
        new Coins(this).setPosition(new Vec2(98f, 8f));
        new Coins(this).setPosition(new Vec2(118f, 20f));
        new Coins(this).setPosition(new Vec2(95f, -16f));
        //Health
        new Health(this).setPosition(new Vec2(55f, 1f));


        new FinishBlock(this, new Vec2(180f, 1f));
        ItemFactory.createBlock(this, new Vec2(180f, -9.5f), GroundImage, GroundShape);

        new DeathBlock(this).setPosition(new Vec2(40f, -40f));
    }

    @Override
    public int getLevelCoins() {
        return coinInitialPositions.length;
    }

    @Override
    public Vec2[] getCoinInitialPositions() {
        return coinInitialPositions;
    }

    @Override
    public Vec2 getJumperPosition() {
        return new Vec2(118f, 12f);
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
        return new ImageIcon("data/Level2_BG.jpg").getImage();
    }
    // Override to initialize the patroller specific to Level2
    @Override
    protected void initializePatroller() {
        patroller = new Patroller(this, 65, 80);
        patroller.setPosition(new Vec2(70, -5));
        setPatrollerInfo(new Vec2(70, -5), 65, 80);
        addStepListener(new PatrollerController(patroller));
    }

    @Override
    protected void initializeJumper() {
        jumper = new Jumper(this, 112, 124);
        jumper.setPosition(new Vec2(118f, 14f));
        addStepListener(new JumperController(jumper));
        jumper.setLeftBound(112);
        jumper.setRightBound(124);
    }


}
