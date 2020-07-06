package de.gymw.inf.breakout.game.brick;

import com.sun.tools.classfile.ConstantPool;
import de.gymw.inf.breakout.views.GameView;

public class SpecialBrick extends Brick {

    public SpecialBrick(GameView parent, float x, float y, float w, float h, int colstr, int colfill, int mode) {
        super(parent, x, y, w, h, colstr, colfill);
        switch(mode) {
            case 3:
                setHitAction(this::hitFire);
                break;
            case 4:
                setHitAction(this::hitDoubleScore);
                break;
            case 5:
                setHitAction(this::hitLargerPaddle);
                break;
            case 6:
                setHitAction(this::hitExtraLife);
                break;
            default:
                setHitAction(defaultAction);
                break;
        }
    }

    private void hitFire(Brick brick) {
        brick.setActiveState(false);
        brick.getParent().addScore(100);
        brick.getParent().b.playSound("powerup");
        brick.getParent().fire();
    }

    private void hitDoubleScore(Brick brick) {
        brick.setActiveState(false);
        brick.getParent().setMultiplier(2);
        brick.getParent().b.playSound("powerup");
        new Thread(() -> {
            try {
                Thread.sleep(10 * 1000);
                brick.getParent().setMultiplier(1);
            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void hitLargerPaddle(Brick brick) {
        brick.setActiveState(false);
        brick.getParent().paddle.w = 2 * brick.getParent().paddle.startWidth;
        brick.getParent().b.playSound("powerup");
        new Thread(() -> {
            try {
                Thread.sleep(10 * 1000);
                brick.getParent().paddle.w = brick.getParent().paddle.startWidth;
            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void hitExtraLife(Brick brick) {
        brick.setActiveState(false);
        brick.getParent().addScore(100);
        brick.getParent().b.playSound("1-up");
        brick.getParent().addLife();
    }

}
