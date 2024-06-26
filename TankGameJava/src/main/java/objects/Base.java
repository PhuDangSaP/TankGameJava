/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import com.mycompany.tankgamejava.Collision;
import com.mycompany.tankgamejava.CollisionEvent;
import com.mycompany.tankgamejava.Game;
import com.mycompany.tankgamejava.ResourceManager;
import com.mycompany.tankgamejava.Sound;
import com.mycompany.tankgamejava.Util;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import gamestates.GameState;

/**
 *
 * @author USER
 */
public class Base extends GameObject {


    public Base(int x, int y) {
        super(x, y);
    }

    @Override
    public void Update() {
        if (isDead) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - deathTime > explosionDuration) {
                Game.getInstance().isGameOver=true;
                GameState.state=GameState.GAMEOVER;
                Sound.gameOver();
                return;
            }
        }
        else{
            Collision.Process(this);
        }
        
    }

    @Override
    public void Render(Graphics2D g2) {
        if (isDead) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - deathTime <= explosionDuration) {
                ResourceManager.getInstance().getAnimation(Util.ID_ANI_EXPLOSION).Render(g2, x, y, 28);
            }
            return;
        }
        ResourceManager.getInstance().getSprite(5).draw(g2, x, y, 32);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, 32, 32);
    }

    @Override
    public void OnCollisionWith(CollisionEvent e) {
        if (e.obj instanceof Bullet) {
            System.err.println("GameOver");
            Sound.explosion();
            e.obj.isDead = true;
            isDead=true;
            deathTime = System.currentTimeMillis();
            Game.getInstance().isGameOver=true;
        }
    }

}
