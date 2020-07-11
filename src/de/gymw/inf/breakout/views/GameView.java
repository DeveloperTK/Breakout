package de.gymw.inf.breakout.views;

import de.gymw.inf.breakout.Breakout;
import de.gymw.inf.breakout.game.Ball;
import de.gymw.inf.breakout.game.brick.Brick;
import de.gymw.inf.breakout.game.Paddle;
import org.foxat.pviewgui.PGView;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class GameView extends PGView {

    public Breakout b;

    private boolean isActive;
    private boolean isPaused;
    private boolean isDemo;

    private float score;
    private int health;

    private final boolean DEBUG_MODE = true;

    public GameView(String label, PApplet p) {
        super(label, p);
        b = (Breakout) p;
    }

    // Pause and Resume

    private PVector speedBeforePaused;

    public void gamePaused() {
        speedBeforePaused = new PVector(ball.getXSpeed(), ball.getYSpeed());
        ball.setSpeed(new PVector(0, 0));
        isPaused = true;
        System.out.println("asd");
    }

    public void gameResumed() {
        ball.setSpeed(speedBeforePaused);
        isPaused = false;
    }

    // Game Objects

    Brick[][] bricks;
    Ball ball;
    Paddle paddle;

    public void loadAsDemo() {
        isDemo = true;
        load();
    }

    public void load() {
        isActive = false;
        isPaused = false;
        isDemo = false;
        score = 0;
        health = 3;

        b.colorMode(PConstants.RGB);
        paddle = new Paddle(this, b.width/2.0F, b.height-50, 80 , 10, b.color(255), b.color(255));
        ball = new Ball(this, b.width/2.0F, b.height-75, 30, b.color(255F), b.color(255F, 0F, 0F), PVector.fromAngle(PApplet.radians(-30F)), paddle);

        ball.setMaxHeight(b.width - 50);

        bricks = new Brick[1][1];
        bricks[0][0] = new Brick( this,100, 100, 50, 15, b.color(255, 0, 0), b.color(255));

        bricks = Brick.generateRandomBricks(this, 9, 5);

        ball.setBricks(bricks);
    }

    // draw and user imput

    @Override
    public void draw() {
        b.background(24);

        if(isActive && !isPaused) {
            // check arrow keys for moving the paddle
            if (parent.keyPressed) {
                if (parent.keyCode == PConstants.LEFT) paddle.move(-1);
                if (parent.keyCode == PConstants.RIGHT) paddle.move(1);
            }

            // update Score every second
            score+=(1/b.frameRate);

            // move the ball forward
            ball.updatePosition(20);
        }

        for (Brick[] brow : bricks) {
            for (Brick br : brow) {
                br.draw();
            }
        }

        // draw the score text in the bottom left corner
        b.noStroke();
        b.fill(255);
        b.textFont(b.getFont("minecraftia"));
        b.textSize(24);
        b.textAlign(PConstants.CORNER, PConstants.CORNER);
        b.text("Score: " + (int) score, 12, b.height);

        // DEBUG_MODE means that the paddle is always underneath the ball
        if(DEBUG_MODE) {
            paddle.x = ball.x;
        }

        // runs the demo until a score of 450 is reached
        if(isDemo && score < 450) {
            paddle.x = ball.x;
        }

        paddle.draw();
        ball.draw();
    }

    @Override
    public void keyPressed() {
        if(parent.key == ' ') {
            if(!isActive) {
                isActive = true;
            } else if(isPaused) {
                isPaused = false;
                gameResumed();
            } else {
                gamePaused();
            }
        }
    }

    // scoring system

    public void addScore(int score) {
        this.score += score;
    }

    public void die() {
        health--;
        if(health <= 0) {
            gameOver();
        }
    }

    public void gameOver() {
        isActive = false;
        System.out.println("Game Over!");
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                b.gameOverView.load((int) score);
                b.getController().setActiveView("gameOverView");
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    /**
     * A list of sounds:
     *      0: Ball hits Paddle or Wall
     *      1: Ball hits regular Brick
     *      2: Ball hits HardBrick, that needs to be hit again
     *      3: Ball hits SolidBrick
     *      4: Laser sound
     *      5: 1 life lost
     *      6: game over
     * */
    public void playSound(int sound) {
        switch (sound) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
