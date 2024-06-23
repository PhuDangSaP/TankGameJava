/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import com.mycompany.tankgamejava.MainMenuPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author USER
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean firePressed;
    public String state;
    MainMenuPanel menu;

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W ->
                upPressed = true;
            case KeyEvent.VK_S ->
                downPressed = true;
            case KeyEvent.VK_A ->
                leftPressed = true;
            case KeyEvent.VK_D ->
                rightPressed = true;
            case KeyEvent.VK_J ->
                firePressed = true;
            default -> {
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == "titlestate")
        {
            if (e.getKeyCode() == KeyEvent.VK_W)
            {
                menu.commandNum --;
                if (menu.commandNum < 0)
                    menu.commandNum = 2;
            }
            if (e.getKeyCode() == KeyEvent.VK_S)
            {
                menu.commandNum ++;
                if (menu.commandNum > 2)
                    menu.commandNum = 0;
            }
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W ->
                upPressed = true;
            case KeyEvent.VK_S ->
                downPressed = true;
            case KeyEvent.VK_A ->
                leftPressed = true;
            case KeyEvent.VK_D ->
                rightPressed = true;
            case KeyEvent.VK_J ->
                firePressed = true;
            default -> {
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W ->
                upPressed = false;
            case KeyEvent.VK_S ->
                downPressed = false;
            case KeyEvent.VK_A ->
                leftPressed = false;
            case KeyEvent.VK_D ->
                rightPressed = false;
            case KeyEvent.VK_J ->
                firePressed = false;
            default -> {
            }
        }
    }

}
