package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import gamestates.GameState;

public class MenuButton {

    private int x, y, width, height;
    private GameState state;
    private boolean mouseOver, mousePressed;
    private String text;

    public MenuButton(int x, int y, int width, int height, GameState state, String text) {
        this.x = x-width/2;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
        this.text = text;
    }

    public void Update() {

    }

    public void Render(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        if (mouseOver) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 20, y + height / 2);
        if (mousePressed) {
            g2.setColor(Color.RED);
            g2.drawRect(x, y, width, height);
        }
    }

    public void applyGameState() {
        GameState.state = state;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }

    public void resetBools() {
        this.mouseOver = false;
        this.mousePressed = false;
    }
}
