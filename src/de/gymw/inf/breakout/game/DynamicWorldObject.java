package de.gymw.inf.breakout.game;

import de.gymw.inf.breakout.views.GameView;
import processing.core.PVector;

public abstract class DynamicWorldObject extends WorldObject {

    PVector speed;

    /**
     * Constructor for movable objects
     * @param direction direction vector
     * */
    public DynamicWorldObject(GameView parent, float x, float y, float w, float h, int colstr, int colfill, PVector direction) {
        super(parent, x, y, w, h, colstr, colfill);
        this.speed = direction;
    }

    public abstract void draw();

    /**
     * changes the speed the object is currently travelling at
     * */
    void changeSpeed(float x, float y) {
        this.speed.x = x;
        this.speed.y = y;
    }

    /**
     * Checks whether this collides with the top of obj.
     * */
    boolean collidesAtTop(WorldObject obj) {
        // `this` should bounce of the top of `obj`, thus making the Y-speed negative
        // this.changeSpeed(this.getXSpeed(), -Math.abs(this.getYSpeed()));
        return this.y >= (obj.y - obj.h / 2F - 1) - this.h / 2F
                && this.y <= (obj.y - obj.h / 2F - 1)
                && this.x >= (obj.x - obj.w / 2F - 1)
                && this.x <= (obj.x + obj.w / 2F + 1);
    }

    /**
     * Checks whether this collides with the bottom of obj.
     * */
    boolean collidesAtBottom(WorldObject obj) {
        // `this` should bounce of the top of `obj`, thus making the Y-speed negative
        // this.changeSpeed(this.getXSpeed(), Math.abs(this.getYSpeed()));
        return this.y >= (obj.y + obj.h / 2F + 1)
                && this.y <= (obj.y + obj.h / 2F + 1) + this.h / 2F
                && this.x >= (obj.x - obj.w / 2F - 1)
                && this.x <= (obj.x + obj.w / 2F + 1);
    }

    /**
     * Checks whether this collides with the top of obj.
     * */
    boolean collidesAtLeft(WorldObject obj) {
        // `this` should bounce of the left side of `obj`, thus making the X-speed negative
        // this.changeSpeed(-Math.abs(this.getXSpeed()), this.getYSpeed());
        return this.y >= (obj.y - obj.h / 2F - 1)
                && this.y <= (obj.y + obj.h / 2F + 1)
                && this.x >= (obj.x - obj.w / 2F - 1) - this.w / 2F
                && this.x <= (obj.x - obj.w / 2F - 1);
    }

    /**
     * Checks whether this collides with the top of obj.
     * */
    boolean collidesAtRight(WorldObject obj) {
        // `this` should bounce of the top of `obj`, thus making the Y-speed negative
        // this.changeSpeed(this.getXSpeed(), -Math.abs(this.getYSpeed()));
        return this.y >= (obj.y - obj.h / 2F - 1)
                && this.y <= (obj.y + obj.h / 2F + 1)
                && this.x >= (obj.x + obj.w / 2F + 1)
                && this.x <= (obj.x + obj.w / 2F + 1) + this.w / 2F;
    }

    public float getXSpeed() {
        return this.speed.x;
    }

    public float getYSpeed() {
        return this.speed.y;
    }

    public void setSpeed(PVector speed) {
        this.speed = speed;
    }
}
