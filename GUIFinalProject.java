/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.guifinaltesting;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class GUIFinalTesting {
    private static int jumps = 0;
    private static int score = 0;
    private static int highScore = 0;
    private static int hearts = 5;
    private static int speed = 5;
    private static JLabel jumpLabel, scoreLabel, highScoreLabel, heartsLabel;
    private static JLabel clickCounter;
    private static int clicks = 0;
    private static Random random = new Random();
    private static GamePanel gamePanel;
    private static JTextField easyField, hardField;
    private static JLabel eqEasy, eqHard;
    private static int a, b, c, d;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIFinalTesting::createForm);
        loadHighScore();
    }

    private static void createForm() {
        JFrame frame = new JFrame("Math Jump Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.setLayout(null);

        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, 800, 400);
        frame.add(panel);

        JLabel title = new JLabel("Solve Math to Play the Game!");
        title.setBounds(250, 20, 400, 30);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(title);

        JButton soundBtn = new JButton("Play Music");
        soundBtn.setBounds(600, 20, 150, 30);
        panel.add(soundBtn);
        soundBtn.addActionListener(e -> playSound("sounds/BackgroundMusic.wav"));

        a = random.nextInt(50); b = random.nextInt(50);
        eqEasy = new JLabel(a + " + " + b + " = ");
        eqEasy.setBounds(250, 80, 150, 30);
        eqEasy.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(eqEasy);

        easyField = new JTextField();
        easyField.setBounds(400, 80, 100, 30);
        panel.add(easyField);

        JButton checkEasy = new JButton("Check");
        checkEasy.setBounds(510, 80, 80, 30);
        panel.add(checkEasy);
        checkEasy.addActionListener(e -> {
            clicks++;
            updateClickCounter();
            if (easyField.getText().equals(String.valueOf(a + b))) {
                playSound("sounds/Correct.wav");
                jumps++;
                updateJumpDisplay();
                a = random.nextInt(50); b = random.nextInt(50);
                eqEasy.setText(a + " + " + b + " = ");
                easyField.setText("");
            } else {
                playSound("sounds/Incorrect.wav");
            }
        });

        c = random.nextInt(20) + 1; d = random.nextInt(20) + 1;
        eqHard = new JLabel(c + " x " + d + " = ");
        eqHard.setBounds(250, 130, 150, 30);
        eqHard.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(eqHard);

        hardField = new JTextField();
        hardField.setBounds(400, 130, 100, 30);
        panel.add(hardField);

        JButton checkHard = new JButton("Check");
        checkHard.setBounds(510, 130, 80, 30);
        panel.add(checkHard);
        checkHard.addActionListener(e -> {
            clicks++;
            updateClickCounter();
            if (hardField.getText().equals(String.valueOf(c * d))) {
                playSound("sounds/Correct.wav");
                jumps += 2;
                updateJumpDisplay();
                c = random.nextInt(20) + 1; d = random.nextInt(20) + 1;
                eqHard.setText(c + " x " + d + " = ");
                hardField.setText("");
            } else {
                playSound("sounds/Incorrect.wav");
            }
        });

        jumpLabel = new JLabel("Jumps: 0");
        jumpLabel.setBounds(50, 200, 100, 30);
        panel.add(jumpLabel);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        scoreLabel.setBounds(600, 320, 150, 30);
        panel.add(scoreLabel);

        highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setForeground(Color.LIGHT_GRAY);
        highScoreLabel.setBounds(600, 290, 200, 30);
        panel.add(highScoreLabel);

        clickCounter = new JLabel("Clicks: 0", SwingConstants.CENTER);
        clickCounter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        clickCounter.setBounds(620, 350, 100, 30);
        panel.add(clickCounter);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(0, 390, 800, 10);
        frame.add(separator);

        gamePanel = new GamePanel();
        gamePanel.setBounds(0, 400, 800, 500);
        frame.add(gamePanel);

        JPanel overlayPanel = new JPanel(null);
        overlayPanel.setOpaque(true);
        overlayPanel.setBackground(new Color(200, 200, 200, 150));
        overlayPanel.setBounds(0, 400, 150, 900);
        frame.add(overlayPanel);

        JButton upBtn = new JButton("↑");
        upBtn.setBounds(30, 450, 60, 60);
        frame.add(upBtn);
        upBtn.addActionListener(e -> gamePanel.moveUp());

        JButton downBtn = new JButton("↓");
        downBtn.setBounds(30, 530, 60, 60);
        frame.add(downBtn);
        downBtn.addActionListener(e -> gamePanel.moveDown());

        Timer repaintTimer = new Timer(30, e -> gamePanel.repaint());
        repaintTimer.start();

        Timer speedTimer = new Timer(10000, e -> speed++);
        speedTimer.start();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void updateJumpDisplay() {
        jumpLabel.setText("Jumps: " + jumps);
    }

    private static void updateClickCounter() {
        clickCounter.setText("Clicks: " + clicks);
    }

    private static void updateHearts() {
        heartsLabel.setText("❤".repeat(hearts));
    }

    private static void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.out.println("Error writing high score");
        }
    }

    private static void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            highScore = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            highScore = 0;
        }
    }

    private static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) return;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }

    static class GamePanel extends JPanel {
        private int playerY;
        private final int playerRadius = 20;
        private final List<Rectangle> obstacles = new ArrayList<>();
        private final int[] tracks = {450, 550, 650};
        private final Timer obstacleMover;

        public GamePanel() {
            setBackground(Color.CYAN);
            playerY = tracks[1];
            new Timer(2000, e -> {
                int trackY = tracks[random.nextInt(tracks.length)];
                obstacles.add(new Rectangle(800, trackY - 25, 40, 50));
            }).start();

            obstacleMover = new Timer(30, e -> moveObstacles());
            obstacleMover.start();

            heartsLabel = new JLabel("❤❤❤❤❤", SwingConstants.CENTER);
            heartsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
            heartsLabel.setForeground(Color.RED);
            this.setLayout(null);
            heartsLabel.setBounds(300, 10, 200, 30);
            this.add(heartsLabel);
        }

        public void moveUp() {
            int index = Arrays.asList(tracks).indexOf(playerY);
            if (index > 0) playerY = tracks[index - 1];
        }

        public void moveDown() {
            int index = Arrays.asList(tracks).indexOf(playerY);
            if (index < tracks.length - 1) playerY = tracks[index + 1];
        }

        public void moveObstacles() {
            Iterator<Rectangle> it = obstacles.iterator();
            while (it.hasNext()) {
                Rectangle r = it.next();
                r.x -= speed;
                if (r.x < 0) it.remove();
                else if (new Rectangle(100 - playerRadius, playerY - playerRadius, playerRadius * 2, playerRadius * 2).intersects(r)) {
                    it.remove();
                    hearts--;
                    updateHearts();
                    if (hearts == 0) {
                        obstacleMover.stop();
                        if (score > highScore) {
                            highScore = score;
                            saveHighScore();
                        }
                        JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
                    }
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.fillOval(100 - playerRadius, playerY - playerRadius, playerRadius * 2, playerRadius * 2);
            g.setColor(Color.RED);
            for (Rectangle r : obstacles) {
                g.fillRect(r.x, r.y, r.width, r.height);
            }
        }
    }
}
