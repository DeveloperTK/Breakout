package de.gymw.inf.breakout;

import de.gymw.inf.breakout.views.*;
import org.foxat.pviewgui.ProcessingGUI;
import org.foxat.pviewgui.action.DrawAction;
import org.foxat.pviewgui.interfaces.Parent;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PShape;
import processing.data.JSONObject;
import processing.sound.SoundFile;

import java.util.HashMap;

/**
 * @author Christian Schliz
 * @version 1.0
 * */
public class Breakout extends PApplet implements Parent {

    public ProcessingGUI gui;

    public MenuView menuView;
    public HelpView helpView;
    public GameView gameView;
    public GameOverView gameOverView;
    public LevelSelectView levelSelectView;

    public boolean isLevelMode;

    public DrawAction style;

    private PShape hardcoreHeart;
    private PShape emptyHeart;

    HashMap<String, PFont> fonts;
    HashMap<String, SoundFile> sounds;

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

        gui.setLocalization("locales/de.json");

        PFont pricedown = loadFont("fonts/pricedown-48.vlw");
        PFont minecraftia = loadFont("fonts/minecraftia-48.vlw");
        PFont pressstart = loadFont("fonts/pressstart-16.vlw");
        fonts = new HashMap<>();
        fonts.put("pricedown", pricedown);
        fonts.put("minecraftia", minecraftia);
        fonts.put("pressstart", pressstart);

        SoundFile bump = new SoundFile(this, "sounds/smb_bump.wav");
        SoundFile fireball = new SoundFile(this, "sounds/smb_fireball.wav");
        SoundFile gameover = new SoundFile(this, "sounds/smb_gameover.wav");
        SoundFile mariodie = new SoundFile(this, "sounds/smb_mariodie.wav");
        SoundFile pause = new SoundFile(this, "sounds/smb_pause.wav");
        SoundFile stage_clear = new SoundFile(this, "sounds/smb_stage_clear.wav");
        SoundFile doot = new SoundFile(this, "sounds/menu_navigate_03.wav");
        doot.amp(0.5F);
        sounds = new HashMap<>();
        sounds.put("bump", bump);
        sounds.put("fireball", fireball);
        sounds.put("gameover", gameover);
        sounds.put("mariodie", mariodie);
        sounds.put("pause", pause);
        sounds.put("stage_clear", stage_clear);
        sounds.put("doot", doot);

        viewSetup();

        hardcoreHeart = loadShape("sprites/Hardcore_Heart.svg");
        emptyHeart = loadShape("sprites/Empty_Heart.svg");

        isLevelMode = false;
        saveAllPaths();
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
            b.text(btn.getLabel(), btn.x + 2F, btn.y + 10.0F);

            if (b.mousePressed && btn.touchUpInside((float) b.mouseX, (float) b.mouseY)) {
                b.fill(255);
            } else {
                b.fill(0);
            }

            b.text(btn.getLabel(), btn.x, btn.y + 8.0F);
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

        levelSelectView = new LevelSelectView("levelSelectView", this);
        gui.addView(levelSelectView);

        gui.setActiveView("menuView");
    }

    public PFont getFont(String byName) {
        return fonts.getOrDefault(byName, createFont("Helvetica", 48));
    }

    public SoundFile getSound(String byName) {
        return sounds.getOrDefault(byName, null);
    }

    public void saveAllPaths() {
        for(int i = 0; i <= 5; i++) {
            JSONObject x = loadJSONObject("levels/level" + i + ".json");
            saveJSONObject(x, "levels/level" + i + ".json");
        }
    }

    public void playSound(String name) {
        try {
            SoundFile x = getSound(name);
            x.stop();
            x.play();
        } catch(RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("Sound with name " + name + " could not be found!");
        }
    }

    public PShape getHardcoreHeart() {
        return hardcoreHeart;
    }

    public PShape getEmptyHeart() {
        return emptyHeart;
    }

    public void draw() {

    }

    public static void main(String[] args) {
        PApplet.main("de.gymw.inf.breakout.Breakout");
    }

    @Override
    public ProcessingGUI getController() {
        return gui;
    }
}