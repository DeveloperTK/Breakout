package de.gymw.inf.breakout.game.brick;

import de.gymw.inf.breakout.views.GameView;

public class SolidBrick extends Brick {

    public SolidBrick(GameView parent, float x, float y, float w, float h, int colstr, int colfill) {
        super(parent, x, y, w, h, parent.b.color(0, 149, 255), parent.b.color(0, 238, 255));
        super.setHitAction(this::hit);
    }

    private void hit(Brick brick) {
        parent.playSound(3);
    }

}
