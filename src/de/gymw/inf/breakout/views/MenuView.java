package de.gymw.inf.breakout.views;

import de.gymw.inf.breakout.Breakout;
import org.foxat.pviewgui.PGView;
import org.foxat.pviewgui.element.PGButton;
import processing.core.PApplet;
import processing.data.JSONArray;

public class MenuView extends PGView {

    Breakout b;

    PGButton startButton, helpButton, demoButton, quitButton;

    public MenuView(String label, PApplet p) {
        super(label, p);
        b = (Breakout) p;
        pulseSize = 0;

        startButton = new PGButton(b.width/2f, b.height*3f/5f - 72, b.width/2f, 48, "startButton");
        helpButton = new PGButton(b.width/2f, b.height*3f/5f, b.width/2f, 48, "helpButton");
        demoButton = new PGButton(b.width/2f - b.width/8f - 6, b.height*3f/5f + 72, b.width/4f - 12, 48, "demoButton");
        quitButton = new PGButton(b.width/2f + b.width/8f + 6, b.height*3f/5f + 72, b.width/4f - 12, 48, "quitButton");

        startButton.setLabel(b.getController().getLocaleString("startButton", "Start"));
        helpButton.setLabel(b.getController().getLocaleString("helpButton", "Help"));
        demoButton.setLabel(b.getController().getLocaleString("demoButton", "Demo"));
        quitButton.setLabel(b.getController().getLocaleString("quitButton", "Quit"));

        startButton.setMouseReleasedAction((x, y) -> b.getController().setActiveView("levelSelectView"));
        helpButton.setMouseReleasedAction((x, y) -> b.getController().setActiveView("helpView"));
        demoButton.setMouseReleasedAction((x, y) -> {
            b.getController().setActiveView("gameView");
            b.gameView.loadAsDemo();
        });
        quitButton.setMouseReleasedAction((x, y) -> b.exit());

        startButton.setStyle(b.style);
        helpButton.setStyle(b.style);
        demoButton.setStyle(b.style);
        quitButton.setStyle(b.style);

        registerElement(startButton);
        registerElement(helpButton);
        registerElement(demoButton);
        registerElement(quitButton);

        JSONArray messages = b.getController().getLocale().getJSONArray("motd");
        int index = PApplet.round(b.random(0f, (float) messages.size()));
        motd = messages.getString(index, "Ohne Aleatorik!");
    }

    private String motd;

    @Override
    public void setup() {
        /*JSONArray messages = b.getController().getLocale().getJSONArray("motd");
        int index = PApplet.round(b.random(0, messages.size()));
        motd = messages.getString(index, "Ohne Aleatorik!");*/
    }

    private float pulseSize;

    @Override
    public void draw() {
        b.background(24);

        pulseSize = 4.5f * PApplet.sin(b.frameCount/(30f)) + 20f;

        b.stroke(0);
        b.strokeWeight(2);
        b.textFont(b.getFont("pricedown"), 96);
        b.fill(31, 51, 237);
        b.textAlign(b.CENTER);
        b.text("Breakout", b.width/2f, b.height/4f);

        b.pushMatrix();

        b.textFont(b.getFont("minecraftia"), pulseSize);

        b.translate(b.width*3.9f/5f, b.height*3f/10f);
        b.rotate(-b.PI/8f);

        b.fill(51);
        b.text(motd, 0, 0);
        b.translate(-4, -4);

        b.noStroke();
        b.fill(210, 210, 0);
        b.text(motd, 0, 0);

        b.popMatrix();
    }

}
