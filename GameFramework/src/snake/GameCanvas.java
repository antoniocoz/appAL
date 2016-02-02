package snake;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

// Custom drawing panel, written as an inner class.
public class GameCanvas extends JPanel implements KeyListener {
   // Constructor
	
   private GameMain main;	
   public GameCanvas(GameMain main) {
	  this.main=main; 
      setFocusable(true);  // so that can receive key-events
      requestFocus();
      addKeyListener(this);
   }

   // Override paintComponent to do custom drawing.
   // Called back by repaint().
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);   // paint background
      setBackground(main.COLOR_PIT);  // may use an image for background

      // Draw the game objects
      main.gameDraw(g);
   }

   // KeyEvent handlers
   @Override
   public void keyPressed(KeyEvent e) {
      main.gameKeyPressed(e.getKeyCode());
   }

   @Override
   public void keyReleased(KeyEvent e) { }

   @Override
   public void keyTyped(KeyEvent e) { }

   // Check if this pit contains the given (x, y), for collision detection
   public boolean contains(int x, int y) {
      if ((x < 0) || (x >= main.ROWS)) {
         return false;
      }
      if ((y < 0) || (y >= main.COLUMNS)) {
         return false;
      }
      return true;
   }
}
