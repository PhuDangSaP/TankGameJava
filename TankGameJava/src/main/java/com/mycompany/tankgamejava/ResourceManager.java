/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USER
 */
public final class ResourceManager {
    private static ResourceManager instance;
    
    Map<Integer, Texture> textures = new HashMap<>();
    Map<Integer, Sprite> sprites = new HashMap<>();
    private ResourceManager()
    {
        
    }
    public void AddTexture(Integer id,String path)
    {
        textures.put(id, Game.getInstance().loadTexture(path));
    }
    
    public Texture getTexture(Integer id)
    {
        return textures.get(id);
    }
    public void AddSprite(int id,int left,int top,int right,int bottom,Texture texture)
    {
        Sprite sprite= new Sprite(id,left,top,right,bottom, texture);
        sprites.put(id,sprite);
    }
    
    public Sprite getSprite(Integer id)
    {
        return sprites.get(id);
    }
    
    public static ResourceManager getInstance()
    {
        if(instance==null)
        {
            instance=new ResourceManager();
        }
        return instance;
    }
}
