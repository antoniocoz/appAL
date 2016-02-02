package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import snake.entity.Food;
import snake.entity.Snake;
   
public class GameMain extends JPanel {   // main class for the game
   
   // Define constants for the game
   static final String TITLE = "SNAKE";
   public static final int ROWS = 40;      // number of rows (in cells)
   public static final int COLUMNS = 40;   // number of columns (in cells)
   public static final int CELL_SIZE = 15; // Size of a cell (in pixels)
   static final int CANVAS_WIDTH  = COLUMNS * CELL_SIZE;  // width and height of the game screen
   static final int CANVAS_HEIGHT = ROWS * CELL_SIZE;
   static final int UPDATES_PER_SEC = 3;    // number of game update per second
   static final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SEC;  // nanoseconds
   public final Color COLOR_PIT = Color.LIGHT_GRAY;
   
   // Enumeration for the states of the game.
   static enum GameState {
      INITIALIZED, PLAYING, PAUSED, GAMEOVER, DESTROYED
   }
   static GameState state;   // current state of the game
   
   // Define instance variables for the game objects
   private Food food;
   private Snake snake;
   
   // Handle for the custom drawing panel
   private GameCanvas pit;
   
   // Constructor to initialize the UI components and game objects
   public GameMain() {
      // Initialize the game objects
      gameInit();
   
      // UI components
      pit = new GameCanvas(this);
      pit.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      add(pit);
   
      // Start the game.
      gameStart();
   }
   
   // ------ All the game related codes here ------
   
   // Initialize all the game objects, run only once in the constructor of the main class.
   public void gameInit() {
      // Allocate a new snake and a food item, do not regenerate.
      snake = new Snake(ROWS,COLUMNS,CELL_SIZE);
      food = new Food();
      state = GameState.INITIALIZED;
   }
   
   // Shutdown the game, clean up code that runs only once.
   public void gameShutdown() { }
   
   // To start and re-start the game.
   public void gameStart() { 
      // Create a new thread
      Thread gameThread =  new Thread() {
         // Override run() to provide the running behavior of this thread.
         @Override
         public void run() {
            gameLoop();
         }
      };
      // Start the thread. start() calls run(), which in turn calls gameLoop().
      gameThread.start();
   }
   
   // Run the game loop here.
   private void gameLoop() {
      // Regenerate and reset the game objects for a new game
      if (state == GameState.INITIALIZED || state == GameState.GAMEOVER) {
         // Generate a new snake and a food item
         snake.regenerate();
         int x, y;
         do {
            food.regenerate();
            x = food.getX();
            y = food.getY();
         } while (snake.contains(x, y)); // regenerate if food placed under the snake
         state = GameState.PLAYING;
      }
   
      // Game loop
      long beginTime, timeTaken, timeLeft;   // in msec
      while (state != GameState.GAMEOVER) {
         beginTime = System.nanoTime();
         if (state == GameState.PLAYING) {
            // Update the state and position of all the game objects,
            // detect collisions and provide responses.
            gameUpdate();
         }
         // Refresh the display
         repaint();
         // Delay timer to provide the necessary delay to meet the target rate
         timeTaken = System.nanoTime() - beginTime;
         timeLeft = (UPDATE_PERIOD_NSEC - timeTaken) / 1000000;  // in milliseconds
         if (timeLeft < 10) timeLeft = 10;  // set a minimum
         try {
            // Provides the necessary delay and also yields control so that other thread can do work.
            Thread.sleep(timeLeft);
         } catch (InterruptedException ex) { }
      }
   }
   
   // Update the state and position of all the game objects,
   // detect collisions and provide responses.
   public void gameUpdate() { 
      snake.update();
      processCollision();
   }
   
   // Collision detection and response
   public void processCollision() {
      // check if this snake eats the food item
      int headX = snake.getHeadX();
      int headY = snake.getHeadY();
   
      if (headX == food.getX() && headY == food.getY()) {
         // food eaten, regenerate one
         int x, y;
         do {
            food.regenerate();
            x = food.getX();
            y = food.getY();
         } while (snake.contains(x, y));
      } else {
         // not eaten, shrink the tail
         snake.shrink();
      }
   
      // Check if the snake moves out of bounds
      if (!pit.contains(headX, headY)) {
         state = GameState.GAMEOVER;
         return;
      }
   
      // Check if the snake eats itself
      if (snake.eatItself()) {
         state = GameState.GAMEOVER;
         return;
      }
   }
   
   // Refresh the display. Called back via rapaint(), which invoke the paintComponent().
   public void gameDraw(Graphics g) {
      // draw game objects
      snake.draw(g);
      food.draw(g);
      // game info
      g.setFont(new Font("Dialog", Font.PLAIN, 14));
      g.setColor(Color.BLACK);
      g.drawString("Snake: (" + snake.getHeadX() + "," + snake.getHeadY() + ")", 5, 25);
      if (state == GameState.GAMEOVER) {
         g.setFont(new Font("Verdana", Font.BOLD, 30));
         g.setColor(Color.RED);
         g.drawString("GAME OVER!", 200, CANVAS_HEIGHT / 2);
      }
   }
   
   // Process a key-pressed event. Update the current state.
   public void gameKeyPressed(int keyCode) {
      switch (keyCode) {
         case KeyEvent.VK_UP:
            snake.setDirection(Snake.Direction.UP);
            break;
         case KeyEvent.VK_DOWN:
            snake.setDirection(Snake.Direction.DOWN);
            break;
         case KeyEvent.VK_LEFT:
            snake.setDirection(Snake.Direction.LEFT);
            break;
         case KeyEvent.VK_RIGHT:
            snake.setDirection(Snake.Direction.RIGHT);
            break;
      }
   }
   
}