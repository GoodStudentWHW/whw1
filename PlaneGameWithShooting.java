package com.planegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PlaneGameWithShooting extends JPanel implements ActionListener {

    private Timer timer;
    private BufferedImage plane;
    private int planeX = 200;
    private int planeY = 200;
    private int planeSpeed = 5;
    private List<Bullet> bullets;

    public PlaneGameWithShooting() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.CYAN);
        plane = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = plane.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 50, 50);
        g2d.dispose();

        bullets = new ArrayList<>();
        timer = new Timer(25, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    planeX -= planeSpeed;
                } else if (key == KeyEvent.VK_RIGHT) {
                    planeX += planeSpeed;
                } else if (key == KeyEvent.VK_UP) {
                    planeY -= planeSpeed;
                } else if (key == KeyEvent.VK_DOWN) {
                    planeY += planeSpeed;
                } else if (key == KeyEvent.VK_SPACE) {
                    shoot();
                }
            }
        });
        setFocusable(true);
    }

    private void shoot() {
        bullets.add(new Bullet(planeX + 25, planeY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(plane, planeX, planeY, this);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        bullets.removeIf(bullet -> bullet.getY() < 0);
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Plane Game with Shooting");
        PlaneGameWithShooting game = new PlaneGameWithShooting();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class Bullet {
        private int x;
        private int y;
        private int speed = 10;

        public Bullet(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move() {
            y -= speed;
        }

        public void draw(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 5, 10);
        }

        public int getY() {
            return y;
        }
    }
}
