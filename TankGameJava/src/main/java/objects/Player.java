/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import inputs.KeyHandler;
import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author USER
 */
enum PlayerState {
    IDLE,
    MOVING_RIGHT,
    MOVING_LEFT,
    MOVING_UP,
    MOVING_DOWN,
}

public class Player extends GameObject {

    final int speed = 2;
    PlayerState state;

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void Update() {
        InputHandle();
        x += vx;
        y += vy;
        if (x > 512) {
            x = 500;
        }
        if (x < 0) {
            x = 0;
        }
        if (y > 384) {
            y = 380;
        }
        if (y < 0) {
            y = 0;
        }
    }

    public void InputHandle() {
         KeyHandler keyHandler = Game.getInstance().getKeyHandler();
        int dirX = 0, dirY = 0;
        if (keyHandler.rightPressed) {
            dirX = 1;
        } else if (keyHandler.leftPressed) {
            dirX = -1;
        } else if (keyHandler.upPressed) {
            dirY = 1;
        } else if (keyHandler.downPressed) {
            dirY = -1;
        }
        UpdateState(dirX, dirY);
    }

    public void UpdateState(int dirX, int dirY) {
        if (dirX > 0) {
            state = PlayerState.MOVING_RIGHT;
            vx = speed;
            vy = 0;
        } else if (dirX < 0) {
            state = PlayerState.MOVING_LEFT;
            vx = -speed;
            vy = 0;
        } else if (dirY > 0) {
            state = PlayerState.MOVING_UP;
            vy = -speed;
            vx = 0;
        } else if (dirY < 0) {
            state = PlayerState.MOVING_DOWN;
            vy = speed;
            vx = 0;
        } else {
            state = PlayerState.IDLE;
            vx = 0;
            vy = 0;
        }
    }

    @Override
    public void Render(Graphics2D g2) {
         ResourceManager.getInstance().getSprite(2).Draw(g2,x,y);
    }

}
