package de.gymw.inf.breakout.game.brick;

import de.gymw.inf.breakout.views.GameView;

public class HardBrick extends Brick {

    private int durability;

    public HardBrick(GameView parent, float x, float y, float w, float h, int colstr, int colfill) {
        super(parent, x, y, w, h, parent.b.color(127, 127, 127), parent.b.color(156, 0, 0));
        durability = 3;
        super.setHitAction(this::hit);
    }

    private void hit(Brick brick) {
        durability--;
        if(durability == 3) {
            colfill = parent.b.color(156, 0, 0);
        } else if(durability == 2) {
            colfill = parent.b.color(156, 156, 0);
        } else if(durability == 1) {
            colfill = parent.b.color(0, 156, 0);
        } else if(durability == 0) {
            brick.setActiveState(false);
        }
    }

}
