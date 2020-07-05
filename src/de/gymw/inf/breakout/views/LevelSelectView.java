package de.gymw.inf.breakout.views;

import de.gymw.inf.breakout.Breakout;
import org.foxat.pviewgui.PGView;
import org.foxat.pviewgui.element.PGButton;
import processing.core.PApplet;

public class LevelSelectView extends PGView {

    PGButton[] levelButtons;
    PGButton customLevel;
    PGButton back;

    public LevelSelectView(String label, PApplet p) {
        super(label, p);
        Breakout b = (Breakout) p;

        levelButtons = new PGButton[6];

        float startPos = b.width/2f - (100 * (levelButtons.length/2f - 0.5f));

        for(int i = 0; i < levelButtons.length; i++) {
            levelButtons[i] = new PGButton(startPos + 100*i, p.height/2f, 80, 48, "level" + i + "Button");
            levelButtons[i].setLabel(i + "");
            levelButtons[i].setStyle(b.style);
            int finalI = i;
            levelButtons[i].setMouseReleasedAction((x, y) -> {
                b.gameView.loadLevel("level" + finalI);
                b.getController().setActiveView("gameView");
            });
            registerElement(levelButtons[i]);
        }

        customLevel = new PGButton(b.width/2f, b.height/2f + 72, 380, 48, "customLevelButton");
        customLevel.setLabel("CUSTOM LEVEL");
        customLevel.setStyle(b.style);
        customLevel.setMouseReleasedAction((x, y) -> {
            b.gameView.loadLevel("custom");
            b.getController().setActiveView("gameView");
        });
        registerElement(customLevel);

        back = new PGButton(124, 72, 160, 48, "backButton");
        back.setLabel(b.getController().getLocaleString("backButton", "BACK"));
        back.setStyle(b.style);
        back.setMouseReleasedAction((x, y) -> b.getController().setActiveView("menuView"));
        registerElement(back);
    }

    @Override
    public void draw() {
        parent.background(24);
    }

}
