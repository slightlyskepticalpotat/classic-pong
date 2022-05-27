/*
* This PlayerPaddle class defines any player paddles, and how they move.
* With minor changes, more PlayerPaddle objects could be created to create
* four-player pong, make the paddles move freely, or something else.
*
* @author  Anthony Chen
* @version 1.0
* @since   2022-05-27
*/

import java.awt.*;
import java.awt.event.*;

public class PlayerPaddle extends Rectangle{
    public int direction; // direction of paddle
    public int speed = 8; // speed of paddle
    public char keyOne; // two keys that control paddle
    public char keyTwo;

    // constructor creates a paddle at a given location with given controls
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

    // stops paddle moving when key released
    public void keyReleased(KeyEvent e){
        if(e.getKeyChar() == keyOne || e.getKeyChar() == keyTwo){
          direction = 0;
          move();
        }
    }

    // called frequently from the GamePanel class
    // draws the current location of the paddle to the screen
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, 8, 64);
    }
}
