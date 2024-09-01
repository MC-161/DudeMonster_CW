package game.World;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import game.Entities.Player;
import game.Game;
import game.Items.DeathBlock;
import game.Utility.ItemFactory;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameEndLevel extends GameLevel{
    private static final BodyImage GroundImage = new BodyImage("data/Level_Res/Level1_Res/Ground.png", 4f * 2); // Adjust the height multiplier as needed
    private static final BodyImage TitleImage = new BodyImage("data/EndGameTitle.png", 4f * 2); // Adjust the height multiplier as needed
    private static final BodyImage fireWorkImg = new BodyImage("data/fireWork.gif", 6f * 2); // Adjust the height multiplier as needed

    private static final Shape GroundShape = new BoxShape(15f, 3f); // Adjust the height multiplier as needed
    private static final Shape fireWorkShape = new BoxShape(6f, 6f); // Adjust the height multiplier as needed

    public GameEndLevel(Game game, String musicPath) {
        super(game , musicPath);

        player = getPlayer();
        player.setPosition(new Vec2(0, -5));

        // GAME TITLE
        StaticBody Title = ItemFactory.createBlock(this, new Vec2(0f, 20f), TitleImage,GroundShape );
        ItemFactory.createBlock(this, new Vec2(0f, -11.5f), GroundImage,GroundShape );
        ItemFactory.createBlock(this, new Vec2(-20f, 10f), fireWorkImg,fireWorkShape );
        ItemFactory.createBlock(this, new Vec2(0f, 10f), fireWorkImg,fireWorkShape );
        ItemFactory.createBlock(this, new Vec2(20f, 10f), fireWorkImg ,fireWorkShape );
        ItemFactory.createBlock(this, new Vec2(0f, 4f), fireWorkImg ,fireWorkShape );
    }


    public int getLevelCoins() {
        return 0;
    }
    @Override
    public Vec2[] getCoinInitialPositions() {
        return new Vec2[0];
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
        return new ImageIcon("data/EndGameBG.jpg").getImage();
    }

    @Override
    protected void initializePatroller() {
        setPatrollerInfo(null,0,0);
    }

    @Override
    protected void initializeJumper() {
    }


}
