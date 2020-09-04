package pong;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.RandomAccess;

public class GamePanel extends JPanel {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 800;

    private OnPointListener listener;

    private final Ball ball;
    private final Racket racket1;
    private final Racket racket2;
    private final int centerX = WIDTH / 2;
    private final int centerY = HEIGHT / 2;
    private int player1Points = 0;
    private int player2Points = 0;

    public GamePanel() {
        super(null);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - WIDTH) / 2);
        int y = (int) ((dimension.getHeight() - HEIGHT) / 2);
        this.setBounds(x, y, WIDTH, HEIGHT);

        this.ball = new Ball(centerX, centerY);

        int racketY = (HEIGHT - Racket.HEIGHT) / 2;
        this.racket1 = new Racket(0, racketY);
        this.racket2 = new Racket(WIDTH - Racket.WIDTH, racketY);

        this.resetBall();
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    public void setOnPointListener(OnPointListener listener) {
        this.listener = listener;
    }

    public void moveRacket1Up() {
        this.racket1.move(-1);
        this.repaint();
    }

    public void moveRacket1Down() {
        this.racket1.move(1);
        this.repaint();
    }

    public void moveRacket2Up() {
        this.racket2.move(-1);
        this.repaint();
    }

    public void moveRacket2Down() {
        this.racket2.move(1);
        this.repaint();
    }


    @Override
    public void paint(Graphics g) {
        g.fillRect(0, 0, WIDTH, HEIGHT);
        this.ball.draw(g);
        this.racket1.draw(g);
        this.racket2.draw(g);
    }

    private void moveBall() {
        Thread thread = new Thread(() -> {
            ball.move();
            if (ball.touches(this.racket1) || ball.touches(this.racket2)) {
                ball.mirrorDirection(180, 90);
                ball.move(3);
            } else {
                boolean[] crossing = ball.getCrossingBorder(GamePanel.this);
                if (crossing[1]) {
                    player1Points++;
                    if (listener != null) {
                        listener.onPoint(1);
                    }
                    resetBall();
                    return;
                } else if (crossing[3]) {
                    player2Points++;
                    if (listener != null) {
                        listener.onPoint(2);
                    }
                    resetBall();
                    return;
                } else if (crossing[0] || crossing[2]) {
                    ball.mirrorDirection(0);
                    ball.move(3);
                }
            }

//            ball.changeSpeedBy(0.001);
//            racket1.changeSpeedBy(0.001);
//            racket2.changeSpeedBy(0.001);

            this.repaint();

            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveBall();
        });
        thread.start();
    }

    private void resetBall() {
        ball.moveTo(centerX, centerY);
        int angle = 90;
        while ((110 > angle && angle > 70) || (290 > angle && angle > 250)) {
            angle = new Random().nextInt(360);
        }
        ball.setAngle(angle);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
        moveBall();
    }
}
