package game.Entities;

import city.cs.engine.*;
import game.World.GameLevel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Entity extends Walker {
    public Entity(GameLevel world, Shape shape) {
        super(world);
        SolidFixture fixture = new SolidFixture(this, shape);
        fixture.setFriction(30f);
    }
}
