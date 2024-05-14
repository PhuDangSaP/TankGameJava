
package objects;
import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import com.mycompany.tankgamejava.Util;
import java.awt.Graphics2D;
import java.util.Random;

enum EnemyState {
    IDLE,
    MOVING_RIGHT,
    MOVING_LEFT,
    MOVING_UP,
    MOVING_DOWN,
}

public class Enemy extends GameObject{
    final int speed = 10;
    EnemyState state;

    public Enemy(int x, int y) {
        super(x, y);
        state=EnemyState.IDLE;
    }

    @Override
    public void Update() {
        InputHandle();
        x += vx;
        y += vy;
        if (x > 512) {
            x = 490;
        }
        if (x < 0) {
            x = 0;
        }
        if (y > 384) {
            y = 350;
        }
        if (y < 0) {
            y = 0;
        }
    }

    public void InputHandle() {
        try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            
                e.printStackTrace();
            }
        Random random = new Random();
        char[] keys = {'a', 's', 'd'};
        char randomKey = keys[random.nextInt(keys.length)];
        int dirX = 0, dirY = 0;
        switch (randomKey) {
            case 'd' -> dirX = 1;
            case 'a' -> dirX = -1;
            case 's' -> dirY = -1;
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
        } else if (dirY < 0) {
            state = EnemyState.MOVING_DOWN;
            vy = speed;
            vx = 0;
            dir = 3;
        } else {
            state = EnemyState.IDLE;
            vx = 0;
            vy = 0;
        }
        
    }

    @Override
    public void Render(Graphics2D g2) {
        int aniId = -1;
        switch (state) {
            case EnemyState.IDLE -> {
                aniId = switch (dir) {
                    case 2 -> Util.ID_ENE1_IDLE_LEFT;
                    case 3 -> Util.ID_ENE1_IDLE_DOWN;
                    case 4 -> Util.ID_ENE1_IDLE_RIGHT;
                    default -> Util.ID_ENE1_IDLE_RIGHT;
                };
            }
            case EnemyState.MOVING_LEFT -> aniId = Util.ID_ENE1_MOVING_LEFT;
            case EnemyState.MOVING_DOWN -> aniId = Util.ID_ENE1_MOVING_DOWN;
            case EnemyState.MOVING_RIGHT -> aniId = Util.ID_ENE1_MOVING_RIGHT;
            default -> aniId = Util.ID_ENE1_IDLE_RIGHT;
        }
        ResourceManager.getInstance().getAnimation(aniId).Render(g2, x, y);
    }
    
}
