/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import com.mycompany.tankgamejava.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import gamestates.GameState;
import static gamestates.GameState.GAMEOVER;
import static gamestates.GameState.GAMEWIN;
import static gamestates.GameState.LEVELSELECTION;
import static gamestates.GameState.MENU;
import static gamestates.GameState.PLAYING;

/**
 *
 * @author USER
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                Game.getInstance().getMenu().mouseClicked(e);
                break;
            case PLAYING:
                break;
            case LEVELSELECTION:
                Game.getInstance().getLevelSelection().mouseClicked(e);
                break;
            case GAMEOVER:
                Game.getInstance().getGameOver().mouseClicked(e);
                break;
            case GAMEWIN:
                Game.getInstance().getGameWin().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                Game.getInstance().getMenu().mousePressed(e);
                break;
            case PLAYING:
                break;
            case LEVELSELECTION:
                Game.getInstance().getLevelSelection().mousePressed(e);
                break;
            case GAMEOVER:
                Game.getInstance().getGameOver().mousePressed(e);
                break;
            case GAMEWIN:
                Game.getInstance().getGameWin().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                Game.getInstance().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                break;
            case LEVELSELECTION:
                Game.getInstance().getLevelSelection().mouseReleased(e);
                break;
            case GAMEOVER:
                Game.getInstance().getGameOver().mouseReleased(e);
                break;
            case GAMEWIN:
                Game.getInstance().getGameWin().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                Game.getInstance().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                break;
            case LEVELSELECTION:
                Game.getInstance().getLevelSelection().mouseMoved(e);
                break;
            case GAMEOVER:
                Game.getInstance().getGameOver().mouseMoved(e);
                break;
            case GAMEWIN:
                Game.getInstance().getGameWin().mouseMoved(e);
                break;
            default:
                break;
        }
    }

}
