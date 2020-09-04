package pong;

import java.awt.*;
import java.util.Random;

public class Ball extends GameObject {
    private static final int STD_RADIUS = 20;
    private static final Color STD_COLOR = Color.RED;
    private final static int BALL_SPEED = 5;

    private final int radius;
    private int angle;
    private double speed;

    public Ball(int x, int y) {
        super(x, y, STD_RADIUS, STD_RADIUS);
        this.radius = STD_RADIUS;
        this.speed = BALL_SPEED;
    }

    public void mirrorDirection(int axisAngle, int variance) {
        int v = new Random().nextInt(variance);
        v = v - v / 2;
        this.angle = 360 + axisAngle + v - this.angle;
    }

    public void mirrorDirection(int axisAngle) {
        this.angle = 360 + axisAngle - this.angle;
    }

    public void changeSpeedBy(double dSpeed) {
        this.speed += dSpeed;
    }

    public void move() {
        moveByAngle(this.angle, (int) this.speed);
    }

    public void move(int n) {
        for (int i = 0; i < n; i++) {
            move();
        }
    }

    public void draw(Graphics g) {
        g.setColor(STD_COLOR);
        g.fillOval(this.getX(), this.getY(), this.radius, this.radius);
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
