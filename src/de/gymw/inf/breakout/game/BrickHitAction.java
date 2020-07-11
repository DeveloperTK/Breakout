package de.gymw.inf.breakout.game;

import de.gymw.inf.breakout.game.brick.Brick;

public interface BrickHitAction {

    /*
    * This is something like a callback in weak typed languages
    * */
    void run(Brick brick);

}
