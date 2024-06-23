/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import com.mycompany.tankgamejava.Collision;
import com.mycompany.tankgamejava.CollisionEvent;
import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author USER
 */
public class Bullet extends GameObject {
    final int point = 100;
    final int speed = 2;

    public Bullet(int x, int y, int direction) {
        super(x, y);
        this.dir = direction;
    }

    @Override
    public void Update() {
        if (isDead) {
            return;
        }
        Collision.Process(this);
        switch (dir) {
            case 1 -> {
                vx = 0;
                vy = -speed;
            }
            case 2 -> {
                vx = -speed;
                vy = 0;
            }
            case 3 -> {
                vx = 0;
                vy = speed;
            }
            case 4 -> {
                vx = speed;
                vy = 0;
            }

        }
        x += vx;
        y += vy;
    }

    @Override
    public void Render(Graphics2D g2) {
        if (isDead) {
            return;
        }
        int spriteId = -1;
        switch (dir) {
            case 1 ->
                spriteId = 100; //up
            case 2 ->
                spriteId = 101; //left
            case 3 ->
                spriteId = 102; //down
            case 4 ->
                spriteId = 103; //right
        }
        ResourceManager.getInstance().getSprite(spriteId).draw(g2, x, y, 5);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, 5, 5);
    }

    @Override
    public void OnCollisionWith(CollisionEvent e) {
        if (!(e.obj instanceof Grass)&&!(e.obj instanceof River)) {
            isDead = true;
            if (!(e.obj instanceof SteelBrick)) {
                e.obj.destroy();
              
            }
            if(e.obj instanceof Enemy)
            {
                Game.getInstance().getPlaying().addScore(point);
            }
            if(e.obj instanceof Player)
            {
                
            }

        }
    }

    boolean isOffScreen() {
        return x < 0 || x > 512 || y < 0 || y > 416;
    }

}
