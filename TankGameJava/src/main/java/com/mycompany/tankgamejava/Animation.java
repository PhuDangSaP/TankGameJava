/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.awt.Graphics2D;
import java.util.Vector;

/**
 *
 * @author USER
 */

class AnimationFrame {

    Sprite sprite;
    int time;

    public AnimationFrame(Sprite sprite, int time) {
        this.sprite = sprite;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public Sprite getSprite() {
        return sprite;
    }

}

public class Animation {

    long lastFrameTime;
    int defaultTime;
    int currentFrame;
    Vector<AnimationFrame> frames;

    public Animation(int defaultTime) {
        this.defaultTime = defaultTime;
        lastFrameTime = -1;
        currentFrame = -1;
        frames = new Vector<>();
    }

    public void Add(int spriteId) {
        Sprite sprite = ResourceManager.getInstance().getSprite(spriteId);
        AnimationFrame frame = new AnimationFrame(sprite, defaultTime);
        frames.add(frame);
    }

    public void Render(Graphics2D g2,int x, int y, int size) {
        long now = System.nanoTime();
        if (currentFrame == -1) {
            currentFrame = 0;
            lastFrameTime = now;
        } else {
            int t = frames.get(currentFrame).getTime();
            if (now - lastFrameTime > t) {
                currentFrame++;
                lastFrameTime = now;
                if (currentFrame == frames.size()) {
                    currentFrame = 0;
                }
             }
        }
        frames.get(currentFrame).getSprite().draw(g2,x, y,size);
    }
}
