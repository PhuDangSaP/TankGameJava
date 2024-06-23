/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import com.mycompany.tankgamejava.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import ui.MenuButton;

/**
 *
 * @author USER
 */
public class GameOver implements StateMethods {

    private MenuButton[] buttons = new MenuButton[2];

    public GameOver() {
        buttons[0] = new MenuButton(200, 150, 100, 50, GameState.LEVELSELECTION, "LevelSelection");
        buttons[1] = new MenuButton(200, 220, 100, 50, GameState.PLAYING, "Restart");
    }

    @Override
    public void Update() {
        for (MenuButton mb : buttons) {
            mb.Update();
        }
    }

    @Override
    public void Render(Graphics2D g2) {

        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.drawString("GAME OVER", Game.getInstance().screenWidth / 2 - 150, 50);
   
        for (MenuButton mb : buttons) {
            mb.Render(g2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
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
                    if (mb.equals(buttons[1])) {
                        Game.getInstance().getPlaying().setLevel(Game.getInstance().getPlaying().getCurrentLevel());
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
}
