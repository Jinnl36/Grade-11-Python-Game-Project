/** 
	File Name: Escape.java
	Name: Jiayan Ma
	Class: ICS3U1-24
	Date: May to June 2026
	Description: This program is the final culminating for AY Jackson grade 11.
   Creates a game that eats cookies and escapes.

   PROGRAM JOURNAL:
   MAY 19
      Created file draw bg
      Finished STEP 1
   MAY 20
      Created reader read level.txt
      Finished STEP 2
   MAY 21
      Created array to read level.txt into array
      Created timer
      Finished STEP 3 & 4
   MAY 22
      Created map draw system
      Created extract player info 
      Finished STEP 5 & 6
   MAY 25
      Took in feedback, changed player x/y into player row/col respectively
      Deleted program headers in middle of codea
      Deleted constant PICTURE_DIMENTIONS
      Create updatePlayerPos()
      Create outputArray ()
      Player block is now able to move udlr
      Finished STEP 7
   MAY 26
      Took in feedback, moved original updatePlayerPos to initLevelGrid
      Took in feedback, now after every move, player row/col is changed instead of recalling updatePlayerPos
      Reconstructed updatePlayerPos into findChar, more functions now, instead of findeing only char p in map_arr
         now it can find ANY char any amount in any 2D char array
      Created changePosChar, changes two char places in a 2d char array
      Created generateCookie() it generates a cookie!
      Added generateCookie() onto bottom of initlevelgrid()
      Changed draw to make it draw cookie
      Almosst finished the eating cookie, still has devastating bugs
      Finished STEP 8
   MAY 27
      Added in overlay if game has not started
      Added if loop in udrl, if not game in start stat, udlr cant be read and ran
      Added drawing of the string PRESS ENTER TO BEGIN GAME...
      Changed font to 70 bc i think it looks better
      Bored so added charachter alive status and cookie amount
      Player_alive is true initialy
      Cookie_eated is 0 initialy
      Finished STEP 9
   QUICK NOTE: JAVA PRINTS ENTIRE MAP ARRAY EVERYTIME PLAYER MOVED

   MAY 28
      Created cookieEaten
      Created deleteReplace
      Created canMove
      Created outputPlayerPos DEBUG
      Added new state: end 
         Note: the dead state would be called dead

      Split changeCharPos
      Still working on reconstructing entire code, finding redundant code and solving
      Reconstructed the player move thingy, now it looks simpler than before
      Finished STEP 10, 11
   
   MAY 29
      Started on Time, Bug: time limit is incorrect, the time is either to long or too short
      Created the checkTime, still debuging
   JUN 1
      Created variables to locate enemy 1 and 2 and created system to locate 1 and 2
      Created variables for 1 and 2 to decide direction moving and types of enemy
   JUN 2 
      Created locateAllEnemy
      Created enemyMoveDirectioninit
      Creating moveEnemy
      Changed time method to make more reasonable
      Not finished step 14, still working on it
   JUN 3
      Finished all methods that I have created yesterday
      Reconstructed
      Finished step 14
   JUN 4
      Changed draw to draw death if game finished
      Tweaked enemy move to make it handle if player hits it
      Finished step 15
   JUN 5
      Changed press q, quits game instantly
      Changed enter pressed, if pressed in end or game over, calls init and restarts game
      Finished step 16
   JUN 8
      Created findCharNum finds the amount of char in an array of chars
      Changed draw to make it calculate where the drawing should be
      Changed levelinit to make it calculate length and height of drawing
   JUN 9 
      Tried to create Dynamic map


*/
/**
 * Escape.java (STARTER CODE)
 * - implementation of the game logic
 *
 */
 
//import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.*;

public class Escape {

   // Create global constants / variables HERE
   boolean player_alive;
   String input, state, stringCookie_Eaten;
   int length, height, area, count, player_row, player_col, cookie_eaten, enemy1amount, enemy2amount, map_drawing_length, map_drawing_height;
   long startTime, currentTime, elapsedTime, lastEnemyMoveTime;
   double time_limit;
   char [] [] map_arr;
   String [] enemy1DirectionArr;
   String [] enemy2DirectionArr;
   int [] enemy1RowArr;
   int [] enemy1ColArr;
   int [] enemy2RowArr;
   int [] enemy2ColArr;

