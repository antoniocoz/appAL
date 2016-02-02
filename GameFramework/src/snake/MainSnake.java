package snake;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainSnake {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      // Use the event dispatch thread to build the UI for thread-safety.
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            JFrame frame = new JFrame("SNAKE");
	            frame.setContentPane(new GameMain());  // main JPanel as content pane
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.pack();
	            frame.setLocationRelativeTo(null); // center the application window
	            frame.setVisible(true);            // show it
	         }
	      });

	}

}
