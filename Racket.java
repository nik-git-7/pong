package pong;

import java.awt.*;

public class Racket extends GameObject {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 60;
    private final static int START_SPEED = 5;


    private double speed;

    public Racket(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        this.speed = START_SPEED;
    }

    public void changeSpeedBy(double dSpeed) {
        this.speed += dSpeed;
    }

    public void move(int direction) {
        this.moveByDelta(0, (int) (this.speed * direction));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.getX(), this.getY(), WIDTH, HEIGHT);
    }
}