   //Created variables above on may 21st
   public final double TIME_MULTIPLICATION_FACTOR = 0.05;
   public final String GAME_STATE = "game";
   public final String START_STATE = "start";
   public final String END_STATE = "end";
   public final String GAME_OVER_STATE = "game_over";
   public final String DIRECTION_RIGHT = "right";
   public final String DIRECTION_LEFT = "left";
   public final String DIRECTION_UP = "up";
   public final String DIRECTION_DOWN = "down";
   
///////////////////////// LEVEL INITIALIZATION ////////////////////
   
   /**
    * Loads a level from the given file populating the <code>levelGrid</code> by doing the following:<p>
    * 
    * 1. Determining the dimensions of the text file level.<p>
    * 2. Setting <code>levelGrid</code> to the correct dimensions.<p>
    * 3. Filling <code>levelGrid</code> with the chars from the text file.
    * @param filename
    * 
    */
   public void initLevelGrid(String filename) {
   
      //From here
      String fileName = filename;
      enemy1amount = 0;
      enemy2amount = 0;
      cookie_eaten = 0;
      player_alive = true;
      input = " "; 
      length = 0;
      height = 0;
      count = 0;
      state = START_STATE; 
      try{ 
         //deciding the dimentions
         BufferedReader reader = new BufferedReader(new FileReader(fileName));
         input = reader.readLine();
         do {
            height ++;
            //System.out.println(input);//DEBUG TEST, DELETE BEFOR SUBMITION
            length = Math.max(length, input.length());
            input = reader.readLine();
            
            
         } while (input != null);
         //System.out.println(input);//DEBUG TEST, DELETE BEFOR SUBMITION
         reader.close();//close reader, create new to restart from front
         //dimentions finished finding, creating 2d array for map

         //re-reading file, creating array
         map_arr = new char [height] [length];
         
         
         //create new reader to re-read from front to enter info into char array for map creation
         BufferedReader reader1 = new BufferedReader(new FileReader(fileName));
         //printing height and length variables
         //System.out.println(height + " " + length);//DEBUG TEST, DELETE BEFOR SUBMITION
         

         do {
            //reads line BEFORE everything happens
            input = reader1.readLine();
            //System.out.println(input);//DEBUG TEST, DELETE BEFOR SUBMITION
            //failsafe in case file ends
            if (input != null){
            //enter info loop
            for (int j = 0; j < input.length(); j ++) {
               map_arr [count] [j] = input.charAt(j);//Fixed logic on may 21 at home, before, 
            }                                        // was not able to read file and kept returning 
                                                     // java.lang.NullPointerException
         }
            //count to decide which row we are on
            count ++;
            
         } while (input != null);
      } catch (IOException iox){
         System.out.print("FILE NOT FOUND: " + fileName);
      }
      map_drawing_height = map_arr.length * EscapeGUI.TILE_HEIGHT;
      map_drawing_length = map_arr[0].length * EscapeGUI.TILE_HEIGHT;

      //to here, done on may 21st in class

      // time calculation
      area = length * height;
      time_limit = Math.ceil(area * TIME_MULTIPLICATION_FACTOR) * 1000;
      //System.out.println(time_limit);
      
      //updates player pos in beginning
      // every other move shall just change variables instead of recalling this method
      int [] temp_playerpos_arr = new int[2];
      temp_playerpos_arr = findChar(map_arr, 'p', 1);
      player_col = temp_playerpos_arr [0];
      player_row = temp_playerpos_arr [1];

      //finding enemy 1 and 2 amount
      for (int i = 0; i < height; i ++) {
         for (int j = 0; j < length; j ++) {
            if (map_arr [i] [j] == '1') {
               enemy1amount ++;
            } else if (map_arr [i] [j] == '2') {
               enemy2amount ++;
            }
         }
      }
      
      System.out.println(enemy1amount);
      System.out.println(enemy2amount);
      enemy1RowArr = new int[enemy1amount];
      enemy1ColArr = new int[enemy1amount];
      enemy2RowArr = new int[enemy2amount];
      enemy2ColArr = new int[enemy2amount];
      enemy1DirectionArr = new String [enemy1amount];
      enemy2DirectionArr = new String [enemy2amount];

      //locate enemy
      locateAllEnemy();

      //initilize move direction
      enemyMoveDirectionInit();
      
      //Cookie init
      generateCookie();
      //generateCookie(8,4);

      
      
      
   }


///////////////////////// GAME LOGIC UPDATES ////////////////////

