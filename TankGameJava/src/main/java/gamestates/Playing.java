/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import com.mycompany.tankgamejava.Game;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import level.LevelManager;
import objects.Player;

/**
 *
 * @author USER
 */
public class Playing implements StateMethods {

    LevelManager levelManager;
    Player player;

    public Playing(int level) {
        player = new Player(128, 384, 3);
        Game game = Game.getInstance();
        levelManager = new LevelManager(level, game.tileSize, game.maxScreenCol, game.maxScreenRow);
    }

    @Override
    public void Update() {
        
        levelManager.Update();
        player.Update();
    }

    @Override
    public void Render(Graphics2D g2) {
        player.Render(g2);
        levelManager.Render(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
   
    }

    @Override
    public void mouseReleased(MouseEvent e) {
   
    }

    @Override
    public void mouseMoved(MouseEvent e) {
   
    }

    @Override
    public void keyPressed(KeyEvent e) {
   
    }

    @Override
    public void keyReleased(KeyEvent e) {
   
    }
    public void setLevel(int level)
    {
        levelManager.setLevel(level);
        player = new Player(128, 384, 3);
    }
    public int getCurrentLevel()
    {
        return levelManager.getCurrentLevel();
    }
    public void addScore(int point) {
        levelManager.addScore(point);
    }
}
