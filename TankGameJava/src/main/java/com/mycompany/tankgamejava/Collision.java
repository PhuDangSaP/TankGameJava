/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.awt.Rectangle;
import objects.GameObject;

/**
 *
 * @author USER
 */
enum CollisionDir {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class CollisionEvent {

    GameObject obj;
    CollisionDir dir;

    public CollisionEvent(GameObject obj, CollisionDir dir) {
        this.obj = obj;
        this.dir = dir;
    }
}

public class Collision {

    public static CollisionEvent SweptAABB(GameObject src, GameObject dest) {
        Rectangle srcRect = src.getBoundingBox();
        Rectangle destRect = dest.getBoundingBox();
        CollisionEvent e = new CollisionEvent(dest, CollisionDir.DOWN);
        return e;
    }

    public static boolean isColliding(Rectangle srcRect, Rectangle destRect) {
        float left = destRect.x - (srcRect.x + srcRect.width);
        float top = (destRect.y + destRect.height) - srcRect.y;
        float right = (destRect.x + destRect.width) - srcRect.x;
        float bottom = destRect.y - (srcRect.y + srcRect.height);

        return !(left > 0 || right < 0 || top < 0 || bottom > 0);
    }
}
