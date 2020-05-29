package de.gymw.inf.breakout.views;

import de.gymw.inf.breakout.Breakout;
import org.foxat.pviewgui.PGView;
import processing.core.PApplet;

public class GameView extends PGView {

    Breakout b;

    public GameView(String label, PApplet p) {
        super(label, p);
        b = (Breakout) p;
    }

    @Override
    public void draw() {
        b.background(24);
        b.fill(255);
        b.translate(b.width/2f, b.height/2f);
        b.rotate((b.frameCount/60f)*b.TWO_PI);
        b.rect(0, 0, 100, 200);
    }
}
