/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

import com.mycompany.tankgamejava.Game;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import ui.MenuButton;

/**
 *
 * @author USER
 */
public class LevelSelection implements StateMethods {

    private MenuButton[] levelButtons;
    private static final int COLUMNS = 5;
    private static final int ROWS = 6;
    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 20;
    private static final int maxLevel = 20;

    public LevelSelection() {
        levelButtons = new MenuButton[20];
        int startX = 50;
        int startY = 50;

        for (int i = 0; i < maxLevel; i++) {
            int col = i % COLUMNS;
            int row = i / COLUMNS;
            int x = startX + col * (BUTTON_WIDTH + BUTTON_SPACING);
            int y = startY + row * (BUTTON_HEIGHT + BUTTON_SPACING);
            levelButtons[i] = new MenuButton(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, GameState.PLAYING, "" + (i + 1));
        }
    }

    @Override
    public void Update() {
        for (MenuButton mb : levelButtons) {
            mb.Update();
        }
    }

    @Override
    public void Render(Graphics2D g2) {
        for (MenuButton mb : levelButtons) {
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
        for (MenuButton mb : levelButtons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : levelButtons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGameState();
                    int levelIndex = getButtonIndex(mb);
                    Game.getInstance().getPlaying().setLevel(levelIndex + 1);
                }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : levelButtons) {
            mb.setMouseOver(false);
        }
        for (MenuButton mb : levelButtons) {
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

    }

    private void resetButtons() {
        for (MenuButton mb : levelButtons) {
            mb.resetBools();
        }
    }

    private int getButtonIndex(MenuButton button) {
        for (int i = 0; i < levelButtons.length; i++) {
            if (levelButtons[i] == button) {
                return i;
            }
        }
        return -1;
    }

}
