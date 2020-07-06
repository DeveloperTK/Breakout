package de.gymw.inf.breakout.game.brick;

import de.gymw.inf.breakout.Breakout;
import de.gymw.inf.breakout.game.BrickHitAction;
import de.gymw.inf.breakout.game.WorldObject;
import de.gymw.inf.breakout.views.GameView;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.Objects;

public class Brick extends WorldObject {

    protected Breakout p;
    private BrickHitAction hit;
    private boolean activeState;

    public static final BrickHitAction defaultAction = brick -> {
        brick.setActiveState(false);
        brick.parent.addScore(20);
        if(brick.getParent().isFire()) {
            brick.parent.b.playSound("breakblock");
        } else {
            brick.parent.b.playSound("doot");
        }
    };

    public static int brickCount = 0;

    public Brick(GameView parent, float x, float y, float w, float h, int colstr, int colfill) {
        super(parent, x, y, w, h, colstr, colfill);
        this.p = parent.b;
        activeState = true;
    }

    public void setHitAction(BrickHitAction hit) {
        this.hit = hit;
    }

    public void hitAction() {
        if(Objects.isNull(this.hit)) {
            Brick.defaultAction.run(this);
        } else {
            this.hit.run(this);
        }
    }

    @Override
    public void draw() {
        if(this.active()) {
            p.pushStyle();

            p.stroke(colstr);
            p.strokeWeight(4);
            p.fill(colfill);

            p.rectMode(PConstants.CENTER);
            p.rect(x, y, w, h);

            p.popStyle();
        }
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
        Brick.brickCount--;

        if(Brick.brickCount <= 0) {
            parent.gameOver();
        }
    }

    public boolean active() {
        return this.activeState;
    }

    public static Brick[][] generateRandomBricks(GameView parent, int lenx, int leny) {
        Brick.brickCount = 0;
        Brick[][] map = new Brick[lenx][leny];
        int colstr = parent.b.color(192, 168, 178);
        int colfill = parent.b.color(127, 0, 0, 1);
        for(int i = 0; i < lenx; i++) {
            for(int j = 0; j < leny; j++) {
                float rand = parent.b.random(0F, 100F);
                if(rand > 90) {
                    map[i][j] = new SpecialBrick(parent, i * parent.b.width / 10F + 80, j * parent.b.height / 10F + 60, 60, 40, parent.b.color(255, 194, 41), colfill, Math.round(parent.b.random(3, 6)));
                } else if(rand > 15) {
                    map[i][j] = new Brick(parent, i * parent.b.width / 10F + 80, j * parent.b.height / 10F + 60, 60, 40, colstr, colfill);
                    Brick.brickCount++;
                } else if(rand > 7) {
                    map[i][j] = new SolidBrick(parent, i * parent.b.width / 10F + 80, j * parent.b.height / 10F + 60, 60, 40, 0, 0);
                } else {
                    map[i][j] = new HardBrick(parent, i * parent.b.width / 10F + 80, j * parent.b.height / 10F + 60, 60, 40, 0, 0);
                    Brick.brickCount++;
                }
            }
        }

        return map;
    }

    public static Brick[][] loadBricksFromFile(GameView parent, String file) {
        try {
            JSONObject levelObject = parent.b.loadJSONObject(file);
            JSONArray rows = levelObject.getJSONArray("data");

            JSONArray size = levelObject.getJSONArray("size");
            Brick[][] map = new Brick[size.getInt(1)][size.getInt(0)];

            int colstr = parent.b.color(192, 168, 178);
            int colfill = parent.b.color(127, 0, 0, 1);
            Brick.brickCount = 0;

            for (int i = 0; i < rows.size(); i++) {
                JSONArray row = rows.getJSONArray(i);
                for(int j = 0; j < row.size(); j++) {
                    switch(row.getInt(j)) {
                        case 1:
                            map[j][i] = new SolidBrick(parent, j * parent.b.width / 10F + 80, i * parent.b.height / 10F + 60, 60, 40, 0, 0);
                            break;
                        case 2:
                            map[j][i] = new HardBrick(parent, j * parent.b.width / 10F + 80, i * parent.b.height / 10F + 60, 60, 40, 0, 0);
                            Brick.brickCount++;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            map[j][i] = new SpecialBrick(parent, j * parent.b.width / 10F + 80, i * parent.b.height / 10F + 60, 60, 40, parent.b.color(255, 194, 41), colfill, row.getInt(j));
                            Brick.brickCount++;
                            break;
                        default:
                            map[j][i] = new Brick(parent, j * parent.b.width / 10F + 80, i * parent.b.height / 10F + 60, 60, 40, colstr, colfill);
                            Brick.brickCount++;
                            break;
                    }
                }
            }

            return map;
        } catch (RuntimeException ex) {
            ex.printStackTrace(System.err);
            return new Brick[0][0];
        }
    }
}
