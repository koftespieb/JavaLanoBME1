import java.awt.Color;
import java.awt.event.KeyEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;


public class Pong extends GraphicsProgram {
	private final int DELAY = 15;
	private final int WIDTH = 600;
	private final int HEIGHT = 400;
	private final int BALL_SIZE = 20;
	private final int PADDLE_SEPARATION = 10;
	private final int PADDLE_VY = 25;
	private final int PADDLE_HEIGHT = 60;
	private final int PADDLE_WIDTH = 10;
	private final int SCORE_Y_OFFSET = 30;
	private final int SCOREP1_X_OFFSET = 30;
	private final int SCOREP2_X_OFFSET = 560;
	private final int START_LABEL_X_OFFSET = 160;
	private final int START_LABEL_Y_OFFSET = 50;
	private final int CONTINUE_LABEL_X_OFFSET = 125;
	private final int MIDDLE_BRICKS_Y_OFFSET = 4;
	private final double MIDDLE_BRICKS_X_OFFSET = (WIDTH / 2) - 1.5;
	private final int MIDDLE_BRICK_WIDTH = 3;
	private final int MIDDLE_BRICK_HEIGHT = 10;

	private GOval ball;
	private GRect middleBricks;
	private GRect leftPaddle;
	private GRect rightPaddle;
	private GLabel scoreP1;
	private GLabel scoreP2;
	private GLabel startLabel = new GLabel("Click to Start.");
	private GLabel continueLabel = new GLabel("Click to Continue...");

	private int punkteP1;
	private int punkteP2;
	private RandomGenerator rgen = new RandomGenerator();
	private double vx = rgen.nextDouble(2.5, 3.0);
	private double vy = rgen.nextDouble(2.5, 3.0);

	public void run() {
		// your code goes here...
		setup();
		waitForClick();
		remove(startLabel);

		// GameLoop
		while (true) {
			moveBall();
			checkForWallCollision();
			checkForPaddleCollision(leftPaddle);
			checkForPaddleCollision(rightPaddle);
			pause(DELAY);
		}

	}

	private void setup() {
		setSize(WIDTH, HEIGHT);
		setBackground(Color.DARK_GRAY);

		drawStartLabel();
		drawMiddleLine();
		drawScoreP1();
		drawScoreP2();
		drawLeftPaddle();
		drawRightPaddle();
		drawBall();

		addKeyListeners();
		addMouseListeners();

	}

	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		switch (c) {
		case 'p':
			rightPaddle.move(0, -PADDLE_VY);
			break;
		case 'l':
			rightPaddle.move(0, PADDLE_VY);
			break;
		case 'q':
			leftPaddle.move(0, -PADDLE_VY);
			break;
		case 'a':
			leftPaddle.move(0, PADDLE_VY);
			break;
		}

	}

	private void moveBall() {
		ball.move(vx, vy);
	}

	private void checkForPaddleCollision(GRect paddle) {
		double x = ball.getX();
		double y = ball.getY();

		GObject obj = getElementAt(x, y);
		if (obj != null && obj == paddle) {
			vx = -vx;
			return;
		}
		obj = getElementAt((x + BALL_SIZE), y);
		if (obj != null && obj == paddle) {
			vx = -vx;
			return;
		}
		obj = getElementAt(x, (y + BALL_SIZE));
		if (obj != null && obj == paddle) {
			vx = -vx;
			return;
		}
		obj = getElementAt((x + BALL_SIZE), (y + BALL_SIZE));
		if (obj != null && obj == paddle) {
			vx = -vx;
			return;
		}

	}

	private void checkForWallCollision() {
		if (ball.getY() < 0 || ball.getY() > (HEIGHT - (2 * BALL_SIZE))) {
			vy = -vy;
		}
		if (ball.getX() > WIDTH) {
			ball.setLocation((WIDTH / 2) - (BALL_SIZE / 2), (HEIGHT / 2) - (BALL_SIZE / 2));
			punkteP1 += 1;
			remove(scoreP1);
			drawScoreP1();
			drawContinueLabel();
			waitForClick();
			remove(continueLabel);
			return;
		}
		if (ball.getX() < 0 - BALL_SIZE) {
			ball.setLocation((WIDTH / 2) - (BALL_SIZE / 2), (HEIGHT / 2) - (BALL_SIZE / 2));
			punkteP2 += 1;
			remove(scoreP2);
			drawScoreP2();
			drawContinueLabel();
			waitForClick();
			remove(continueLabel);
			return;
		}

	}

	private String addPoint(int p) {
		String punkte = "";
		punkte += p;
		return punkte;
	}

	private void drawScoreP1() {
		scoreP1 = new GLabel(addPoint(punkteP1));
		scoreP1.setColor(Color.WHITE);
		scoreP1.setFont("Manaspace-20");
		add(scoreP1, SCOREP1_X_OFFSET, SCORE_Y_OFFSET);
	}

	private void drawScoreP2() {
		scoreP2 = new GLabel(addPoint(punkteP2));
		scoreP2.setColor(Color.WHITE);
		scoreP2.setFont("Manaspace-20");
		add(scoreP2, SCOREP2_X_OFFSET, SCORE_Y_OFFSET);
	}

	private void drawBall() {
		ball = new GOval(BALL_SIZE, BALL_SIZE);
		add(ball, (WIDTH / 2) - (BALL_SIZE / 2), (HEIGHT / 2) - (BALL_SIZE / 2));
		ball.setFilled(true);
		ball.setColor(Color.white);
	}

	private void drawRightPaddle() {
		rightPaddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(rightPaddle, ((WIDTH - PADDLE_WIDTH) - PADDLE_SEPARATION), ((HEIGHT / 2) - (PADDLE_HEIGHT / 2)));
		rightPaddle.setFilled(true);
		rightPaddle.setColor(Color.white);
	}

	private void drawLeftPaddle() {
		leftPaddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(leftPaddle, PADDLE_SEPARATION, ((HEIGHT / 2) - (PADDLE_HEIGHT / 2)));
		leftPaddle.setFilled(true);
		leftPaddle.setColor(Color.white);
	}

	private void drawStartLabel() {
		startLabel.setColor(Color.MAGENTA);
		startLabel.setFont("Manaspace-30");
		add(startLabel, START_LABEL_X_OFFSET, START_LABEL_Y_OFFSET);
	}

	private void drawContinueLabel() {
		continueLabel.setColor(Color.MAGENTA);
		continueLabel.setFont("Manaspace-30");
		add(continueLabel, CONTINUE_LABEL_X_OFFSET, START_LABEL_Y_OFFSET);
	}

	private void drawMiddleLine() {
		int y = MIDDLE_BRICKS_Y_OFFSET;
		for (int i = 0; i < 20; i++) {
			middleBricks = new GRect(MIDDLE_BRICK_WIDTH, MIDDLE_BRICK_HEIGHT);
			middleBricks.setFilled(true);
			middleBricks.setColor(Color.LIGHT_GRAY);
			middleBricks.sendToBack();
			add(middleBricks, MIDDLE_BRICKS_X_OFFSET, y);
			y += 20;
		}
	}

}