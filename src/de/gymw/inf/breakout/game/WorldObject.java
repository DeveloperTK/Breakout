package de.gymw.inf.breakout.game;

import de.gymw.inf.breakout.views.GameView;

public abstract class WorldObject {

    protected GameView parent;

    public float x, y, w, h;
    public int colstr, colfill;

    public WorldObject(GameView parent, float x, float y, float w, float h, int colstr, int colfill) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.colstr = colstr;
        this.colfill = colfill;

        this.parent = parent;
    }

    public abstract void draw();

}
