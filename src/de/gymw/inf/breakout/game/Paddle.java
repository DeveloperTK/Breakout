package de.gymw.inf.breakout.game;

import de.gymw.inf.breakout.Breakout;
import de.gymw.inf.breakout.views.GameView;
import processing.core.PConstants;

public class Paddle extends WorldObject {

    Breakout p;
    private float trueSpeed;

    public Paddle(GameView parent, float x, float y, float w, float h, int colstr, int colfill) {
        super(parent, x, y, w, h, colstr, colfill);
        this.p = parent.b;
        trueSpeed = 6;
    }

    // move(1) = right | move{-1} = left
    public void move(int direction) {
        if(direction > 0 && this.x - this.w/2 >= 0) {
            this.x += this.trueSpeed * direction;
        } else if(direction < 0 && this.x + this.w/2 <= parent.b.width) {
            this.x += this.trueSpeed * direction;
        }
    }

    @Override
    public void draw() {
        p.pushStyle();

        p.strokeWeight(2);
        p.stroke(colstr);
        p.fill(colfill);

        p.rectMode(PConstants.CENTER);
        p.rect(x, y, w, h);

        p.popStyle();
    }

    public float getTrueSpeed() {
        return trueSpeed;
    }

    public void setTrueSpeed(float trueSpeed) {
        this.trueSpeed = trueSpeed;
    }
}
