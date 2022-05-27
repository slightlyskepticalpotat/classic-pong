/*
* This PlayerBall class controls the ball. It controls how the ball is
* drawn, and how it moves. Note that balls can be initialised with
* starting velocity in the constructor.
*
* @author  Anthony Chen
* @version 1.0
* @since   2022-05-27
*/

/* PlayerBall class defines behaviours for the player-controlled ball

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/

import java.awt.*;

public class PlayerBall extends Rectangle{

  public int xVelocity;
  public int yVelocity;
  public static final int speed = 8; // max horizontal or vertical speed of ball
  public static final int BALL_DIAMETER = 10; // size of ball

  // constructor creates ball at given location with given velocity
  public PlayerBall(int x, int y, int xV, int yV){
    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
    xVelocity = xV;
    yVelocity = yV;
  }

  // called frequently from both PlayerBall class and GamePanel class
  // updates the current location of the ball
  public void move(){
    y = y + yVelocity;
    x = x + xVelocity;
  }

  // called frequently from the GamePanel class
  // draws the current location of the ball to the screen
  public void draw(Graphics g){
    g.setColor(Color.white);
    g.fillRect(x, y, BALL_DIAMETER, BALL_DIAMETER);
  }
}