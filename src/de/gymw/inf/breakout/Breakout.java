package de.gymw.inf.breakout;

import de.gymw.inf.breakout.views.GameOverView;
import de.gymw.inf.breakout.views.GameView;
import de.gymw.inf.breakout.views.HelpView;
import de.gymw.inf.breakout.views.MenuView;
import org.foxat.pviewgui.ProcessingGUI;
import org.foxat.pviewgui.action.DrawAction;
import org.foxat.pviewgui.interfaces.Parent;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.HashMap;

/**
 * @author Christian Schliz
 * @version 1.0
 * */
public class Breakout extends PApplet implements Parent {

    public ProcessingGUI gui;

    private boolean loading = true;

    private MenuView menuView;
    private HelpView helpView;
    public GameView gameView;
    public GameOverView gameOverView;

    public boolean isLevelMode;

    public DrawAction style;

    HashMap<String, PFont> fonts;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        background(0);
        textAlign(3);
        textSize(69);
        text("loading...", width/2f, height/2f);

        frameRate(60);

        gui = new ProcessingGUI(this);

        gui.setLocalization("de.json");

        PFont pricedown = loadFont("pricedown-48.vlw");
        PFont minecraftia = loadFont("minecraftia-48.vlw");
        PFont pressstart = loadFont("pressstart-16.vlw");
        fonts = new HashMap<>();
        fonts.put("pricedown", pricedown);
        fonts.put("minecraftia", minecraftia);
        fonts.put("pressstart", pressstart);

        viewSetup();

        isLevelMode = false;
        loading = false;
    }

    private void viewSetup() {

        style = (btn, b) -> {
            b.pushStyle();
            b.rectMode(3);
            if (b.mousePressed && btn.touchUpInside((float) b.mouseX, (float) b.mouseY)) {
                b.fill(12, 25, 127);
            } else {
                b.fill(220);
            }

            b.strokeWeight(4.0F);
            b.stroke(127);
            b.rect(btn.x, btn.y, btn.w, btn.h);
            b.textAlign(3, 3);
            b.textSize(24.0F);

            b.noStroke();
            b.fill(127);
            b.text(btn.getLabel(), btn.x + 10.0F, btn.y + 10.0F);

            if (b.mousePressed && btn.touchUpInside((float) b.mouseX, (float) b.mouseY)) {
                b.fill(255);
            } else {
                b.fill(0);
            }

            b.text(btn.getLabel(), btn.x + 8.0F, btn.y + 8.0F);
            b.popStyle();
        };

        menuView = new MenuView("menuView", this);
        gui.addView(menuView);

        helpView = new HelpView("helpView", this);
        gui.addView(helpView);

        gameView = new GameView("gameView", this);
        gui.addView(gameView);

        gameOverView = new GameOverView("gameOverView", this);
        gui.addView(gameOverView);

        gui.setActiveView("menuView");
    }

    public PFont getFont(String byName) {
        return fonts.getOrDefault(byName, createFont("Helvetica", 48));
    }

    public void draw() {

    }

    public static void main(String[] args) {
        PApplet.main(Breakout.class.getName());
    }

    @Override
    public ProcessingGUI getController() {
        return gui;
    }
}