/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tankgamejava;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private static Clip fireSE;
    private static Clip explosionSE;
    private static Clip mainSE;
    private static Clip gameoverSE;
    private static Clip gamewinSE;
    private static Clip clickSE;

    private static boolean check = false;
    
     public static void initialize() {
        System.out.println("INITIALIZE");
        try {

            File fSE = new File("sound/bullet_shot.wav");
            fireSE = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(fSE);
            fireSE.open(ais);
            fireSE.setFramePosition(fireSE.getFrameLength());

            File ex1 = new File("sound/explosion_2.wav");
            explosionSE = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(ex1);
            explosionSE.open(ais);
            explosionSE.setFramePosition(explosionSE.getFrameLength());


            File mainmenu = new File("sound/stage_start.wav");
            mainSE = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(mainmenu);
            mainSE.open(ais);
            mainSE.setFramePosition(mainSE.getFrameLength());


            File game_over = new File("sound/game_over.wav");
            gameoverSE = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(game_over);
            gameoverSE.open(ais);
            gameoverSE.setFramePosition(gameoverSE.getFrameLength());

            File game_win = new File("sound/game_win.wav");
            gamewinSE = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(game_win);
            gamewinSE.open(ais);
            gamewinSE.setFramePosition(gamewinSE.getFrameLength());
            
            File click = new File("sound/click.wav");
            clickSE = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(click);
            clickSE.open(ais);
            clickSE.setFramePosition(clickSE.getFrameLength());

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE,
                                                               null, ex);
        }
        check = true;
    }

    public static void fireSound() {
        if (check) {
            fireSE.loop(1);
        } else {
            initialize();
            fireSE.loop(1);
        }
    }


    public static void explosion() {
        if (check) {
            explosionSE.loop(1);
        } else {
            initialize();
            explosionSE.loop(1);
        }
    }

 
    public static void startStage() {
        if (check) {
            mainSE.loop(1);
        } else {
            initialize();
            mainSE.loop(1);
        }
    }

    public static void gameWin() {
        if (check) {
            gamewinSE.loop(1);
        } else {
            initialize();
            gamewinSE.loop(1);
        }
    }

    public static void gameOver() {
        if (check) {
            gameoverSE.loop(1);
        } else {
            initialize();
            gameoverSE.loop(1);
        }
    }
    
     public static void clickSound() {
        if (check) {
            clickSE.loop(1);
        } else {
            initialize();
            clickSE.loop(1);
        }
    }

    
}
