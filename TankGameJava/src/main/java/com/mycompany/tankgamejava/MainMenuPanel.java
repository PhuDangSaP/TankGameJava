/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

/**
 *
 * @author Admin
 */
import inputs.KeyHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuPanel extends JPanel {
    private static final int TILE_SIZE = 32;
    private static final int MAX_SCREEN_COL = 13;
    private static final int MAX_SCREEN_ROW = 13;
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public int commandNum = 0;
    Graphics2D g2;
    KeyHandler keyHandler;

    public MainMenuPanel(JFrame window) {
        setLayout(new GridLayout(2, 1, 10, 10));
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
         
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        
        JButton startButton = new JButton("Start");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLevelSelectionDialog(window);
            }
        });
        
         leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderboard();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(exitButton);

        // Add title screen and buttons to the panel
       
        //add(buttonPanel, BorderLayout.SOUTH);

        keyHandler = new KeyHandler();
         addKeyListener(keyHandler);
        keyHandler.state = "titlestate";
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
         
        //TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        String text = "Tank Game Java";
       
        int x = getXforCenteredText(text);
        int y = TILE_SIZE*3;
        
        setBackground(Color.BLACK);
        
        g2.setColor(Color.GRAY);
        g2.drawString(text, x+5, y+5);
        
        g2.setColor(Color.white);
        g2.drawString(text,x,y);
        
        x = SCREEN_WIDTH/2 - (TILE_SIZE*2)/2;
        y = TILE_SIZE*4;
        BufferedImage tankImage = null;
        try {
            tankImage = ImageIO.read(new File("Resources\\tank.png"));
        } catch (IOException ex) {
            Logger.getLogger(MainMenuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        g2.drawImage(tankImage, x, y, TILE_SIZE*3/2, TILE_SIZE*3/2, null);
        
        
        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        text = "Start";
        x = getXforCenteredText(text);
        y += TILE_SIZE*4;
        g2.drawString(text, x, y);
        if (commandNum == 0)
        {
            g2.drawString(">", x - TILE_SIZE, y);
        }
        
        text = "Leaderboard";
        x = getXforCenteredText(text);
        y += TILE_SIZE;
        g2.drawString(text, x, y);
         if (commandNum == 1)
        {
            g2.drawString(">", x - TILE_SIZE, y);
        }
        
        text = "Exit";
        x = getXforCenteredText(text);
        y += TILE_SIZE;
        g2.drawString(text, x, y);
         if (commandNum == 2)
        {
            g2.drawString(">", x - TILE_SIZE, y);
        }
        
    }

    private void showLevelSelectionDialog(JFrame window) {
        String[] options = {"Level 1", "Level 2", "Level 3"};
        int level = JOptionPane.showOptionDialog(
                window,
                "Select a level to start:",
                "Level Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (level != JOptionPane.CLOSED_OPTION) {
            Game game = Game.getInstance();
            game.setLevel(level + 1); // Levels are 1, 2, 3
            window.getContentPane().removeAll();
            window.add(game);
            window.revalidate();
            window.repaint();
            game.init();
        }
    }
    
    private void showLeaderboard() {
        // Implement leaderboard display logic here
        JOptionPane.showMessageDialog(this, "Leaderboard is not implemented yet.");
    }
    
    public int getXforCenteredText (String text)
    {
         int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
         int x = SCREEN_WIDTH/2 - length/2;
         return x;
    }
}

