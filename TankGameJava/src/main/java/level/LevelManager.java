/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package level;

import com.mycompany.tankgamejava.Collision;
import data.GameData;
import data.SaveLoad;
import gamestates.GameState;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Base;
import objects.Brick;
import objects.Enemy;
import objects.GameObject;
import objects.Grass;
import objects.River;
import objects.SteelBrick;

/**
 *
 * @author USER
 */
public class LevelManager {

    private int currentLevel;
    private final int tileSize;
    private final int maxScreenCol;
    private final int maxScreenRow;
    Vector<GameObject> objects;
    private int score;

    public LevelManager(int level, int tileSize, int maxScreenCol, int maxScreenRow) {
        this.currentLevel = level;
        this.tileSize = tileSize;
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        objects = new Vector<>();
        score = 0;
        loadLevel();
    }

    public void loadLevel() {
        objects.clear();
        objects.add(new Enemy(0, 0));
        try {
            File map = new File("Resources\\Level" + currentLevel + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(map));

            int i = 0; // row
            int j = 0; // col
            while (i < maxScreenRow && j < maxScreenCol) {
                String line = br.readLine();
                while (j < maxScreenRow) {
                    String[] numbers = line.split(" ");
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

    public void Update() {
        checkGameWin();
        Collision.coObjects = objects;
        for (GameObject obj : objects) {
            obj.Update();
        }

    }

    public void Render(Graphics2D g2) {
        for (GameObject obj : objects) {
            obj.Render(g2);
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setLevel(int level) {
        this.currentLevel = level;
        loadLevel();
    }

    public void checkGameWin() {
        boolean isWin = true;
        for (GameObject obj : objects) {
            if (obj instanceof Enemy && !obj.isDead) {
                isWin = false;
            }
        }
        if (isWin) {
            try {
                SaveLoad.saveData(new GameData(currentLevel, score));
            } catch (IOException ex) {
                Logger.getLogger(LevelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            GameState.state = GameState.GAMEWIN;
        }
    }

    public void addScore(int point) {
        score += point;
    }
;
}
