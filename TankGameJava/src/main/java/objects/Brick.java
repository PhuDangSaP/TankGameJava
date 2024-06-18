/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import com.mycompany.tankgamejava.CollisionEvent;
import com.mycompany.tankgamejava.ResourceManager;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author USER
 */
public class Brick extends GameObject{

    public Brick(int x, int y) {
        super(x, y);
    }

    

    @Override
    public void Update() {
   
    }

    @Override
    public void Render(Graphics2D g2) {
        ResourceManager.getInstance().getSprite(1).draw(g2, x, y, 32);
    }
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, 32, 32);
    }

    @Override
    public void OnCollisionWith(CollisionEvent e) {
 
    }
    
}
