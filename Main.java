/*
* This Main class just starts the entire game. It keeps things organised.
*
* @author  Anthony Chen
* @version 1.0
* @since   2022-05-27
*/

/* Main class starts the game
All it does is run the constructor in GameFrame class

This is a common technique among coders to keep things organized (and handy when coding in repl.it since we're forced to call a class Main, which isn't always very descriptive)
*/

class Main {
  public static void main(String[] args) {
    new GameFrame();
  }
}