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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import objects.Base;
import objects.Brick;
import objects.GameObject;
import objects.Grass;
import objects.River;
import objects.SteelBrick;

/**
 *
 * @author USER
 */
public final class Game extends JPanel implements Runnable {

    private static Game instance;
    final int tileSize = 32;
    final int maxScreenCol = 13;
    final int maxScreenRow = 13;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    final int FPS_SET = 120;

    KeyHandler keyHandler;
    Thread gameThread;
    Player player;
    Enemy enemy1;
    Vector<GameObject> objects;
    int mapData[][];

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
        objects = new Vector<>();
        mapData = new int[maxScreenCol][maxScreenRow];
        ResourceManager res = ResourceManager.getInstance();

        res.addTexture(1, "Resources\\General.png");
        Texture tex = res.getTexture(1);

        //player
        res.addSprite(51, 1, 2, 13, 14, tex);
        res.addSprite(52, 17, 2, 29, 14, tex);
        res.addSprite(53, 34, 1, 46, 13, tex);
        res.addSprite(54, 50, 1, 62, 13, tex);
        res.addSprite(55, 65, 1, 77, 13, tex);
        res.addSprite(56, 81, 1, 93, 13, tex);
        res.addSprite(57, 97, 1, 109, 13, tex);
        res.addSprite(58, 113, 1, 125, 13, tex);

        Animation ani = new Animation(100000000);
        ani.Add(51);
        res.addAnimation(Util.ID_ANI_IDLE_UP, ani);// idle up
        ani = new Animation(100000000);
        ani.Add(53);
        res.addAnimation(Util.ID_ANI_IDLE_LEFT, ani); // idle left
        ani = new Animation(100000000);
        ani.Add(55);
        res.addAnimation(Util.ID_ANI_IDLE_DOWN, ani); // idle down
        ani = new Animation(100000000);
        ani.Add(57);
        res.addAnimation(Util.ID_ANI_IDLE_RIGHT, ani); // idle right

        ani = new Animation(100000000);
        ani.Add(51);
        ani.Add(52);
        res.addAnimation(Util.ID_ANI_MOVING_UP, ani); // moving up

        ani = new Animation(100000000);
        ani.Add(53);
        ani.Add(54);
        res.addAnimation(Util.ID_ANI_MOVING_LEFT, ani);// moving left

        ani = new Animation(100000000);
        ani.Add(55);
        ani.Add(56);
        res.addAnimation(Util.ID_ANI_MOVING_DOWN, ani);// moving down

        ani = new Animation(100000000);
        ani.Add(57);
        ani.Add(58);
        res.addAnimation(Util.ID_ANI_MOVING_RIGHT, ani);// moving right

        player = new Player(160, 384);
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

        enemy1 = new Enemy(150, 100);
        objects.add(enemy1);
        //endenemy1

        //tile
        res.addSprite(1, 256, 0, 271, 15, tex); // brick
        res.addSprite(2, 256, 16, 271, 31, tex); // steel brick
        res.addSprite(3, 272, 32, 287, 47, tex); // grass
        res.addSprite(4, 272, 48, 287, 63, tex); // river
        res.addSprite(5, 304, 32, 319, 47, tex); // base
        //bullet
        res.addSprite(100, 323, 102, 325, 105, tex); // bullet up
        res.addSprite(101, 330, 102, 333, 104, tex); // bullet left
        res.addSprite(102, 339, 102, 341, 105, tex); // bullet down
        res.addSprite(103, 346, 102, 349, 104, tex); // bullet right

        //explose
        res.addSprite(150, 256, 128, 269, 143, tex);
        res.addSprite(151, 271, 128, 286, 143, tex);
        res.addSprite(152, 288, 128, 303, 143, tex);

        Animation exploseAni = new Animation(100000000);
        exploseAni.Add(150);
        exploseAni.Add(151);
        exploseAni.Add(152);
        res.addAnimation(Util.ID_ANI_EXPLOSION, exploseAni);

        loadMap();

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
        Collision.coObjects = objects;
        for (GameObject obj : objects) {
            obj.Update();
        }
        player.Update();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (objects == null) {
            return;
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (GameObject obj : objects) {
            obj.Render(g2);
        }
        if (player != null) {
            player.Render(g2);
        }
        //renderMap(g2);

        g2.dispose();
    }

    public KeyHandler getKeyHandler() {
        return this.keyHandler;
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

    public void loadMap() {
        try {
            File map = new File("Resources\\Level1.txt");
            BufferedReader br = new BufferedReader(new FileReader(map));

            int i = 0; // row
            int j = 0; // col
            while (i < maxScreenRow && j < maxScreenCol) {
                String line = br.readLine();
                while (j < maxScreenRow) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[j]);

                    switch (num) {
                        case 1 ->
                            objects.add(new Brick(j * tileSize, i * tileSize));
                        case 2 ->
                            objects.add(new SteelBrick(j * tileSize, i * tileSize));
                        case 3 ->
                            objects.add(new Grass(j * tileSize, i * tileSize));
                        case 4 ->
                            objects.add(new River(j * tileSize, i * tileSize));
                        case 5 ->
                            objects.add(new Base(j * tileSize, i * tileSize));
                    }
                    mapData[i][j] = num;
                    j++;
                }
                if (j == maxScreenCol) {
                    j = 0;
                    i++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeObject(GameObject obj) {/*
        objects.remove(obj);
        ResourceManager res= ResourceManager.getInstance();
        for(int i=0;i<maxScreenRow;i++)
        {
            for(int j=0;j<maxScreenCol;j++)
            {
                int id=mapData[i][j];
                if(id!=0)
                {
                     res.getSprite(id).draw(g2, j*tileSize, i*tileSize);
                }
               
            }
        }*/
    }

    /*
    void renderMap(Graphics2D g2) {
        ResourceManager res = ResourceManager.getInstance();
        for (int i = 0; i < maxScreenRow; i++) {
            for (int j = 0; j < maxScreenCol; j++) {
                int id = mapData[i][j];
                if (id != 0) {
                    res.getSprite(id).draw(g2, j * tileSize, i * tileSize, tileSize);
                }

            }
        }
    }*/
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
}
