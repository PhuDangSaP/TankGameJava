/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import inputs.KeyHandler;
import objects.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author USER
 */
public final class Game extends JPanel implements Runnable {
    private static Game instance;
    final int tileSize = 32;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    final int FPS_SET = 120;

    KeyHandler keyHandler;
    Thread gameThread;
    Player player;

    private Game() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void init() {
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        this.setFocusable(true);
        requestFocus();

        loadResources();
        gameThread = new Thread(this);
        gameThread.start();

    }
    public void loadResources()
    {
        ResourceManager res=ResourceManager.getInstance();
        res.AddTexture(1, "D:\\TankGameJava\\Resources\\General.png");
        Texture tex= res.getTexture(1);
        res.AddSprite(1, 128, 2, 141, 14, tex);
        res.AddSprite(2, 1, 2, 13, 14, tex);
        System.out.println(res.getTexture(1).image);
        player = new Player(100, 100);
       
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now;
        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {

                Update();
                repaint();
                lastFrame = now;
            }

        }
    }

    public void Update() {
        player.Update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        player.Render(g2);
        g2.dispose();
    }

    public KeyHandler getKeyHandler() {
        return this.keyHandler;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    Texture loadTexture(String path) {
        BufferedImage image = null;
         System.out.println(path);
        
        try {         
            image = ImageIO.read(new File(path));
            System.out.println("Load image success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Texture(image);
    }

}
