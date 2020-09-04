package pong;

import javax.swing.*;
import java.awt.*;

public class GameObject {
    private int x;
    private int y;
    private int width;
    private int height;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveByDelta(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void moveByAngle(int angle, int speed) {
        int x = (int) (Math.cos(Math.toRadians(angle)) * speed);
        int y = (int) (Math.sin(Math.toRadians(angle)) * speed);
        moveByDelta(x, y);
    }

    public boolean touches(GameObject object) {
        return object.getX() < this.getX() + this.getWidth() && this.getX() < object.getX() + object.getWidth()
                && object.getY() < this.getY() + this.getHeight() && this.getY() < object.getY() + object.getHeight();
    }

    public boolean insideOf(JPanel panel) {
        return panel.getX() < this.getX() && this.getX() + this.getWidth() < panel.getX() + panel.getWidth()
                && panel.getY() < this.getY() && this.getY() + this.getHeight() < panel.getY() + panel.getHeight();
    }

    /**
     * @param panel which might be crossed by this
     * @return contains
     * true if crossing, else false at position
     * 0 for bottom, 1 for right, 2 for top, 3 for left border
     */
    public boolean[] getCrossingBorder(JPanel panel) {
        boolean[] crossing = new boolean[4];
        GameObject border;

        border = new GameObject(0, panel.getHeight(), panel.getWidth(), 0);
        crossing[0] = this.touches(border);

        border = new GameObject(panel.getWidth(), 0, 0, panel.getHeight());
        crossing[1] = this.touches(border);

        border = new GameObject(0, 0, panel.getWidth(), 0);
        crossing[2] = this.touches(border);

        border = new GameObject(0, 0, 0, panel.getHeight());
        crossing[3] = this.touches(border);

        return crossing;
    }

    public void draw(Graphics g) {}
}
