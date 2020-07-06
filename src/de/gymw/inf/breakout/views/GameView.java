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
    public String level;

    private boolean isActive;
    private boolean isPaused;
    private boolean isDemo;

    private float score;
    private int health;
    private int tpf;

    private final boolean DEBUG_MODE = false;
    private float multiplier;

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
        b.playSound("pause");
    }

    public void gameResumed() {
        ball.setSpeed(speedBeforePaused);
        isPaused = false;
        b.playSound("pause");
    }

    // Game Objects

    Brick[][] bricks;
    Ball ball;
    public Paddle paddle;

    public void loadAsDemo() {
        loadRandom();
        level = "DEMO";
        isDemo = true;
        isPaused = false;
        isActive = true;
        health = 1;
        tpf *= 2;
    }

    public void loadLevel(String name) {
        load();
        bricks = Brick.loadBricksFromFile(this, "levels/" + name + ".json");
        ball.setBricks(bricks);
        level = name;
    }

    public void loadRandom() {
        load();
        bricks = Brick.generateRandomBricks(this, 9, 5);
        ball.setBricks(bricks);
    }

    public void load() {
        isActive = false;
        isPaused = false;
        isDemo = false;
        score = 0;
        health = 3;
        tpf = 6;
        level = null;
        multiplier = 1.0f;

        b.colorMode(PConstants.RGB);
        paddle = new Paddle(this, b.width / 2.0F, b.height - 50, 80, 10, b.color(255), b.color(255));
        ball = new Ball(this, b.width / 2.0F, b.height - 75, 30, b.color(255F), b.color(255F, 0F, 0F), PVector.fromAngle(PApplet.radians(-30F)), paddle);

        ball.setMaxHeight(b.height - 50);

        bricks = new Brick[1][1];
        bricks[0][0] = new Brick(this, 100, 100, 50, 15, b.color(255, 0, 0), b.color(255));

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
            ball.updatePosition(tpf);
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

        b.shapeMode(PConstants.CENTER);

        for(int i = 0; i < health; i++) {
            b.shape(b.getHardcoreHeart(), b.width - 24 - 32 * i, b.height - 24, 32, 32);
        }

        if(multiplier != 1) {
            b.fill(255);
            b.text("x" + PApplet.parseFloat(Math.round(multiplier*10)) / 10f, b.width/2f - 12, b.height);
        }
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
        } else if(parent.key == '0') {
            Brick.brickCount = 0;
        }
    }

    // scoring system

    public void addScore(int score) {
        this.score += score * multiplier;
    }

    public void die() {
        health--;

        // stops the ball from moving
        if(!isDemo) {
            this.isActive = false;
            this.isPaused = false;
            this.ball.fireMode = false;
        }

        // resets the direction the ball is moving in
        ball.setSpeed(PVector.fromAngle(PApplet.radians(-30)));

        b.playSound("mariodie");

        if(health <= 0) {
            gameOver();
        } else {
            ball.resetPosition();
            paddle.resetPosition();
        }
    }

    public void gameOver() {
        isActive = false;
        System.out.println("Game Over!");
        new Thread(() -> {
            try {
                if(health <= 0) {
                    Thread.sleep(3100);
                    b.playSound("gameover");
                } else if(isDemo) {
                    Thread.sleep(3100);
                    b.playSound("gameover");
                    b.getController().setActiveView("menuView");
                    return;
                } else {
                    Thread.sleep(500);
                    b.playSound("stage_clear");
                }
                b.gameOverView.load((int) score, health > 0);
                b.getController().setActiveView("gameOverView");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public void fire() {
        ball.fireMode = true;
    }

    public boolean isFire() {
        return ball.fireMode;
    }
    
    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public void addLife() {
        this.health += 1;
    }
}
