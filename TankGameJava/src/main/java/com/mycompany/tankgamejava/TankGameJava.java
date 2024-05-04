/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tankgamejava;

import javax.swing.JFrame;

/**
 *
 * @author USER
 */
public class TankGameJava {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Tank Game");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        Game game=Game.getInstance();
        window.add(game);
        window.pack();
        game.init();
    }
}
