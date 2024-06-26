/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import com.mycompany.tankgamejava.Sound;
import data.GameData;
import data.SaveLoad;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.MenuButton;

/**
 *
 * @author USER
 */
public class Menu implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];

    public Menu() {
        init();
        Sound.startStage();
    }

    private void init() {
        buttons[0] = new MenuButton(200, 100, 100, 50, GameState.LEVELSELECTION, "Start");
        buttons[1] = new MenuButton(200, 200, 100, 50, GameState.LEADERBOARD, "Leaderboard");
        buttons[2] = new MenuButton(200, 300, 100, 50, GameState.EXIT, "Exit");
        
    }

    @Override
    public void Update() {
        for (MenuButton mb : buttons) {
            mb.Update();
        }
    }

    @Override
    public void Render(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.drawString("TANK GAME", Game.getInstance().screenWidth / 2 - 150, 50);
        g2.drawImage(ResourceManager.getInstance().getTexture(2).image,Game.getInstance().screenWidth / 2-16, 60,32, 32, null);
        for (MenuButton mb : buttons) {
            mb.Render(g2);
        }
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBoundingBox().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Sound.clickSound();
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
         for (MenuButton mb : buttons) {
             mb.setMouseOver(false);
         }
         for (MenuButton mb : buttons) {
             if(isIn(e, mb))
             {
                 mb.setMouseOver(true);
                 break;
             }
         }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBools();
        }
    }

}
