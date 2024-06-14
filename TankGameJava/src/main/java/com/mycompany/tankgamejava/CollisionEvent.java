/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import objects.GameObject;

/**
 *
 * @author USER
 */
public class CollisionEvent {
    
    public GameObject obj;
    public CollisionDir dir;

    CollisionEvent(GameObject obj, CollisionDir dir) {
        this.obj = obj;
        this.dir = dir;
    }
}
