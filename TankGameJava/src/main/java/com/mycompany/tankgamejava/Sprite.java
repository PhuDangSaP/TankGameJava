/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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

    public Sprite(int id, int left, int top, int right, int bottom, Texture texture) {
        this.id = id;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.image = texture.image.getSubimage(left, top, right - left, bottom - top);
    }

    public void draw(Graphics2D g2, int x, int y, int size) {
        g2.drawImage(image, x, y, size, size, null);

    }
    public void draw(Graphics2D g2, int x, int y, int width,int height) {
        g2.drawImage(image, x, y, width, height, null);

    }

    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(image, x, y, 30, 30, null);

    }

    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, right - left, bottom - top);
    }
}
