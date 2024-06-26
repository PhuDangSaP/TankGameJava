/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import com.mycompany.tankgamejava.Game;
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
public class GameWin implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];

    public GameWin() {
        buttons[0] = new MenuButton(200, 150, 100, 50, GameState.LEVELSELECTION, "LevelSelection");
        buttons[1] = new MenuButton(200, 220, 100, 50, GameState.PLAYING, "Restart");
        buttons[2] = new MenuButton(200, 290, 100, 50, GameState.PLAYING, "Next Level");
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
        g2.drawString("GAME WIN", Game.getInstance().screenWidth / 2 - 150, 50);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("SCORE", Game.getInstance().screenWidth / 2-50, 70);
        g2.drawString(getScore()+"", Game.getInstance().screenWidth / 2-10 , 100);
        for (MenuButton mb : buttons) {
            mb.Render(g2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                Sound.clickSound();
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
                    if (mb.equals(buttons[1])) {
                        Game.getInstance().getPlaying().setLevel(Game.getInstance().getPlaying().getCurrentLevel());
                    } else if (mb.equals(buttons[2])) {
                        Game.getInstance().getPlaying().setLevel(Game.getInstance().getPlaying().getCurrentLevel() + 1);
                    }
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
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBoundingBox().contains(e.getX(), e.getY());
    }
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBools();
        }
    }
    public int getScore()
    {
        try {
            GameData data= SaveLoad.getData(Game.getInstance().getPlaying().getCurrentLevel());
            return data.getScore();
        } catch (IOException ex) {
            Logger.getLogger(GameWin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameWin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
