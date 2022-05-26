import java.awt.*;
import java.awt.event.*;

public class PlayerPaddle extends Rectangle{
    public int direction;
    public int speed = 8;
    public char keyOne;
    public char keyTwo;

    // constructor creates a paddle at a given location
    public PlayerPaddle(int x, int y, char kO, char kT){
        super(x, y, 8, 64);
        keyOne = kO;
        keyTwo = kT;
    }

    // moves the paddle
    public void move(){
        y = y + direction * speed;
    }

    // controls the paddle
    public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == keyOne){
          direction = -1;
          move();
        }

        if(e.getKeyChar() == keyTwo){
          direction = 1;
          move();
        }
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyChar() == keyOne || e.getKeyChar() == keyTwo){
          direction = 0;
          move();
        }
    }

    //called frequently from the GamePanel class
    //draws the current location of the paddle to the screen
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, 8, 64);
    }
}
