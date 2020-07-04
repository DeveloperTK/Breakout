package de.gymw.inf.breakout.views;

import org.foxat.pviewgui.PGView;
import processing.core.PApplet;

public class GameOverView extends PGView {

    public GameOverView(String label, PApplet p) {
        super(label, p);
    }

    public void load(int score) {
        System.out.println(score);
    }

    @Override
    public void draw() {
        parent.background(0);
        parent.rect(100, 100, 10, 10);
    }
}
