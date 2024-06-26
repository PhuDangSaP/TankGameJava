package objects;

import com.mycompany.tankgamejava.Collision;
import com.mycompany.tankgamejava.CollisionEvent;
import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import com.mycompany.tankgamejava.Sound;
import com.mycompany.tankgamejava.Util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

enum EnemyState {
    IDLE,
    MOVING_RIGHT,
    MOVING_LEFT,
    MOVING_UP,
    MOVING_DOWN,
    DEAD
}

public class Enemy extends GameObject {

    final int speed = 1;
    EnemyState state;
    final int fireRate = 2000;
    long lastFiredTime = 0;
    ArrayList<Bullet> bullets;
    private long lastInputTime;

    public Enemy(int x, int y) {
        super(x, y);
        state = EnemyState.IDLE;
        bullets = new ArrayList<>();
    }

    @Override
    public void Update() {
        if (isDead) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - deathTime > explosionDuration) {
                return;

            }
        } else {
            Collision.Process(this);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastInputTime >= 5000) {
                InputHandle();
                lastInputTime = currentTime;
            }

            x += vx;
            y += vy;
            if (x > 384) {
                x = 384;
                InputHandle();
            }
            if (x < 0) {
                x = 0;
                InputHandle();
            }
            if (y > 384) {
                y = 384;
                InputHandle();
            }
            if (y < 0) {
                y = 0;
                InputHandle();
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
            fire();
        }
    }

    public void InputHandle() {

        Random random = new Random();
        char[] keys = {'a', 's', 'd', 'w'};
        char randomKey = keys[random.nextInt(keys.length)];
        int dirX = 0, dirY = 0;
        switch (randomKey) {
            case 'd' ->
                dirX = 1;
            case 'a' ->
                dirX = -1;
            case 's' ->
                dirY = 1;
            case 'w' ->
                dirY = -1;
            default -> {
            }
        }
        UpdateState(dirX, dirY);
    }

    public void UpdateState(int dirX, int dirY) {
        if (dirX > 0) {
            state = EnemyState.MOVING_RIGHT;
            vx = speed;
            vy = 0;
            dir = 4;
        } else if (dirX < 0) {
            state = EnemyState.MOVING_LEFT;
            vx = -speed;
            vy = 0;
            dir = 2;
        } else if (dirY > 0) {
            state = EnemyState.MOVING_DOWN;
            vy = speed;
            vx = 0;
            dir = 3;
        } else if (dirY < 0) {
            state = EnemyState.MOVING_UP;
            vy = -speed;
            vx = 0;
            dir = 1;
        } else {
            state = EnemyState.IDLE;
            vx = 0;
            vy = 0;
        }

    }

    @Override
    public void Render(Graphics2D g2) {
        if (isDead) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - deathTime <= explosionDuration) {
                ResourceManager.getInstance().getAnimation(Util.ID_ANI_EXPLOSION).Render(g2, x, y, 28);
            }
            return; // Stop rendering the player after the explosion
        }
        int aniId = -1;
        switch (state) {
            case EnemyState.IDLE -> {
                aniId = switch (dir) {
                    case 1 ->
                        Util.ID_ENE1_IDLE_UP;
                    case 2 ->
                        Util.ID_ENE1_IDLE_LEFT;
                    case 3 ->
                        Util.ID_ENE1_IDLE_DOWN;
                    case 4 ->
                        Util.ID_ENE1_IDLE_RIGHT;
                    default ->
                        Util.ID_ENE1_IDLE_RIGHT;
                };
            }
            case EnemyState.MOVING_UP ->
                aniId = Util.ID_ENE1_MOVING_UP;
            case EnemyState.MOVING_LEFT ->
                aniId = Util.ID_ENE1_MOVING_LEFT;
            case EnemyState.MOVING_DOWN ->
                aniId = Util.ID_ENE1_MOVING_DOWN;
            case EnemyState.MOVING_RIGHT ->
                aniId = Util.ID_ENE1_MOVING_RIGHT;
            default ->
                aniId = Util.ID_ENE1_IDLE_RIGHT;
        }
        ResourceManager.getInstance().getAnimation(aniId).Render(g2, x, y, 28);
        g2.setColor(Color.YELLOW);
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
                    offSetX = 14;
                    offSetY = 30;
                }
                case 4 -> {
                    offSetX = 30;
                    offSetY = 14;
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
        /*
        Rectangle rect = ResourceManager.getInstance().getSprite(9).getBoundingBox();
        rect.x = x;
        rect.y = y;
        return rect;*/
        return new Rectangle(x, y, 28, 28);
    }

    @Override
    public void OnCollisionWith(CollisionEvent e) {
        if (e.obj instanceof Brick || e.obj instanceof SteelBrick || e.obj instanceof River || e.obj instanceof Base) {
            if (vx > 0) {
                x = e.obj.x - 32;
            } else if (vx < 0) {
                x = e.obj.x + 32;
            } else if (vy > 0) {
                y = e.obj.y - 32;
            } else {
                y = e.obj.y + 32;
            }
            InputHandle();
        }

        if (e.obj instanceof Bullet) {
            isDead = true;
            deathTime = System.currentTimeMillis();
            state = EnemyState.DEAD;
            Sound.explosion();
        }
    }

}
