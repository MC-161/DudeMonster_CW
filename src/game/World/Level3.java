package game.World;

import city.cs.engine.*;
import city.cs.engine.Shape;
import game.Controllers.CannonSensorListener;
import game.Controllers.JumperController;
import game.Controllers.MiniCannonSensorListener;
import game.Controllers.PatrollerController;
import game.Entities.Jumper;
import game.Entities.Patroller;
import game.Entities.Player;
import game.Game;
import game.Items.Cannon;
import game.Items.Coins;
import game.Items.DeathBlock;
import game.Items.MiniCannon;
import game.Levels.FinishBlock;
import game.Utility.ItemFactory;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class Level3 extends GameLevel{
    private static final BodyImage GroundImage = new BodyImage("data/Level_Res/Level3_Res/Ground1.png", 1.8f * 2); // Adjust the height multiplier as needed
    private static final BodyImage PlatformImage = new BodyImage("data/Level_Res/Level3_Res/Platform1.png", 1.5f * 2); // Adjust the height multiplier as needed
    private static final BodyImage TitleImage = new BodyImage("data/Level_Res/Level2Title.png", 4f * 2); // Adjust the height multiplier as needed
    private static final BodyImage SensorBlockImage = new BodyImage("data/Level_Res/Level3_Res/Ground1.png", 0.5f * 2);
    private static final Shape PLatformShape = new BoxShape(6f,1.2f); // Adjust the height multiplier as needed
    private static final Shape GroundShape = new BoxShape(15f, 2.2f); // Adjust the height multiplier as needed
    private static final Shape SensorShape = new BoxShape(1f, 1f); // Adjust the height multiplier as needed
    private final Vec2[] coinInitialPositions = {new Vec2(-40f, 1f), new Vec2(59f, 20f), new Vec2(110f, 12f), new Vec2(130f, -4f), new Vec2(90f, -4f), new Vec2(110f, -15f)};

    private Patroller patroller2;
    private Cannon cannon;
    private Cannon cannon1;

    public Level3(Game game, String musicPath) {
        super(game, musicPath);
        player = getPlayer();
        player.setPosition(new Vec2(0, -5));

        // GAME TITLE
        StaticBody Title = ItemFactory.createBlock(this, new Vec2(0f, 15f), TitleImage,GroundShape );

        Vec2[] groundPositions = {new Vec2(0f, -11.5f), new Vec2(110f, 5f)};
        Vec2[] platformPositions = {new Vec2(-40f, -4f),new Vec2(35f, -6f),new Vec2(110f, -18f), new Vec2(60f, -1f), new Vec2(36f, 8f), new Vec2(59f, 16f),new Vec2(140f, 2f), new Vec2(130f, -8f), new Vec2(120f, -14f), new Vec2(100f, -14f), new Vec2(90f, -8f), new Vec2(80f, 2f), new Vec2(150f, -9f)};
        ItemFactory.createGrounds(this, GroundImage, GroundShape, groundPositions);
        ItemFactory.createGrounds(this, PlatformImage, PLatformShape, platformPositions);
        StaticBody ground = ItemFactory.createBlock(this, new Vec2(110f, 18f), SensorBlockImage, SensorShape);
        StaticBody ground2 = ItemFactory.createBlock(this, new Vec2(110f, -18f), SensorBlockImage, SensorShape);

        // Coins
        new Coins(this).setPosition(new Vec2(-40f, 1f));
        new Coins(this).setPosition(new Vec2(59f, 20f));
        StaticBody CPCoin = new Coins(this);
        CPCoin.setPosition(new Vec2(110f, 12f));
        new Coins(this).setPosition(new Vec2(130f, -4f));
        new Coins(this).setPosition(new Vec2(90f, -4f));
        new Coins(this).setPosition(new Vec2(110f, -15f));

        Cannon cannon =  new Cannon(this, new Vec2(98, 8.5f));
        cannon.setFacingRight(true);

        MiniCannon miniCannon = new MiniCannon(this, new Vec2(100, -11.5f));
        miniCannon.setFacingRight(true);

        Sensor TopCannon = new Sensor(ground, new BoxShape(12, 10));
        TopCannon.addSensorListener(new CannonSensorListener(cannon));

        Sensor SmallCannon = new Sensor(ground2, new BoxShape(12, 10));
        SmallCannon.addSensorListener(new MiniCannonSensorListener(miniCannon));

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
        return new Vec2(90, -6f);
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
        return new ImageIcon("data/Level3_BG.png").getImage();
    }
    // Override to initialize the patroller specific to Level2
    @Override
    protected void initializePatroller() {
        patroller = new Patroller(this, 56, 64);
        patroller.setPosition(new Vec2(60, 2));
        setPatrollerInfo(new Vec2(60, 2), 56, 64);
        addStepListener(new PatrollerController(patroller));
    }

    @Override
    protected void initializeJumper() {
        jumper = new Jumper(this, 135, 145);
        jumper.setPosition(new Vec2(140, 4f));
        addStepListener(new JumperController(jumper));
        jumper.setLeftBound(135);
        jumper.setRightBound(145);
    }


}
