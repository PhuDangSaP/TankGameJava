/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import com.mycompany.tankgamejava.Collision;
import com.mycompany.tankgamejava.CollisionEvent;
import inputs.KeyHandler;
import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import com.mycompany.tankgamejava.Util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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

    final int speed = 1;
    PlayerState state;

    public Player(int x, int y) {
        super(x, y);
        state = PlayerState.IDLE;
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
            dir = 4;
        } else if (dirX < 0) {
            state = PlayerState.MOVING_LEFT;
            vx = -speed;
            vy = 0;
            dir = 2;
        } else if (dirY > 0) {
            state = PlayerState.MOVING_UP;
            vy = -speed;
            vx = 0;
            dir = 1;

        } else if (dirY < 0) {
            state = PlayerState.MOVING_DOWN;
            vy = speed;
            vx = 0;
            dir = 3;
        } else {
            state = PlayerState.IDLE;
            vx = 0;
            vy = 0;
        }
    }

    @Override
    public void Render(Graphics2D g2) {
        int aniId = -1;
        switch (state) {
            case PlayerState.IDLE -> {
                aniId = switch (dir) {
                    case 1 ->
                        Util.ID_ANI_IDLE_UP;
                    case 2 ->
                        Util.ID_ANI_IDLE_LEFT;
                    case 3 ->
                        Util.ID_ANI_IDLE_DOWN;
                    case 4 ->
                        Util.ID_ANI_IDLE_RIGHT;
                    default ->
                        Util.ID_ANI_IDLE_UP;
                };
            }
            case PlayerState.MOVING_UP ->
                aniId = Util.ID_ANI_MOVING_UP;
            case PlayerState.MOVING_LEFT ->
                aniId = Util.ID_ANI_MOVING_LEFT;
            case PlayerState.MOVING_DOWN ->
                aniId = Util.ID_ANI_MOVING_DOWN;
            case PlayerState.MOVING_RIGHT ->
                aniId = Util.ID_ANI_MOVING_RIGHT;
            default ->
                aniId = Util.ID_ANI_IDLE_UP;
        }
        ResourceManager.getInstance().getAnimation(aniId).Render(g2, x, y);
        g2.setColor(Color.PINK);
        g2.drawRect(x, y, 32,32);
    }

    @Override
    public Rectangle getBoundingBox() {
        ResourceManager res = ResourceManager.getInstance();
        Rectangle rect = new Rectangle();
        switch (state) {
            case PlayerState.IDLE:
                rect= res.getSprite(51).getBoundingBox();

            case PlayerState.MOVING_UP:
                rect= res.getSprite(51).getBoundingBox();
            case PlayerState.MOVING_LEFT:
                rect= res.getSprite(53).getBoundingBox();
            case PlayerState.MOVING_DOWN:
                rect= res.getSprite(55).getBoundingBox();
            case PlayerState.MOVING_RIGHT:
                rect= res.getSprite(57).getBoundingBox();
            default:
                rect= res.getSprite(51).getBoundingBox();
        }
        rect.x =x;
        rect.y =y;
        return rect;
    }

    @Override
    public void OnCollisionWith(CollisionEvent e) {

    }

}
