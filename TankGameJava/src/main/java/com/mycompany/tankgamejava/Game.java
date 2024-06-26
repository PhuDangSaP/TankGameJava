/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import gamestates.GameOver;
import gamestates.GameState;
import static gamestates.GameState.GAMEOVER;
import static gamestates.GameState.GAMEWIN;
import static gamestates.GameState.LEADERBOARD;
import static gamestates.GameState.LEVELSELECTION;
import static gamestates.GameState.MENU;
import static gamestates.GameState.PLAYING;
import gamestates.GameWin;
import gamestates.LeaderBoard;
import gamestates.LevelSelection;
import gamestates.Menu;
import gamestates.Playing;
import inputs.KeyHandler;
import inputs.MouseHandler;
import objects.Player;
import objects.Enemy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import level.LevelManager;
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
    public final int tileSize = 32;
    public final int maxScreenCol = 13;
    public final int maxScreenRow = 13;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int FPS_SET = 120;

    public boolean isGameOver = false;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Thread gameThread;

    private Playing playing;
    private Menu menu;
    private LevelSelection levelSelection;
    private GameOver gameOver;
    private GameWin gameWin;
    private LeaderBoard leaderBoard;

    private Game() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void init() {
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        this.setFocusable(true);
        requestFocus();
        loadResources();
        start();
    }

    public void start() {
        playing = new Playing(1);
        menu = new Menu();
        levelSelection = new LevelSelection();
        gameOver = new GameOver();
        gameWin = new GameWin();
        leaderBoard = new LeaderBoard();
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void loadResources() {

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

        res.addTexture(2, "Resources\\tank.png");
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
        switch (GameState.state) {
            case MENU -> {
                menu.Update();
            }
            case PLAYING -> {
                playing.Update();
            }
            case LEVELSELECTION -> {
                levelSelection.Update();
            }
            case GAMEOVER -> {
                gameOver.Update();
            }
            case GAMEWIN -> {
                gameWin.Update();
            }
            case LEADERBOARD -> {
                leaderBoard.Update();
            }
            default -> {
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        /*
        if (isGameOver) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("GAME OVER", screenWidth / 2 - 150, screenHeight / 2);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Press R to Restart or Q to Quit", screenWidth / 2 - 130, screenHeight / 2 + 50);
        } */
        switch (GameState.state) {
            case MENU -> {
                if (menu == null) {
                    return;
                }
                menu.Render(g2);
            }
            case PLAYING -> {
                if (playing == null) {
                    return;
                }
                playing.Render(g2);
            }
            case LEVELSELECTION -> {
                levelSelection.Render(g2);
            }
            case GAMEOVER -> {
                gameOver.Render(g2);
            }
            case GAMEWIN -> {
                gameWin.Render(g2);
            }
            case LEADERBOARD -> {
                leaderBoard.Render(g2);
            }
            default -> {
            }
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

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public LevelSelection getLevelSelection() {
        return levelSelection;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public GameWin getGameWin() {
        return gameWin;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
}
