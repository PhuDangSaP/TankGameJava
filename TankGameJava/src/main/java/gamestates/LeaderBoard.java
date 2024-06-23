/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestates;

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
public class LeaderBoard implements StateMethods {

    private MenuButton backButton;
    private int[] scores;

    public LeaderBoard() {
        backButton = new MenuButton(50, 10, 50, 30, GameState.MENU, "Back");
        scores = new int[20];
        getScores();
    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Graphics2D g2) {

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.drawString("Level", 100, 20);
        g2.drawString("Score", 200, 20);

        for (int i = 0; i < scores.length; i++) {
            g2.drawString("Level " + (i + 1), 100, 50 + i * 18);
            g2.drawString(String.valueOf(scores[i]), 200, 50 + i * 18);
        }
        backButton.Render(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, backButton)) {
            backButton.setMousePressed(true);

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, backButton)) {
            if (backButton.isMousePressed()) {
                backButton.applyGameState();
            }

        }
        resetButtons();
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

    public void getScores() {
        for (int i = 0; i < 20; i++) {
            try {
                GameData data = SaveLoad.getData(i + 1);
                scores[i] = data.getScore();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(LeaderBoard.class.getName()).log(Level.SEVERE, null, ex);
                scores[i] = 0;
            }
        }
    }

    private void resetButtons() {
        backButton.resetBools();
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBoundingBox().contains(e.getX(), e.getY());
    }

}
