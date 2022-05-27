/*
* This GameFrame class sets up the initial window for the game,
* being a child of JFrame because JFrame manages frames. It also
* Runs the constructor in GamePanel class to create a GamePanel.
*
* @author  Anthony Chen
* @version 1.0
* @since   2022-05-27
*/

/* GameFrame class establishes the frame (window) for the game
It is a child of JFrame because JFrame manages frames
Runs the constructor in GamePanel class
*/

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{

  GamePanel panel;

  public GameFrame(){
    panel = new GamePanel(); // run GamePanel constructor
    this.add(panel);
    this.setTitle("Pong"); // set title for frame
    this.setResizable(false); // frame can't change size
    this.setBackground(Color.black); // set background colour
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack(); // makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); // makes window visible to user
    this.setLocationRelativeTo(null); // set window in middle of screen
    System.out.println("Welcome to Pong!"); // some console instructions
    System.out.println("Player 1 uses W and S");
    System.out.println("Player 2 uses K and M");
    System.out.println("Player 1 on the left, 2 on the right");
    System.out.println("First to 8 gets super paddle");
    System.out.println("First to 12 wins!");
  }
}