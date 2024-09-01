package game.Items;

import city.cs.engine.*;
import game.World.GameLevel;

public class Coins extends Collectible {
    private static final Shape coinsShape = new CircleShape(1);
    private static final BodyImage CoinImage = new BodyImage("data/Level_Res/Coin.gif", 1f * 2);

    public Coins(GameLevel w) {
        super(w, coinsShape, CoinImage);
    }
}
