import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, escape;

    @Override
    public void keyTyped(KeyEvent e) {} // NOT USED EVER

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns number of key pressed
        
        if (code == KeyEvent.VK_ESCAPE) {
            escape = true;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_Z) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_Q) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {} // NOT USED EVER

    public Direction getDirection() {
        if (upPressed && !downPressed && !leftPressed && !rightPressed) {
            upPressed = false;
            return Direction.NORD;
        } else if (!upPressed && downPressed && !leftPressed && !rightPressed) {
            downPressed = false;
            return Direction.SUD;
        } else if (!upPressed && !downPressed && leftPressed && !rightPressed) {
            leftPressed = false;
            return Direction.OUEST;
        } else if (!upPressed && !downPressed && !leftPressed && rightPressed) {
            rightPressed = false;
            return Direction.EST;
        }
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        return Direction.NONE; // Default direction when no arrow keys are pressed
    }
    

    public boolean Pause(){
        boolean pause = this.escape;
        this.escape = false;
        return pause;
    }
}