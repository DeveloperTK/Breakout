package de.gymw.inf.breakout.views;

import de.gymw.inf.breakout.Breakout;
import org.foxat.pviewgui.PGView;
import org.foxat.pviewgui.element.PGButton;
import processing.core.PApplet;
import processing.core.PConstants;

public class HelpView extends PGView {

    Breakout b;

    PGButton back;

    public HelpView(String label, PApplet p) {
        super(label, p);
        b = (Breakout) p;

        back = new PGButton(b.width/2.0F, b.height*3.0F/4.0F, b.width/4.0F, 48.0F, "backButton");
        back.setStyle(b.style);
        back.setLabel(b.getController().getLocaleString(back.objectName, "Back"));
        back.setMouseReleasedAction((x,y) -> b.getController().setActiveView("menuView"));
        registerElement(back);
    }

    @Override
    public void draw() {
        b.background(24);
        b.textFont(b.getFont("minecraftia"), 24);
        b.textMode(PConstants.CENTER);
        b.text("A man has fallen for a man\nin Lego City.", b.width/2f, b.height/2f);
    }
}
