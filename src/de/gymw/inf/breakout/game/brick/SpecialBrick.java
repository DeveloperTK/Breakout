package de.gymw.inf.breakout.game.brick;

import de.gymw.inf.breakout.game.BrickHitAction;
import de.gymw.inf.breakout.views.GameView;

public class SpecialBrick extends Brick {

    public SpecialBrick(GameView parent, float x, float y, float w, float h, int colstr, int colfill) {
        super(parent, x, y, w, h, colstr, colfill);
        setHitAction(this::hit);
    }

    private void hit(Brick brick) {
        brick.setActiveState(false);
        brick.getParent().addScore(100);
        brick.getParent().b.playSound("bump");
        brick.getParent().fire();
    }

}
