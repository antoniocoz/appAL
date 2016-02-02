package snake.entity;

import java.awt.Graphics;

import snake.GameMain;
/**
 * SnakeSegment represents one horizontal or vertical segment of a snake. The "head" of
 * this segment is at (headX, headY). The segment is drawn starting from the "head"
 * and proceeding "length" cells in "direction", until it reaches the "tail".
 */
public class SnakeSegment {
   
   private int headX, headY;   // The position of the head of this segment
   private int length;         // length of this segment
   private Snake.Direction direction;   // direction of this segment
   
   // Construct a new snake segment at given (headX, headY), length and direction.
   public SnakeSegment(int headX, int headY, int length, Snake.Direction direction) {
      this.headX = headX;
      this.headY = headY;
      this.direction = direction;
      this.length = length;
   }
   
   // Grow by adding one cell to the head of this segment.
   public void grow() {
      ++length;
      // need to adjust the headX and headY
      switch (direction) {
         case LEFT:  --headX; break;
         case RIGHT: ++headX; break;
         case UP:    --headY; break;
         case DOWN:  ++headY; break;
      }
   }
   
   // Shrink by removing one cell from the tail of this segment.
   public void shrink() {
      length--;  // no change in headX and headY needed
   }
   
   // Get the length, in cells, of this segment.
   public int getLength() { return length; }
   
   // Get the X coordinate of the cell that contains the head of this snake segment.
   public int getHeadX() { return headX; }
   
   // Get the Y coordinate of the cell that contains the head of this snake segment.
   public int getHeadY() { return headY; }
   
   // Get the X coordinate of the cell that contains the tail of this snake segment.
   private int getTailX() {
      if (direction == Snake.Direction.LEFT) {
         return headX + length - 1;
      } else if (direction == Snake.Direction.RIGHT) {
         return headX - length + 1;
      } else {   // UP and DOWN
         return headX;
      }
   }
   
   // Get the Y coordinate of the cell that contains the tail of this snake segment.
   private int getTailY() {
      if (direction == Snake.Direction.DOWN) {
         return headY - length + 1;
      } else if (direction == Snake.Direction.UP) {
         return headY + length - 1;
      } else {   // LEFT and RIGHT
         return headY;
      }
   }
   
   // Returns true if the snake segment contains the given cell. Used for collision detection.
   public boolean contains(int x, int y) {
      switch (direction) {
         case LEFT:  return ((y == this.headY) && ((x >= this.headX) && (x <= getTailX())));
         case RIGHT: return ((y == this.headY) && ((x <= this.headX) && (x >= getTailX())));
         case UP:    return ((x == this.headX) && ((y >= this.headY) && (y <= getTailY())));
         case DOWN:  return ((x == this.headX) && ((y <= this.headY) && (y >= getTailY())));
      }
      return false;
   }
   
   // Draw this segment.
   public void draw(Graphics g) {
      int x = headX;
      int y = headY;
   
      switch (direction) {
         case LEFT:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * GameMain.CELL_SIZE, y * GameMain.CELL_SIZE,
                     GameMain.CELL_SIZE - 1, GameMain.CELL_SIZE - 1, true);
               ++x;
            }
            break;
         case RIGHT:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * GameMain.CELL_SIZE, y * GameMain.CELL_SIZE,
                     GameMain.CELL_SIZE - 1, GameMain.CELL_SIZE - 1, true);
               --x;
            }
            break;
         case UP:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * GameMain.CELL_SIZE, y * GameMain.CELL_SIZE,
                     GameMain.CELL_SIZE - 1, GameMain.CELL_SIZE - 1, true);
               ++y;
            }
            break;
         case DOWN:
            for (int i = 0; i < length; ++i) {
               g.fill3DRect(x * GameMain.CELL_SIZE, y * GameMain.CELL_SIZE,
                     GameMain.CELL_SIZE - 1, GameMain.CELL_SIZE - 1, true);
               --y;
            }
            break;
      }
   }
   
   // For debugging.
   @Override
   public String toString() {
      return "SnakeSegment[head=(" + headX + "," + headY + "), tail=(" + getTailX() + ","
            + getTailY() + "), length=" + length + ", dir=" + direction + "]";
   }
}