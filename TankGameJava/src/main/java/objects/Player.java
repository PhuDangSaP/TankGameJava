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
import com.mycompany.tankgamejava.Sound;
import com.mycompany.tankgamejava.Util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import gamestates.GameState;
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
    DEAD
}

public class Player extends GameObject {

    final int speed = 1;
    PlayerState state;
    ArrayList<Bullet> bullets;
    final int fireRate = 500;
    long lastFiredTime = 0;
    public long deathTime = 0;
    final int explosionDuration = 1000; 
    final int respawnTime = 2000; 
    long respawnStartTime = 0;
    final int spawnX, spawnY; 
    int spawnCount; 
    final int initialSpawnCount;

    public Player(int x, int y, int initialSpawnCount) {
        super(x, y);
        this.spawnX = x;
        this.spawnY = y;
        this.initialSpawnCount = initialSpawnCount;
        this.spawnCount = initialSpawnCount;
        state = PlayerState.IDLE;
        bullets = new ArrayList<>();
        dir = 1;
    }

    @Override
    public void Update() {
        if (isDead) { 
            long currentTime = System.currentTimeMillis();
            if (currentTime - deathTime > explosionDuration) {
                if (respawnStartTime == 0) {
                    respawnStartTime = currentTime; 
                } else if (currentTime - respawnStartTime > respawnTime) {
                    Respawn();
                    return;
                }
            }
        } else {
            Collision.Process(this);
            InputHandle();
            x += vx;
            y += vy;
            if (x > 384) {
                x = 384;
            }
            if (x < 0) {
                x = 0;
            }
            if (y > 384) {
                y = 384;
            }
            if (y < 0) {
                y = 0;
            }

            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.Update();
                if (bullet.isOffScreen()) {
                    bullets.remove(i);
                    //Collision.coObjects.remove(bullet);
                    i--;
                }
            }
        }
    }

    public void InputHandle() {
        if (isDead) {
            return;
        }

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

        if (keyHandler.firePressed) {
            fire();
        }

        UpdateState(dirX, dirY);
    }

    public void UpdateState(int dirX, int dirY) {
        if (isDead) {
            return;
        }

        if (dirX > 0) {
            state = PlayerState.MOVING_RIGHT;
            vx = speed;
            vy = 0;
            dir = 4; // Right

        } else if (dirX < 0) {
            state = PlayerState.MOVING_LEFT;
            vx = -speed;
            vy = 0;
            dir = 2; // left
        } else if (dirY > 0) {
            state = PlayerState.MOVING_UP;
            vy = -speed;
            vx = 0;
            dir = 1; // up

        } else if (dirY < 0) {
            state = PlayerState.MOVING_DOWN;
            vy = speed;
            vx = 0;
            dir = 3; // down
        } else {
            state = PlayerState.IDLE;
            vx = 0;
            vy = 0;
        }
    }

    @Override
    public void Render(Graphics2D g2) {
        if (isDead) {
           
            long currentTime = System.currentTimeMillis();
            System.err.println(currentTime+"---"+deathTime);
            
            if (currentTime - deathTime <= explosionDuration) {
                ResourceManager.getInstance().getAnimation(Util.ID_ANI_EXPLOSION).Render(g2, x, y, 28);
            }
            return; 
        }

        int aniId = -1;
        switch (state) {
            case IDLE -> {
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
            case MOVING_UP ->
                aniId = Util.ID_ANI_MOVING_UP;
            case MOVING_LEFT ->
                aniId = Util.ID_ANI_MOVING_LEFT;
            case MOVING_DOWN ->
                aniId = Util.ID_ANI_MOVING_DOWN;
            case MOVING_RIGHT ->
                aniId = Util.ID_ANI_MOVING_RIGHT;
            default ->
                aniId = Util.ID_ANI_IDLE_UP;
        }
        ResourceManager.getInstance().getAnimation(aniId).Render(g2, x, y, 28);
        g2.setColor(Color.PINK);
        g2.drawRect(x, y, 28, 28);

        for (Bullet bullet : bullets) {
            bullet.Render(g2);
        }
    }

    public void fire() {
        if (isDead) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFiredTime >= fireRate) {
            int offSetX = 0;
            int offSetY = 0;

            switch (dir) {
                case 1 -> {
                    offSetX = 14;
                    offSetY = -7;
                }
                case 2 -> {
                    offSetX = -7;
                    offSetY = 14;
                }
                case 3 -> {
                    offSetX = 16;
                    offSetY = 30;
                }
                case 4 -> {
                    offSetX = 30;
                    offSetY = 16;
                }
            }

            Bullet bullet = new Bullet(x + offSetX, y + offSetY, dir);
            bullets.add(bullet);
            Sound.fireSound();
            //Collision.coObjects.add(bullet);
            lastFiredTime = currentTime;
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, 28, 28);
    }

    @Override
    public void OnCollisionWith(CollisionEvent e) {
        if (e.obj instanceof Enemy || e.obj instanceof Bullet && !isDead) {
            isDead = true;
            deathTime = System.currentTimeMillis();
            state = PlayerState.DEAD;
            Sound.explosion();
            System.err.println(e.obj.getClass());
            if(e.obj instanceof Bullet)
            {
                e.obj.destroy();
            }
        }

        if (e.obj instanceof Brick || e.obj instanceof SteelBrick || e.obj instanceof River|| e.obj instanceof Base) {
            x -= vx;
            y -= vy;
        }
    }
    

    public void Respawn() {
        if (spawnCount > 0) {
            isDead = false;
            state = PlayerState.IDLE;
            x = spawnX;
            y = spawnY;
            vx = 0;
            vy = 0;
            respawnStartTime = 0;
            spawnCount--; 
        }
        else {
            GameState.state=GameState.GAMEOVER;
            Sound.gameOver();
        }
    }
}