   /**
    * Handles all game logic updates for the current frame.
    * <p>
    * This method should contain logic only; it must not perform any drawing.<p>
    * 
    */
   public void update() {
      int [] temp_arr = new int[2];
      if (state.equalsIgnoreCase(GAME_STATE)){
         checkTime();
      }
      if (state.equalsIgnoreCase(GAME_STATE)){
         
         checkTime();
         enemyMove(); // ADD THIS HERE so it runs continuously!
      }
      locateAllEnemy();
  

      
      
   }
   // you may add helper methods below

///////////////////////////// DRAWING //////////////////////////

   /**
    * Renders all visual elements of the game for the current frame.
    * <p>
    * This method is responsible only for drawing and must not contain game logic.<p>
    * @param g
    */
   public void draw(Graphics g) {  
      //screen height - title = 620
      //screen width = 1280
      int spacing_height = (EscapeGUI.HEIGHT - EscapeGUI.HEADING_HEIGHT - map_drawing_height)/2 + 100;
      int spacing_length = (EscapeGUI.WIDTH - map_drawing_length)/2;
      System.out.printf("%d%n%d%n", spacing_height, spacing_length);
      //System.out.println("drawing background");
      g.drawImage(EscapeGUI.getImage("bg"), 0, 0, null);   
      stringCookie_Eaten = String.valueOf(cookie_eaten);
      String string_timer = String.valueOf(Math.ceil((time_limit - elapsedTime)/100)/10);
      g.setColor(Color.WHITE);
      g.setFont(new Font("Consolas", Font.BOLD, 70));
      //draw timer and cookie_eaten on screen
      g.drawString(stringCookie_Eaten, 1080, 68);
      g.drawString(string_timer, 400, 68);
      for (int i = 0; i < height; i ++) {
         for (int j = 0; j < length; j ++) {
            if (map_arr [i] [j] == 'x') {
              g.drawImage(EscapeGUI.getImage("wall"), EscapeGUI.TILE_HEIGHT * j + spacing_length, i * EscapeGUI.TILE_HEIGHT  + spacing_height, null);
            } else if (map_arr [i] [j] == 'p') {
               if (state.equalsIgnoreCase(GAME_OVER_STATE)) {
                  g.drawImage(EscapeGUI.getImage("death"), EscapeGUI.TILE_HEIGHT * j + spacing_length, EscapeGUI.TILE_HEIGHT * i  + spacing_height, null);
               } else{
                  g.drawImage(EscapeGUI.getImage("player"), EscapeGUI.TILE_HEIGHT * j + spacing_length, EscapeGUI.TILE_HEIGHT * i  + spacing_height, null);
               }
            } else if (map_arr [i] [j] == '1' || map_arr [i] [j] == '2') {
               g.drawImage(EscapeGUI.getImage("enemy"), EscapeGUI.TILE_HEIGHT * j + spacing_length, EscapeGUI.TILE_HEIGHT * i  + spacing_height, null);
            } else if (map_arr [i] [j] == 'e') {
               if (cookie_eaten >= 5) {
                  g.drawImage(EscapeGUI.getImage("checkered"), EscapeGUI.TILE_HEIGHT * j + spacing_length, EscapeGUI.TILE_HEIGHT * i  + spacing_height, null);
               } else {
                  g.drawImage(EscapeGUI.getImage("jail"), EscapeGUI.TILE_HEIGHT * j + spacing_length, EscapeGUI.TILE_HEIGHT * i  + spacing_height, null);
               }
            } else if (map_arr[i] [j] == 'c') {
               g.drawImage(EscapeGUI.getImage("cookie"), EscapeGUI.TILE_HEIGHT * j + spacing_length, EscapeGUI.TILE_HEIGHT * i  + spacing_height, null);
            }

         }
      }
      //if game in start state, draw overlay
      if (state.equalsIgnoreCase(START_STATE)) {

         EscapeGUI.drawOverlay(g);
         g.setColor(Color.WHITE);
         g.setFont(new Font("Consolas", Font.BOLD, 70));
         g.drawString("PRESS ENTER TO BEGIN GAME...", 10, 700);
      } else if (state.equalsIgnoreCase(END_STATE)) {
         EscapeGUI.drawOverlay(g);
         g.setColor(Color.WHITE);
         g.setFont(new Font("Consolas", Font.BOLD, 70));
         g.drawString("YOU WON!!!", 10, 630);
         g.drawString("PRESS ENTER TO RESTART GAME...", 10, 700);
      } else if (state.equalsIgnoreCase(GAME_OVER_STATE)) {
         EscapeGUI.drawOverlay(g);
         g.setColor(Color.WHITE);
         g.setFont(new Font("Consolas", Font.BOLD, 70));
         g.drawString("YOU LOSE!!!", 10, 630);
         g.drawString("PRESS ENTER TO RESTART GAME...", 10, 700);
      }

      
      
      
      
   }
   
