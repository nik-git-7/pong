package pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Gui extends JFrame {

    private final Set<Integer> pressedKeys = new HashSet<>();

    public Gui() {
        super("Pong");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(0, 0, 1200, 800);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
        );
        this.setResizable(false);
//        this.setUndecorated(true);

        JPanel root = new JPanel(null);
        GamePanel gamePanel = new GamePanel();

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setBounds(gamePanel.getX(), gamePanel.getY() - 50, gamePanel.getWidth(), 50);
//        infoPanel.setBackground(Color.WHITE);

        JLabel player1Points = new JLabel("0");
        player1Points.setForeground(new Color(0, 0, 200));
        player1Points.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel player2Points = new JLabel("0");
        player2Points.setForeground(new Color(0, 200, 0));
        player2Points.setFont(new Font("Arial", Font.BOLD, 30));

        gamePanel.setOnPointListener(playerId -> {
            player1Points.setText(String.valueOf(gamePanel.getPlayer1Points()));
            player2Points.setText(String.valueOf(gamePanel.getPlayer2Points()));
        });

        infoPanel.add(wrap(player1Points));
        infoPanel.add(wrap(player2Points));
        root.add(gamePanel);
        root.add(infoPanel);

        this.setContentPane(root);

        Thread performKey1 = new Thread(() -> {
            while (true) {
                for (int key : pressedKeys) {
                    switch (key) {
                        case KeyEvent.VK_E:
                            gamePanel.moveRacket1Up();
                            break;
                        case KeyEvent.VK_D:
                            gamePanel.moveRacket1Down();
                            break;
                        default:
                            break;
                    }
                }
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        performKey1.start();

        Thread performKey2 = new Thread(() -> {
            while (true) {
                for (int key : pressedKeys) {
                    switch (key) {
                        case KeyEvent.VK_UP:
                            gamePanel.moveRacket2Up();
                            break;
                        case KeyEvent.VK_DOWN:
                            gamePanel.moveRacket2Down();
                            break;
                        default:
                            break;
                    }
                }
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        performKey2.start();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
    }

    private static JPanel wrap(Component component) {
        JPanel wrapper = new JPanel(new FlowLayout());
        wrapper.add(component);
        return wrapper;
    }

}
