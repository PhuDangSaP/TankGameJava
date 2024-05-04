/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author USER
 */
public class Sprite {
    int id;
    int left;
    int top;
    int right;
    int bottom;
    BufferedImage image;
    public Sprite(int id,int left,int top,int right,int bottom,Texture texture )
    {
        this.id=id;
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
        this.image=texture.image.getSubimage(left, top, right-left, bottom-top);
    }
    public void Draw(Graphics2D g2,int x,int y)
    {      
        g2.drawImage(image, x,y,32,32, null);
    }
}