   /**
    * Handles the event where the Enter/Return key is pressed.<p>
    * 
    */
   public void onEnterPressed() {
      if (state.equalsIgnoreCase(START_STATE)) {
         state = GAME_STATE;
         startTime = System.currentTimeMillis();
         lastEnemyMoveTime = System.currentTimeMillis();
      } else if (state.equalsIgnoreCase(GAME_OVER_STATE) || state.equalsIgnoreCase(END_STATE)) {
         initLevelGrid("level.txt");
      }
      
   }

   /**
    * Handles the event where the 'q' key is pressed.<p>
    * 
    */
   public void onQPressed() {
      //end game
      EscapeGUI.endGame();
   }

   /**
    * Handles player movement based on the given grid offsets.
    *
    * @direction direction of the movement as defined in constants
    * 
    */
   public void movePlayer(int direction) {
      //if game didnt start, dont let player do anything
      if (state.equalsIgnoreCase(GAME_STATE)) {
         char temp;
         //First level of switch checks direction of input
         switch (direction) {
            case EscapeGUI.LEFT:
               //Second level of ifs checks if can move
               if (canMove (map_arr, player_col, player_row - 1)) {
                  changePosChar(map_arr, player_col, player_row - 1, player_col, player_row);
                  outputArray();
               }
               break;
            case EscapeGUI.RIGHT:
               if (canMove (map_arr, player_col, player_row + 1)) {
                  changePosChar(map_arr, player_col, player_row + 1, player_col, player_row);
                  outputArray();
               }
               break;
            case EscapeGUI.UP:
               if (canMove (map_arr, player_col - 1, player_row)) {
                  changePosChar(map_arr, player_col - 1, player_row, player_col, player_row);
                  outputArray();
               }
               break;
            case EscapeGUI.DOWN:
               if (canMove (map_arr, player_col + 1, player_row)) {
                  changePosChar(map_arr, player_col + 1, player_row, player_col, player_row);
                  outputArray();
               }
               break;
            default:
               System.out.print("Something went wrong on line 282");
         }
      }
   }

   public int [] findChar (char [] [] array, char target, int target_num) {
      int [] xyOfTarget = {-1, -1};
      int count = 0;
      for (int i = 0; i < height ; i ++) {
         for (int j = 0; j < length; j ++) {
            if (array [i] [j] == target) {
               count ++;
               if (count == target_num) {
                  xyOfTarget [0] = i;
                  xyOfTarget [1] = j;
                  //System.out.print(xyOfTarget [0] + " " + xyOfTarget [1]);//Debugging Line DELETE BEFORE SUBMITION
               }
            }
         }
      }
      //System.out.println(xyOfTarget [0] + " " + xyOfTarget [1]);
      //System.out.println(xyOfTarget [0] + " " + xyOfTarget [1]);
      return xyOfTarget;
      
   } 

   public int findCharNum(char [] [] array, char target) {
      int count = 0;
      for (int i = 0; i < height; i ++) {
         for (int j = 0; j < length; j ++) {
            if (map_arr [i][j] == target) {
               count ++;
            }
         }
      }
      return count;
   }

