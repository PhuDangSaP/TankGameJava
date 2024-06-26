/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package gamestates;

/**
 *
 * @author USER
 */
public enum GameState {
    MENU,
    PLAYING,
    LEADERBOARD,
    LEVELSELECTION,
    GAMEOVER,
    GAMEWIN,
    EXIT;
    public static GameState state = MENU;
}
