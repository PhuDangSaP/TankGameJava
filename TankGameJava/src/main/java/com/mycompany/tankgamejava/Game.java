/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import inputs.KeyHandler;
import objects.Player;
import objects.Enemy;
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
    Enemy enemy1;
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
        objects= new Vector<>();
        ResourceManager res = ResourceManager.getInstance();

        res.addTexture(1, "Resources\\General.png");
        Texture tex = res.getTexture(1);
        
        //player
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
        //end player
        
        //enemy1
        res.addSprite(9, 130, 2, 141, 14, tex);
        res.addSprite(10, 162, 1, 175, 14, tex);
        res.addSprite(11, 194, 1, 205, 13, tex);
        res.addSprite(12, 224, 1, 237, 13, tex);
        res.addSprite(13, 144, 2, 158, 13, tex);
        res.addSprite(14, 177, 1, 191, 13, tex);
        res.addSprite(15, 209, 1, 221, 13, tex);
        res.addSprite(16, 241, 1, 253, 13, tex);

        Animation ani1 = new Animation(100000000);
        ani1.Add(9);
        res.addAnimation(Util.ID_ENE1_IDLE_UP, ani1);// idle up
        ani1 = new Animation(100000000);
        ani1.Add(10);
        res.addAnimation(Util.ID_ENE1_IDLE_LEFT, ani1); // idle left
        ani1 = new Animation(100000000);
        ani1.Add(11);
        res.addAnimation(Util.ID_ENE1_IDLE_DOWN, ani1); // idle down
        ani1 = new Animation(100000000);
        ani1.Add(12);
        res.addAnimation(Util.ID_ENE1_IDLE_RIGHT, ani1); // idle right

        ani1 = new Animation(100000000);
        ani1.Add(9);
        ani1.Add(13);
        res.addAnimation(Util.ID_ENE1_MOVING_UP, ani1); // moving up

        ani1 = new Animation(100000000);
        ani1.Add(10);
        ani1.Add(14);
        res.addAnimation(Util.ID_ENE1_MOVING_LEFT, ani1);// moving left

        ani1 = new Animation(100000000);
        ani1.Add(11);
        ani1.Add(15);
        res.addAnimation(Util.ID_ENE1_MOVING_DOWN, ani1);// moving down

        ani1 = new Animation(100000000);
        ani1.Add(12);
        ani1.Add(16);
        res.addAnimation(Util.ID_ENE1_MOVING_RIGHT, ani1);// moving right

        enemy1 = new Enemy(screenWidth-30, 0);
        objects.add(enemy1);
        //endenemy1

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
         if(objects==null) return;
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
