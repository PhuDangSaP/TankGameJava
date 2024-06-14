/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import com.mycompany.tankgamejava.CollisionEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author USER
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected float vx;
    protected float vy;
    protected int dir;
    
    public GameObject(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    public float getSpeedX(){
        return vx;
    }
    public float getSpeedY()
    {
        return vy;
    }
    public abstract Rectangle getBoundingBox();
    public abstract void OnCollisionWith(CollisionEvent e);
    public abstract void Update();
    public abstract void Render(Graphics2D g2);
    
}
