package de.gymw.inf.breakout.game;

import de.gymw.inf.breakout.game.brick.Brick;
import de.gymw.inf.breakout.views.GameView;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Ball extends DynamicWorldObject {

    float d;
    float trueSpeed;
    Paddle pd;

    public boolean fireMode;

    float startPosX, startPosY;

    private Brick[][] bricks;

    private float maxHeight;

    public Ball(GameView parent, float x, float y, float d, int colstr, int colfill, PVector speed, Paddle pd) {
        super(parent, x, y, d, d, colstr, colfill, speed);
        this.startPosX = x;
        this.startPosY = y;
        this.d = d;
        this.trueSpeed = 1;
        this.pd = pd;
        this.fireMode = false;
    }

    @Override
    public void draw() {
        PApplet p = parent.b;
        p.stroke(colstr);
        p.strokeWeight(2);
        if(fireMode) {
            p.fill(p.color(235, 125, 0));
        } else {
            p.fill(colfill);
        }

        p.ellipseMode(PConstants.CENTER);

        p.ellipse(this.x, this.y, this.d, this.d);
    }

    /**
     * @param tpf Ticks per Frame is the number of cycles and collision checking
     *            should be done per Frame. This is implemented so that the ball
     *            doesn't skip boundaries while moving.
     * */
    public void updatePosition(int tpf) {
        for(int i = 1; i <= tpf; i++) {
            checkForWallCollision();
            checkForPaddleCollision();
            checkForDeath();
            collideNear(bricks);
            this.x += this.getXSpeed() * this.trueSpeed;
            this.y += this.getYSpeed() * this.trueSpeed;
            //this.x = parent.b.mouseX;
            //this.y = parent.b.mouseY;
        }
    }

    private void checkForWallCollision() {
        if(this.x + this.w/2 >= parent.b.width) {
            this.speed.x = -Math.abs(this.getXSpeed());
        } else if(this.x - this.w/2 <= 0) {
            this.speed.x = Math.abs(this.getXSpeed());
        }

        if(this.y + this.h/2 >= parent.b.height) {
            this.speed.y = -Math.abs(this.getYSpeed());
        } else if(this.y - this.h/2 <= 0) {
            this.speed.y = Math.abs(this.getYSpeed());
        }
    }

    private void checkForPaddleCollision() {
        if(this.collidesAtTop(pd)) {
            this.speed.y = -Math.abs(this.getYSpeed());
            this.fireMode = false;
        }
    }

    private void checkForDeath() {
        if(this.maxHeight > 0 && this.y >= this.maxHeight) {
            parent.die();
        }
    }

    /**
     * This function registers the array that will be tested
     *
     * @param bricks 2d array of bricks that will be tested for collision
     * */
    public void collideNear(Brick[][] bricks) {
        for (Brick[] brow : bricks) {
            for(Brick brick : brow) {
                if (brick.active()) {
                    checkCollision(brick);
                }
            }
        }
    }

    void checkCollision(Brick brick) {
        if(this.collidesAtTop(brick)) {
            brick.hitAction();
            if(!fireMode) this.speed.y = -Math.abs(this.getYSpeed());
        } else if(this.collidesAtBottom(brick)) {
            brick.hitAction();
            if(!fireMode) this.speed.y = Math.abs(this.getYSpeed());
        } else if(this.collidesAtLeft(brick)) {
            brick.hitAction();
            if(!fireMode) this.speed.x = -Math.abs(this.getXSpeed());
        } else if(this.collidesAtRight(brick)) {
            brick.hitAction();
            if(!fireMode) this.speed.x = Math.abs(this.getXSpeed());
        }
    }

    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void resetPosition() {
        this.x = this.startPosX;
        this.y = this.startPosY;
    }
}
