/*
* This GamePanel class acts as the main game loop, continuously running
* the game, listening to keyboard input, updating the graphics, and
* checking if any win conditions are met. It uses threading to multitask.
*
* @author  Anthony Chen
* @version 1.0
* @since   2022-05-27
*/

/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 1024;
  public static final int GAME_HEIGHT = 512;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public PlayerBall ball;
  public PlayerPaddle paddleOne;
  public PlayerPaddle paddleTwo;
  public int scoreOne = 0;
  public int scoreTwo = 0;
  public boolean oneWin;
  public boolean twoWin;

  public GamePanel(){
    ball = new PlayerBall(GAME_WIDTH/2, GAME_HEIGHT/2, getRandomSpeed(), getRandomSpeed()); //create a player controlled ball, set start location to middle of screen
    paddleOne = new PlayerPaddle(8, 224, 'w', 's');
    paddleTwo = new PlayerPaddle(1008, 224, 'k', 'm');

    this.setFocusable(true); //make everything in this class appear on the screen
    this.addKeyListener(this); //start listening for keyboard input

    // method stub temporarily left empty
    addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

			}
		});
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    //make this class run at the same time as other classes (without this each class would "pause" while another class runs). By using threading we can remove lag, and also allows us to do features like display timers in real time!
    gameThread = new Thread(this);
    gameThread.start();
  }

  //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
  public void paint(Graphics g){
    //we are using "double buffering here" - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen, then simply move the image on screen as needed.
    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics();
    graphics.setFont(new Font("Roboto", Font.PLAIN, 64));
    graphics.setColor(Color.white);
    graphics.drawString(scoreOne + "", 402, 64);
    graphics.drawString(scoreTwo + "", 542, 64);
    if (scoreOne >= 8 && scoreTwo < 8) { // give players super paddle
      if (paddleOne.speed == 8) {
        paddleOne.speed = 12;
        System.out.println("Player 1 gets super paddle!");
      }
    } else if (scoreTwo >= 8 && scoreOne < 8) {
      if (paddleTwo.speed == 8) {
        paddleTwo.speed = 12;
        System.out.println("Player 2 gets super paddle!");
      }
    }
    if (scoreOne == 12) {
      System.out.println("Player 1 wins!");
      oneWin = true;

    } else if (scoreTwo == 12) {
      System.out.println("Player 2 wins!");
      twoWin = true;
    }
    if (oneWin) {
      image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
      graphics = image.getGraphics();
      graphics.setFont(new Font("Roboto", Font.PLAIN, 64));
      graphics.setColor(Color.white);
      graphics.drawString("Player 1 wins!", 32, 64);
    } else if (twoWin) {
      image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
      graphics = image.getGraphics();
      graphics.setFont(new Font("Roboto", Font.PLAIN, 64));
      graphics.setColor(Color.white);
      graphics.drawString("Player 2 wins!", 32, 64);
    }
    draw(graphics);//update the positions of everything on the screen
    g.drawImage(image, 0, 0, this); //move the image on the screen
  }

  //call the draw methods in each class to update positions as things move
  public void draw(Graphics g){
    for (int i = 0; i < 512; i += 16) { // draw central line
      g.setColor(Color.white);
      g.fillRect(508, i, 8, 8);
    }
    ball.draw(g);
    paddleOne.draw(g);
    paddleTwo.draw(g);
  }

  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy
  public void move(){
    ball.move();
    paddleOne.move();
    paddleTwo.move();
  }

  //handles all collision detection and responds accordingly
  public void checkCollision(){

    //force player to remain on screen
    if(ball.y <= 0){
      ball.y = 0;
      ball.yVelocity = -ball.yVelocity;
    }
    if(ball.y >= GAME_HEIGHT - PlayerBall.BALL_DIAMETER){
      ball.y = GAME_HEIGHT - PlayerBall.BALL_DIAMETER;
      ball.yVelocity = -ball.yVelocity;
    }
    if(ball.x <= 0){
      scoreTwo += 1;
      ball = new PlayerBall(GAME_WIDTH/2, GAME_HEIGHT/2, PlayerBall.speed, getRandomSpeed()); //create a player controlled ball, set start location to middle of screen
    }
    if(ball.x + PlayerBall.BALL_DIAMETER >= GAME_WIDTH){
      scoreOne += 1;
      ball = new PlayerBall(GAME_WIDTH/2, GAME_HEIGHT/2, -PlayerBall.speed, getRandomSpeed()); //create a player controlled ball, set start location to middle of screen
    }

    if (paddleOne.y <= 0) {
      paddleOne.y = 0;
    }
    if (paddleOne.y >= 448) {
      paddleOne.y = 448;
    }
    if (paddleTwo.y <= 0) {
      paddleTwo.y = 0;
    }
    if (paddleTwo.y >= 448) {
      paddleTwo.y = 448;
    }

    if (ball.intersects(paddleOne)) {
      ball.xVelocity = -ball.xVelocity;
    }
    if (ball.intersects(paddleTwo)) {
      ball.xVelocity = -ball.xVelocity;
    }
  }

  //run() method is what makes the game continue running without end. It calls other methods to move objects,  check for collision, and update the screen
  public void run(){
    //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen.
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if(delta >= 1){
        move();
        checkCollision();
        repaint();
        delta--;
        if (oneWin || twoWin) {
          try {
            TimeUnit.SECONDS.sleep(86400);
          } catch (InterruptedException e) {

          }
        }
      }
    }
  }

  // method stubs temporarily left empty
  public void keyPressed(KeyEvent e){
    paddleOne.keyPressed(e);
    paddleTwo.keyPressed(e);
  }

  public void keyReleased(KeyEvent e){
    paddleOne.keyReleased(e);
    paddleTwo.keyReleased(e);
  }

  public void keyTyped(KeyEvent e){

  }

  public int getRandomSpeed() {
    int result = 0;
    while (result == 0) {
      result = ThreadLocalRandom.current().nextInt(-PlayerBall.speed, PlayerBall.speed);
    }
    return result;
  }
}