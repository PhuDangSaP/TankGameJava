/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;
import objects.GameObject;

/**
 *
 * @author USER
 */
enum CollisionDir {
    UP, DOWN, RIGHT, LEFT
}

public class Collision {

    public static Vector<GameObject> coObjects;

    public static CollisionEvent SweptAABB(GameObject src, GameObject dest) {
        if (src.isDead || dest.isDead) {
            return null;
        }
        float Sdx = src.getSpeedX();
        float Sdy = src.getSpeedY();

        float Ddx = dest.getSpeedX();
        float Ddy = dest.getSpeedY();

        float dx = Sdx - Ddx;
        float dy = Sdy - Ddy;

        Rectangle srcRect = src.getBoundingBox();
        Rectangle destRect = dest.getBoundingBox();

        if (dx == 0 && dy == 0) {
            return null;
        }

        Rectangle rect = new Rectangle();
        rect.x = dx > 0 ? srcRect.x : srcRect.x + (int) dx;
        rect.width = dx > 0 ? srcRect.width + (int) dx : srcRect.width;
        rect.y = dy > 0 ? srcRect.y + (int) dy : srcRect.y;
        rect.height = dy > 0 ? srcRect.height : srcRect.height + (int) dy;

        if (rect.intersects(destRect)) {
            return new CollisionEvent(dest, CollisionDir.UP);
        }
        return null;

    }

    public static void Process(GameObject objSrc) {
        ArrayList<CollisionEvent> coEvents = new ArrayList<>();

        for (GameObject obj : coObjects) {
            CollisionEvent e = SweptAABB(objSrc, obj);
            if (e != null) {
                coEvents.add(e);
            }
        }

        for (CollisionEvent e : coEvents) {
            objSrc.OnCollisionWith(e);
        }

    }

}
