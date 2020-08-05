import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class BreakOut extends GraphicsProgram {
	private RandomGenerator rgen = new RandomGenerator();

	private GOval ball;
	private GRect paddle;

	private double vx = rgen.nextDouble(8.0, 11.0);
	private double vy = rgen.nextDouble(8.0, 11.0);

	final int Board_Length = 640;
	final int Board_Width = 750;

	final int Paddle_Width = 10;
	final int Paddle_Length = 100;

	final int Ball_Radius = 20;

	final int Brick_Length = 40;
	final int Brick_Width = 20;

	private int Brick_X_Offset = 40;
	private int Brick_Y_Offset = 20;

	private int Bricks_Per_Row = 14;
	private int Nummber_Of_Brick_Rows = 5;

	public void run() {
		// your code goes here...
		setUp();
		// Game Loop
		while (true) {
			moveBall();
			checkCollision();
			checkForCollisionWithPaddleOrBricks();
			if (ball.getY() > 750) {
				break;
			}
			pause(40);
		}
		showGameOverScreen();

	}

	private void setUp() {
		setSize(Board_Length, Board_Width);
		addMouseListeners();
		drawWall();
		drawPaddle();
		drawBall();

	}

	private void showGameOverScreen() {
		GLabel over = new GLabel("Game Over");
		add(over, 100, 350);
		over.setFont("Arial-72-bold");
		over.setColor(Color.black);

	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		paddle.setLocation(x - (Paddle_Length / 2), paddle.getY());
	}

	private void checkForCollisionWithPaddleOrBricks() {
		GObject obj = getElementAt(ball.getX(), ball.getY());
		if (obj != null) {
			if (obj == paddle) {
				vy = -vy;

			} else {
				remove(obj);
				vy = -vy;
			}
		}

	}

	private void checkCollision() {
		if (ball.getX() > 620 || ball.getX() < 0) {
			vx = -vx;
		}
		if (ball.getY() < 0) {
			vy = -vy;
		}

	}

	private void moveBall() {
		ball.move(vx, vy);

	}

	private void drawPaddle() {
		paddle = new GRect(Paddle_Length, Paddle_Width);
		add(paddle, 250, 700);
		paddle.setFilled(true);
		paddle.setFillColor(Color.black);

	}

	public void drawBall() {
		ball = new GOval(Ball_Radius, Ball_Radius);
		add(ball, 285, 500);
		ball.setFilled(true);
		ball.setFillColor(Color.black);

	}

	public void drawWall() {
		for (int i = 0; i < Nummber_Of_Brick_Rows; i++) {
			drawOneRow(Brick_Y_Offset);
			Brick_Y_Offset += 20;
		}

	}

	public void drawOneRow(int Brick_Y_Offset) {
		Brick_X_Offset = 40;
		for (int i = 0; i < Bricks_Per_Row; i++) {
			GRect brick = new GRect(Brick_Length, Brick_Width);
			add(brick, Brick_X_Offset, Brick_Y_Offset);
			brick.setFilled(true);
			brick.setFillColor(rgen.nextColor());
			Brick_X_Offset += 40;

		}

	}

}