   public void generateCookie() {
      int numoftile = 0;
      int min = 1;
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < length; j++){
            if (map_arr [i] [j] == '_') {
               numoftile ++;
            }
         }
      }
      // Formula for a random integer between min (inclusive) and max (inclusive):
      int grid_changed = (int)(Math.random() * (numoftile - min + 1)) + min;
      //System.out.println(grid_changed);
      map_arr [findChar(map_arr, '_', grid_changed)[0]] [findChar(map_arr, '_', grid_changed)[1]] = 'c';
      outputArray();

   }
   public void generateCookie(int col, int row) {
      map_arr [col] [row] = 'c';
      outputArray();

   }
   public void changePosChar(char [] [] array, int target1col, int target1row, int target2col, int target2row) {
      char temp;
      temp = array [target2col] [target2row];
      array [target2col] [target2row] = array [target1col] [target1row];
      array [target1col] [target1row] = temp;

      cookieEaten(array, target1col, target1row, target2col, target2row);
   }
   public void cookieEaten(char [] [] array, int target1col, int target1row, int target2col, int target2row) {
      if (array [target1col] [target1row] == 'p') {
         player_col = target1col;
         player_row = target1row;
      } else if (array [target2col] [target2row] == 'p') {
         player_col = target2col;
         player_row = target2row;
      }
      deleteReplace(array, target2col, target2row);
      outputArray();
   }
   public void deleteReplace (char [] [] array, int targetcol, int targetrow) {
      if (array [targetcol] [targetrow] == 'c'){
         array [targetcol] [targetrow] = '_';
         cookie_eaten += 1;
         generateCookie();
         System.out.println ("DELETEREPLACE REPLACED COOKIE");
      } else if (array [targetcol] [targetrow] == 'e' && cookie_eaten >= 5) {
         state = END_STATE;
         array [targetcol] [targetrow] = '_';
         System.out.println ("DELETEREPLACE REPLACED");
   }
   }
   public boolean canMove (char [] [] array, int target_col, int target_row) {
      if (array [target_col] [target_row] == '_') {
         //System.out.println(true);
         return true;
      } else if (array [target_col] [target_row] == 'c') {
         //System.out.println(true);
         return true;
      } else if (array [target_col] [target_row] == 'e' && cookie_eaten >= 5){
         //System.out.println(true);
         return true;
      } else if (array [target_col] [target_row] == '1' || array [target_col] [target_row] == '2') {
         state = GAME_OVER_STATE;
         return false;
      } else {
         //System.out.println(false);
         return false;
      }
   }
   public void locateAllEnemy () {
      int [] tempPlayerPosArr = new int[2];
      //recording location of enemy 1
      for (int i = 0; i < enemy1amount; i ++) {
         tempPlayerPosArr = findChar(map_arr, '1', i + 1);
         enemy1RowArr [i] = tempPlayerPosArr [0];
         enemy1ColArr [i] = tempPlayerPosArr [1];
      }

      //recording location of enemy 2
      for (int i = 0; i < enemy2amount; i ++) {
         tempPlayerPosArr = findChar(map_arr, '2', i + 1);
         enemy2RowArr [i] = tempPlayerPosArr [0];
         enemy2ColArr [i] = tempPlayerPosArr [1];
      }
   }
   public void enemyMove () {
      currentTime = System.currentTimeMillis();
         if (currentTime - lastEnemyMoveTime >= 250) {
            for (int i = 0; i < enemy1amount; i++) {
               if (enemy1DirectionArr[i].equals(DIRECTION_LEFT)) {
                  if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] - 1] == '_') {
                     // Passed in backwards to satisfy your changePosChar method!
                     changePosChar(map_arr, enemy1RowArr[i], enemy1ColArr[i], enemy1RowArr[i], enemy1ColArr[i] - 1);
                  } else if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] - 1] == 'x' || map_arr[enemy1RowArr[i]][enemy1ColArr[i] - 1] == '1' || map_arr[enemy1RowArr[i]][enemy1ColArr[i] - 1] == '2') {
                     enemy1DirectionArr[i] = DIRECTION_RIGHT;
                  } else if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] - 1] == 'p') {
                     
                     state = GAME_OVER_STATE;
                  } else if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] - 1] == 'c') {
                        System.out.print("ran cookie");
                        //deleteReplace(map_arr, enemy1ColArr[i] - 1, enemy1RowArr[i]);
                        deleteReplace(map_arr, enemy1RowArr[i], enemy1ColArr[i] - 1);
                        cookie_eaten -= 1;
                  }
               } else if (enemy1DirectionArr[i].equals(DIRECTION_RIGHT)) {
                  if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] + 1] == '_') {
                     // Passed in backwards!
                     changePosChar(map_arr, enemy1RowArr[i], enemy1ColArr[i], enemy1RowArr[i], enemy1ColArr[i] + 1);
                  } else if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] + 1] == 'x' || map_arr[enemy1RowArr[i]][enemy1ColArr[i] + 1] == '1' || map_arr[enemy1RowArr[i]][enemy1ColArr[i] + 1] == '2') {
                     enemy1DirectionArr[i] = DIRECTION_LEFT;
                  } else if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] + 1] == 'p') {
                     state = GAME_OVER_STATE;
                  } else if (map_arr[enemy1RowArr[i]][enemy1ColArr[i] + 1] == 'c') {
                        System.out.print("ran cookie");
                        //deleteReplace(map_arr, enemy1ColArr[i] + 1, enemy1RowArr[i]);
                        deleteReplace(map_arr,  enemy1RowArr[i], enemy1ColArr[i] + 1);
                        cookie_eaten -= 1;
                  }
               }
            }
            for (int i = 0; i < enemy2amount; i++) {
                  // USING ENEMY 2 VARIABLES NOW
                  if (enemy2DirectionArr[i].equals(DIRECTION_UP)) {
                     if (map_arr[enemy2RowArr[i] - 1][enemy2ColArr[i]] == '_') {
                        // Passed in backwards!
                        changePosChar(map_arr, enemy2RowArr[i], enemy2ColArr[i], enemy2RowArr[i] - 1, enemy2ColArr[i]);
                     } else if (map_arr[enemy2RowArr[i] - 1][enemy2ColArr[i]] == 'x' || map_arr[enemy2RowArr[i] - 1][enemy2ColArr[i]] == '1' || map_arr[enemy2RowArr[i] - 1][enemy2ColArr[i]] == '2') {
                        enemy2DirectionArr[i] = DIRECTION_DOWN;
                     } else if (map_arr[enemy2RowArr[i] - 1][enemy2ColArr[i]] == 'p') {
                        state = GAME_OVER_STATE;
                     } else if (map_arr[enemy2RowArr[i] - 1][enemy2ColArr[i]] == 'c') {
                        System.out.print("ran cookie");
                        //deleteReplace(map_arr, enemy1ColArr[i], enemy1RowArr[i] - 1);
                        deleteReplace(map_arr, enemy2RowArr[i] - 1, enemy2ColArr[i]);
                        cookie_eaten -= 1;
                     }
                  } else if (enemy2DirectionArr[i].equals(DIRECTION_DOWN)) {
                     if (map_arr[enemy2RowArr[i] + 1][enemy2ColArr[i]] == '_') {
                        // Passed in backwards!
                        changePosChar(map_arr, enemy2RowArr[i], enemy2ColArr[i], enemy2RowArr[i] + 1, enemy2ColArr[i]);
                     } else if (map_arr[enemy2RowArr[i] + 1][enemy2ColArr[i]] == 'x' || map_arr[enemy2RowArr[i] + 1][enemy2ColArr[i]] == '1' || map_arr[enemy2RowArr[i] + 1][enemy2ColArr[i]] == '2') {
                        enemy2DirectionArr[i] = DIRECTION_UP;
                     } else if (map_arr[enemy2RowArr[i] + 1][enemy2ColArr[i]] == 'p') {
                        state = GAME_OVER_STATE;
                     } else if (map_arr[enemy2RowArr[i] + 1][enemy2ColArr[i]] == 'c') {
                        System.out.print("ran cookie");
                        //deleteReplace(map_arr, enemy1ColArr[i], enemy1RowArr[i] + 1);
                        deleteReplace(map_arr, enemy2RowArr[i] + 1, enemy2ColArr[i]);
                        cookie_eaten -= 1;
                     }
                  }
            }
            lastEnemyMoveTime = currentTime;
         }
         
      }
   public void enemyMoveDirectionInit () {
      for (int i = 0; i < enemy1amount; i ++) {
         if (map_arr [enemy1RowArr [i]] [enemy1ColArr [i] + 1] == '_') {
            enemy1DirectionArr [i] = DIRECTION_RIGHT;
         } else if (map_arr [enemy1RowArr [i]] [enemy1ColArr [i] - 1] == '_') {
            enemy1DirectionArr [i] = DIRECTION_LEFT;
         }
      }

      for (int i = 0; i < enemy2amount; i ++) {
         if (map_arr [enemy2RowArr [i] - 1] [enemy2ColArr [i]] == '_') {
            enemy2DirectionArr [i] = DIRECTION_UP;
         } else if (map_arr [enemy2RowArr [i] + 1] [enemy2ColArr [i]] == '_') {
            enemy2DirectionArr [i] = DIRECTION_DOWN;
         }
      }
   }
   public void checkTime () {
      long current_time = System.currentTimeMillis();
      elapsedTime = current_time - startTime; 
      if (time_limit - elapsedTime <= 0){
         state = GAME_OVER_STATE;
      }

   }
   //DEBUG METHODS
   // Method Name: outputPlayerPos()
   // I/O: nothing
   // Use: Prints player pos in variables for debugging
   public void outputPlayerPos() {
      System.out.println(player_col);
      System.out.println(player_row);
   }
   public void outputArray() {
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < length; j++){
            //System.out.print(map_arr[i][j] + " ");
         }
            //System.out.print("\n");
      }
   }
}
