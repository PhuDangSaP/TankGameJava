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
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import objects.GameObject;

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
    Vector<GameObject> objects;

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

    public void loadResources() {

        ResourceManager res = ResourceManager.getInstance();

        res.addTexture(1, "D:\\TankGameJava\\Resources\\General.png");
        Texture tex = res.getTexture(1);
        res.addSprite(1, 1, 2, 13, 14, tex);
        res.addSprite(2, 17, 2, 29, 14, tex);
        res.addSprite(3, 34, 1, 46, 13, tex);
        res.addSprite(4, 50, 1, 62, 13, tex);
        res.addSprite(5, 65, 1, 77, 13, tex);
        res.addSprite(6, 81, 1, 93, 13, tex);
        res.addSprite(7, 97, 1, 109, 13, tex);
        res.addSprite(8, 113, 1, 125, 13, tex);

        Animation ani = new Animation(100000000);
        ani.Add(1);
        res.addAnimation(Util.ID_ANI_IDLE_UP, ani);// idle up
        ani = new Animation(100000000);
        ani.Add(3);
        res.addAnimation(Util.ID_ANI_IDLE_LEFT, ani); // idle left
        ani = new Animation(100000000);
        ani.Add(5);
        res.addAnimation(Util.ID_ANI_IDLE_DOWN, ani); // idle down
        ani = new Animation(100000000);
        ani.Add(7);
        res.addAnimation(Util.ID_ANI_IDLE_RIGHT, ani); // idle right

        ani = new Animation(100000000);
        ani.Add(1);
        ani.Add(2);
        res.addAnimation(Util.ID_ANI_MOVING_UP, ani); // moving up

        ani = new Animation(100000000);
        ani.Add(3);
        ani.Add(4);
        res.addAnimation(Util.ID_ANI_MOVING_LEFT, ani);// moving left

        ani = new Animation(100000000);
        ani.Add(5);
        ani.Add(6);
        res.addAnimation(Util.ID_ANI_MOVING_DOWN, ani);// moving down

        ani = new Animation(100000000);
        ani.Add(7);
        ani.Add(8);
        res.addAnimation(Util.ID_ANI_MOVING_RIGHT, ani);// moving right

        player = new Player(100, 100);
        objects.add(player);

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
        for (GameObject obj : objects) {
            obj.Update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (GameObject obj : objects) {
            obj.Render(g2);
        }

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
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Texture(image);
    }

}
