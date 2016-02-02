package snake.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import snake.GameMain;
/**
 * Food is a food item that the snake can eat. It is placed randomly in the pit.
 */
public class Food {
   
   private int x, y;   // current food location (x, y) in cells
   private Color color = Color.BLUE;   // color for display
   private Random rand = new Random(); // For randomly placing the food
   
   // Default constructor.
   public Food() {
      // place outside the pit, so that it will not be "displayed".
      x = -1;
      y = -1;
   }
   
   // Regenerate a food item. Randomly place inside the pit (slightly off the edge).
   public void regenerate() {
      x = rand.nextInt(GameMain.COLUMNS - 4) + 2;
      y = rand.nextInt(GameMain.ROWS - 4) + 2;
   }
   
   // Returns the x coordinate of the cell that contains this food item.
   public int getX() { return x; }
   
   // Returns the y coordinate of the cell that contains this food item.
   public int getY() { return y; }
   
   // Draw itself.
   public void draw(Graphics g) {
      g.setColor(color);
      g.fill3DRect(x * GameMain.CELL_SIZE, y * GameMain.CELL_SIZE,
            GameMain.CELL_SIZE, GameMain.CELL_SIZE, true);
   }
}
