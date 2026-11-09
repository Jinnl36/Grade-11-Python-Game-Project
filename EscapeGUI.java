/**
 * EscapeGUI.java (STARTER CODE)
 * - set up the GUI for the Escape game
 * - set up the listener to handle key presses
 *
 * DO NOT MODIFY THIS CLASS!
 */

// General Graphics
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class EscapeGUI {
   // Global variables
   // Demensions of the different components on the game board
   public static final int WIDTH = 1280;
   public static final int HEIGHT = 720;
   
   public static final int HEADING_HEIGHT = 100;
   public static final int TILE_WIDTH = 32;
   public static final int TILE_HEIGHT = 32;

   // Window and drawing variables
   public static JFrame window;
   public static Canvas canvas;
   public static BufferStrategy bufferStrategy;
   
   // Info of the image files
   public static final String IMG_FOLDER = "images";
   public static final String IMG_EXTENSION = ".png";

   // Stores all game images
   public static final HashMap<String, Image> images = new HashMap<>();

   
   // Key handling
   public static boolean leftAllowed = true;
   public static boolean rightAllowed = true;
   public static boolean upAllowed = true;
   public static boolean downAllowed = true;

   // Constant representing the direction of the player move
   public static final int LEFT = 1;
   public static final int RIGHT = 2;
   public static final int UP = 3;
   public static final int DOWN = 4;

   public static boolean running = true;
   public static Escape game;  // object containing implementation of the game logic
   
   /**
    * Starts the game by initializing systems, loading the level,
    * running the main game loop, and then cleaning up.<p>
    * 
    */
   public static void main(String[] args) {
      initWindow();


      initInput();
      initBufferStrategy();
      loadFolderImages();
   
      game = new Escape();
      
      game.initLevelGrid("level.txt");
      gameLoop();
      cleanup();
   }

   /**
    * Sets up the game window and canvas, sizes them, centers the window,
    * and makes it visible.<p>
    * 
    */
   public static void initWindow() {
      canvas = new Canvas();
      canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
   
      window = new JFrame("Escape");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setResizable(false);
      window.add(canvas);
      window.pack();
      window.setLocationRelativeTo(null);
      window.setVisible(true);
   }

   /**
    * Attaches keyboard controls to the canvas and forwards key events to the
    * game-logic handler methods.<p>
    * 
    */
   public static void initInput() {
      canvas.addKeyListener(
         new KeyAdapter() {
         
            @Override
            public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
            
               if (code == KeyEvent.VK_LEFT)  onLeftPressed();
               if (code == KeyEvent.VK_RIGHT) onRightPressed();
               if (code == KeyEvent.VK_UP)    onUpPressed();
               if (code == KeyEvent.VK_DOWN)  onDownPressed();
               if (code == KeyEvent.VK_ENTER) game.onEnterPressed();
               if (code == KeyEvent.VK_Q)     game.onQPressed();
            }
         
            @Override
            public void keyReleased(KeyEvent e) {
               int code = e.getKeyCode();
            
               if (code == KeyEvent.VK_LEFT)  onLeftReleased();
               if (code == KeyEvent.VK_RIGHT) onRightReleased();
               if (code == KeyEvent.VK_UP)    onUpReleased();
               if (code == KeyEvent.VK_DOWN)  onDownReleased();
            }
         });
   
      canvas.setFocusable(true);
      canvas.requestFocus();
   }

   /** Allows left movement again after the key is released.<p>
    * 
   */
   public static void onLeftReleased() {
      leftAllowed  = true;
   }

   /** Allows right movement again after the key is released.<p>
    * 
   */
   public static void onRightReleased() {
      rightAllowed = true;
   }

   /** Allows up movement again after the key is released.<p>
    * 
   */
   public static void onUpReleased() {
      upAllowed = true;
   }

   /** Allows down movement again after the key is released.<p>
    * 
   */
   public static void onDownReleased() {
      downAllowed  = true;
   }

   /**
    * Creates a triple-buffered drawing strategy for smooth rendering.<p>
    * 
    */
   public static void initBufferStrategy() {
      canvas.createBufferStrategy(3); // triple buffering
      bufferStrategy = canvas.getBufferStrategy();
   }

   /**
    * Auto-loads all .png files from the images folder and stores them in the image map by filename.<p>
    * 
    */
   public static void loadFolderImages() {
      File folder = new File(IMG_FOLDER);
   
      for (File f : folder.listFiles()) {
         if (f.getName().endsWith(IMG_EXTENSION)) {
            try {
               Image img = ImageIO.read(f);
               String filename = f.getName();
               int lastDotIndex = filename.lastIndexOf(".");
               String key = filename.substring(0, lastDotIndex);
               images.put(key, img);
            } catch (Exception e) {
               System.out.println("Could not load: " + f.getName());
            }
         }
      }
   }
   
   /**
    * Returns the Image object used for drawImage method in Graphics class
    * 
    * @param  name of image file without extension
    * @return the Image object
    */
   public static Image getImage(String imageName) {
      return images.get(imageName);
   }
   
   /**
    * Runs the main loop of the game, repeatedly updating and drawing
    * the world until the program is no longer running.<p>
    * 
    */
   public static void gameLoop() {
      while (running) {
         game.update();
         draw();
      
         // Simple ~60 FPS cap
         try {
            Thread.sleep(16);
         } catch (InterruptedException e) {
            // ignore
         }
      }
   }
   
   /**
    * Set up the game window.  
    * Obtain the Graphics object and pass it to the Escape object
    * for it to display the game status
    *
    */
   public static void draw() {
      Graphics g = bufferStrategy.getDrawGraphics();
           
      // Clear the screen (black background)
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, WIDTH, HEIGHT);

      game.draw(g);
      
      // Must be the last two lines of code in this method
      g.dispose();
      bufferStrategy.show();
   }
   
   /**
    * Draws a translucent overlay on top of the game view.
    * <p>
    * This is a convenience method that you may use to dim the screen during non-play states 
    * (such as start, win, or lose screens). It performs drawing only 
    * and contains no game logic.<p>
    * 
    * <b>USE, DO NOT MODIFY</b>
    *
    * @param g the graphics context used to render the overlay
    */
   public static void drawOverlay(Graphics g) {
      g.setColor(new Color(0, 0, 0, 200)); // 128 ≈ 50% opacity
      g.fillRect(0, 0, WIDTH, HEIGHT);
   }

///////////////////////////// KEY EVENT HANDLING //////////////////////////

/*
These methods are called automatically when one of the arrow keys is pressed.
*/

   /**
    * Handles the event where the left-arrow key is pressed.<p>
    * 
    */
   public static void onLeftPressed() {
      leftAllowed = false;
      game.movePlayer(LEFT);
   }

   /**
    * Handles the event where the right-arrow key is pressed.<p>
    * 
    */
   public static void onRightPressed() {
      rightAllowed = false;
      game.movePlayer(RIGHT);
   }

   /**
    * Handles the event where the up-arrow key is pressed.<p>
    * 
    */
   public static void onUpPressed() {
      upAllowed = false;
      game.movePlayer(UP);
   }

   /**
    * Handles the event where the down-arrow key is pressed.<p>
    * 
    */
   public static void onDownPressed() {
      downAllowed = false;
      game.movePlayer(DOWN);
   }
   
   /**
    * Handles the event where the down-arrow key is pressed.<p>
    * 
    */
   public static void endGame() {
      running = false;
   }

   
   ///////////////////////////// GAME CLEAN UP //////////////////////////

   /**
    * Closes the game window and releases resources.<p>
    * 
    */
   public static void cleanup() {
      window.dispose();
   }



}