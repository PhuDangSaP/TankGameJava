/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author USER
 */
public class SaveLoad {

    public static void saveData(GameData data) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = new FileOutputStream("Level" + (data.getLevel()) + ".tank");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(data);
        out.close();
        fileOut.close();

    }

    public static GameData getData(int level) throws FileNotFoundException, IOException, ClassNotFoundException {
        GameData data = null;
        FileInputStream fileIn = new FileInputStream("Level" + level + ".tank");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        data = (GameData) in.readObject();
        in.close();
        fileIn.close();

        return data;
    }
}
//"C:\Users\USER\source\repos\TankGameJava\TankGameJava\Level1.tank"