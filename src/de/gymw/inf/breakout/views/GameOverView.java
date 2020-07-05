package de.gymw.inf.breakout.views;

import de.gymw.inf.breakout.Breakout;
import org.foxat.pviewgui.PGView;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.data.JSONArray;
import processing.data.JSONObject;

import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

public class GameOverView extends PGView {

    public GameOverView(String label, PApplet p) {
        super(label, p);
    }

    private Breakout b;
    private boolean success;
    private int startAt;

    String highscoreText = "";
    int[] scores;

    public void load(int score, boolean success) {
        System.out.println("Score: " + score);
        b = (Breakout) parent;
        startAt = b.frameCount;
        this.success = success;
        String level = b.gameView.level;
        if (level == "%DEMO%") {
            b.getController().setActiveView("menuView");
            return;
        }

        try {
            JSONArray hscore = b.loadJSONObject("levels/" + level + ".json").getJSONArray("highscore");
            if(hscore == null) hscore = JSONArray.parse("[0, 0, 0]");

            hscore.append(score);

            System.out.println(hscore);

            scores = new int[hscore.size()];
            for(int i = 0; i < hscore.size(); i++) {
                scores[i] = hscore.getInt(i);
            }
            Arrays.sort(scores);

            System.out.println(Arrays.toString(scores));

            highscoreText = "LEADERBOARD:";
            for(int i = scores.length-1; i >= scores.length-3 && i >= 0; i--) {
                highscoreText += "\n" + (scores.length-i) + ".   " + scores[i];
            }

            JSONObject levelFile = b.loadJSONObject("levels/" + level + ".json");
            levelFile.setJSONArray("highscore", hscore);
            b.saveJSONObject(levelFile, "levels/" + level + ".json");

        } catch (RuntimeException ex) {
            //ex.printStackTrace();
        }
    }

    @Override
    public void draw() {
        if(b.frameCount - startAt > 140) {
            b.background(24);
        }

        if(success) {
            drawText("LEVEL COMPLETE");
        } else {
            drawText("GAME OVER");
        }

        if (b.frameCount - startAt > 280) {
            float greyscale;

            if((b.frameCount % 90) < 45) {
                greyscale = PApplet.map(b.frameCount % 45, 0, 44, 24, 255);
            } else {
                greyscale = PApplet.map(b.frameCount % 45, 0, 44, 255, 24);
            }

            b.fill(greyscale);
            b.textSize(16);
            b.text("Press Space to continue!", b.width/2f, b.height - 80);
        }

        if(b.frameCount - startAt > 300) {
            b.fill(b.color(255, 255, 0));
            b.textSize(24);

            b.text(highscoreText, b.width/2, b.height/2);
        }
    }

    private void drawText(String text) {
        float ypos;

        if(b.frameCount - startAt < 260 && b.frameCount - startAt > 160) {
            ypos = PApplet.map(b.frameCount-startAt-160, 0, 100, b.height/2f, 120);
        } else if(b.frameCount - startAt <= 160) {
            ypos = b.height/2f;
        } else {
            ypos = 120;
        }


        if(b.frameCount - startAt < 100) {
            b.textFont(b.getFont("minecraftia"));
            b.fill(b.random(0, 200), b.random(0, 200), b.random(0, 200));
            b.noStroke();

            float randX = b.random(0, b.width);
            float randY = b.random(0, b.height);

            b.textSize(24);
            b.textAlign(PConstants.CENTER, PConstants.CENTER);
            b.text(text, randX, randY);
        } else if (b.frameCount - startAt > 100){
            b.noStroke();
            b.textFont(b.getFont("minecraftia"));

            b.textAlign(PConstants.CENTER, PConstants.CENTER);
            b.textSize(48);

            b.fill(71);
            b.text(text, b.width/2F + 4, ypos + 4);

            b.fill(255);
            b.text(text, b.width/2F, ypos);
        }
    }

    @Override
    public void keyPressed() {
        if(b.key == ' ') {
            b.getController().setActiveView("menuView");
        }
    }
}
