import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Main extends Application {
    static int height = 600; //height of GUI
    static int width = 600; //width of GUI
    static int[] buttonPressed = new int[2]; //array of button pressed
    static boolean playerBlue = false; //human or AI player
    static boolean playerWhite = false; //human or AI player
    static boolean readyPlay = false; //variable to init AI v AI mode
    static Board board; //current board
    static Player players; //players class for AI

    /**
     * Start of program
     */
    public static void main(String[] args) {
     launch(args);
    }


	/**
	 * Try to place a piece in the grid, and then return the new board if it's successful
	 * 
	 * @param primaryStage is the stage being passed
	 * @return void
	 */
    @Override
    public void start(Stage primaryStage) {

    //start of GUI
    try {
    	//create player and board
    	players = new Player(board);
    	board = new Board();
    	
    	//set title
        primaryStage.setTitle("Starting Screen");

        //set labels 
        Label label = new Label("Checkers!");
        label.setFont(new Font("Arial", 60));
        label.setLayoutX(80);

        //buttons for modes
        Button button1 = new Button("Human vs Human");
        Button button2 = new Button("Human vs AI");
        Button button3 = new Button("AI vs AI");
        
        //button font
        button1.setStyle("-fx-font-size:20;");
        button2.setStyle("-fx-font-size:20;");
        button3.setStyle("-fx-font-size:20;");
        
        //button height and width
        button1.setPrefHeight(40);
        button1.setPrefWidth(220);
        button2.setPrefHeight(40);
        button2.setPrefWidth(220);
        button3.setPrefHeight(40);
        button3.setPrefWidth(220);


        //set position on screen
        button1.setLayoutX(140);
        button1.setLayoutY(200);
        button2.setLayoutX(140);
        button2.setLayoutY(250);
        button3.setLayoutX(140);
        button3.setLayoutY(300);

        //root pane to add onto
        Pane root = new Pane();
        
        //adding buttons
        root.getChildren().add(button1);
        root.getChildren().add(button2);
        root.getChildren().add(button3);
        root.getChildren().add(label);

        //human v human mode inited when button is pressed
        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {           	
            	//humans are both blue and white
	            playerBlue = true;
	            playerWhite = true;
	            
	            //calling method to run human v human mode
	            updateBoard(primaryStage);
            }
        });

        //human v AI mode inited when button is pressed
        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	//human is blue and AI is white
	            playerBlue = true;
	            playerWhite = false;
	            
	            //calling method to run human v human mode
	            updateBoard(primaryStage);
            }
        });
        
   
        //AI v AI mode inited when button is pressed
        button3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	//AI is blue and AI is white
	            playerBlue = false;
	            playerWhite = false;
	            
	            //variable to allow animation to take place
	            readyPlay = true;
            }
        });

        //create scene and add root
        Scene scene = new Scene(root,500,500);
        
        //set scene to primary stage
        primaryStage.setScene(scene);
        
        //show the stage
        primaryStage.show();


        //animation for AI v AI mode
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	
            	 //if we are in AI v AI mode
            	 if (readyPlay == true) {

            		 //set title
                	 primaryStage.setTitle("Game is being played");

                	 //make new grid for buttons
                     GridPane grid = new GridPane();
                     
                     //getting raw score of boards
                     int blueScore = board.getBlueScore();
                     int whiteScore = board.getWhiteScore();

                     
                     //seeing all the next possible moves
                     ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());
             		 ArrayList<Board> nextBoards = new ArrayList<Board>();    
             		 
             		 //if we have pieces to move
             		 for (int i = 0; i < piecesPossible.size(); i++) {  
             			 //if we can make moves for those pieces
             		     if (board.nextBoards(piecesPossible.get(i))!= null) {
             		    	 //for each move we can make
             		         for (Board b: board.nextBoards(piecesPossible.get(i))) {		
             		        	 //add to nextBoards
             		        	 nextBoards.add(b);
             		         }
             		     }
             		 }	 
             		 

             		 //if the game has ended
             		 if (blueScore == 0 || whiteScore == 0 || nextBoards.size() == 0) {
             			
            			 //title is end game
            			 primaryStage.setTitle("End Of Game");

            			 Label label;
            			 
            			 //if loss was because of score
            			 if (blueScore == 0 || whiteScore == 0) {
            				 //white has greater score so white wins
            				 if (blueScore < whiteScore) {
                				 label = new Label("END OF GAME! \n\n" + "White wins!");
                			 }
            				 //blue has greater score so blue wins
                			 else {
                				 System.out.println("hey");
                				 label = new Label("END OF GAME! \n\n" + "Blue wins!");
                			 }
            			 }
            			 //if loss was because of lack of moves
            			 else {
            				 //white wins because blue can't move
            				 if (board.getTurn() == 'X') {
            					 label = new Label("END OF GAME! \n\n" + "White wins!");
            				 }
            				 //blue wins because white can't move
            				 else {
            					 System.out.println("hey");
            					 label = new Label("END OF GAME! \n\n" + "Blue wins!");
            				 }
            			 }
            			 
             			 //setting style of label
             			 label.setFont(new Font("Arial", 50));
             			 label.setLayoutX(80);

             			 //make root
             			 Pane root = new Pane();
             			 
             			 //add label
             			 root.getChildren().add(label);
                        
             			 //add root to scene
             			 Scene scene = new Scene(root,500,500);

             			 //set scene on stage
             			 primaryStage.setScene(scene);
             			 primaryStage.show();

             		 }
             		
             		 //if game is not over
             		 else {
             			 
             			 //for every piece
                         for (int i = 0; i < 8; i++) {
                             for (int j = 0; j < 8; j++) {

                                 //Creating a rectangle object         
                                 Rectangle rectangle = new Rectangle();
                                        
                                 //set rectangle
                                 rectangle.setHeight(height/8);
                                 rectangle.setWidth(width/8);
                                    
                                 //add to the grid
                                 GridPane.setRowIndex(rectangle, j);
                                 GridPane.setColumnIndex(rectangle, i);
                                 

                                 //if i,j mod 2 are 0 then make it red
                                 if (i % 2 == 0 && j % 2 == 0) {
                                     rectangle.setFill(Color.RED);
                                 }
                                 // if both i and j mod 2 is not 0 make it red 
                                 else if (i % 2 != 0 && j % 2 != 0) {
                                     rectangle.setFill(Color.RED);
                                 }
                                 //else make it black
                                 else {
                                     rectangle.setFill(Color.BLACK);
                                 }

                                 grid.getChildren().add(rectangle);

                             }
                                             
                                             
                         }
                                     
                         for (int i = 0; i < 8; i++) {
                             for (int j = 0; j < 8; j++) {
                            	 //if we have a blue king
                                 if (board.getPiece(i, j) == 'K') {
                                	 //make new button for king
                                     Button bt = new Button("K");
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: Blue;" +  "-fx-font-size:25;");
                                     
                                     //add to the grid
                                     grid.add(bt, i, j);
                                 }
                                 //if we have a white king                        
                                 if (board.getPiece(i, j) == 'k') {
                                	 //make new button for king
                                     Button bt = new Button("K");
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: White;" + "-fx-font-size:25;");
                                     
                                     //add to the grid
                                     grid.add(bt, i, j);
                                 }
                                 //if we have a blue piece
                                 if (board.getPiece(i, j) == 'X') {
                                	 //make a blue button
                                     Button bt = new Button();
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: Blue");

                                     //add to the grid
                                     grid.add(bt, i, j);
                                                                         
                                 }
                                 //if we have a white piece
                                 if (board.getPiece(i, j) == 'x') {
                                	 //make a white button
                                     Button bt = new Button();
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: White");
                                     
                                     //add to the grid
                                     grid.add(bt, i, j);
                                         
                                 }
                             }
                         }

                         //add grid to the scene
                         primaryStage.setScene(new Scene(grid,height,width));

                         //show the scene
                         primaryStage.show();
                         
                         //if it is white turn
                         if (board.getTurn() == 'x') {
                        	 //do white move 
                             board = players.AlphaBeta(board, 'x');
                         } 
                         //if it is blue turn
                         else if (board.getTurn() == 'X') {
                        	 //do blue move
                             board =  players.AlphaBeta(board, 'X');
                             
                         }
                        
                         //sleepy boi to make it look like the game doesn't just end at low depths
                         try {
                         	Thread.sleep(200);
                         } catch (InterruptedException e) {
                         	e.printStackTrace();
                         }
                         
             		 }
                }
            }
				 
        }.start(); //start the animation

        //catch any GUI exceptions
        } catch(Exception e) {
                    e.printStackTrace();
        }

    }
    

	/**
	 * Try to place a piece in the grid, and then return the new board if it's successful
	 * 
	 * @param primaryStage is the stage being passed
	 * @return void
	 */
    public static void updateBoard(Stage primaryStage) {

    	//final for action handlers
    	final Board otherBoard = board;
    	
    	//set title
        primaryStage.setTitle("Starting Screen");

        //make grid
        GridPane grid = new GridPane();
        
        //checking if the human pressed a button
        buttonPressed[0] = -1;
        buttonPressed[1] = -1;
        
        //getting raw score of boards
        int blueScore = board.getBlueScore();
        int whiteScore = board.getWhiteScore();

        
        //seeing all the next possible moves
        ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());
		 ArrayList<Board> nextBoards = new ArrayList<Board>();    
		 
		 //if we have pieces to move
		 for (int i = 0; i < piecesPossible.size(); i++) {  
			 //if we can make moves for those pieces
		     if (board.nextBoards(piecesPossible.get(i))!= null) {
		    	 //for each move we can make
		         for (Board b: board.nextBoards(piecesPossible.get(i))) {		
		        	 //add to nextBoards
		        	 nextBoards.add(b);
		         }
		     }
		 }	 
		 
        
        
 		 //if the game has ended
		 if (blueScore == 0 || whiteScore == 0 || nextBoards.size() == 0) {
			
			 //title is end game
			 primaryStage.setTitle("End Of Game");

			 Label label;
			 
			 //if loss was because of score
			 if (blueScore == 0 || whiteScore == 0) {
				 //white has greater score so white wins
				 if (blueScore < whiteScore) {
    				 label = new Label("END OF GAME! \n\n" + "White wins!");
    			 }
				 //blue has greater score so blue wins
    			 else {
    				 System.out.println("hey");
    				 label = new Label("END OF GAME! \n\n" + "Blue wins!");
    			 }
			 }
			 //if loss was because of lack of moves
			 else {
				 //white wins because blue can't move
				 if (board.getTurn() == 'X') {
					 label = new Label("END OF GAME! \n\n" + "White wins!");
				 }
				 //blue wins because white can't move
				 else {
					 System.out.println("hey");
					 label = new Label("END OF GAME! \n\n" + "Blue wins!");
				 }
			 }
		
          
			 //setting style of label
			 label.setFont(new Font("Arial", 50));
			 label.setLayoutX(80);

			 //make root
			 Pane root = new Pane();
			 
			 //add label
			 root.getChildren().add(label);
           
			 //add root to scene
			 Scene scene = new Scene(root,500,500);

			 //set scene on stage
			 primaryStage.setScene(scene);
			 primaryStage.show();

		 }
		 else{
             
			 //for each piece on the board
			 for (int i = 0; i < 8; i++) {
				 for (int j = 0; j < 8; j++) {

					 //Creating a rectangle object         
					 Rectangle rectangle = new Rectangle();

                     //set rectangle
                     rectangle.setHeight(height/8);
                     rectangle.setWidth(width/8);
                        
                     //add to the grid
                     GridPane.setRowIndex(rectangle, j);
                     GridPane.setColumnIndex(rectangle, i);
                     

                     //if i,j mod 2 are 0 then make it red
                     if (i % 2 == 0 && j % 2 == 0) {
                         rectangle.setFill(Color.RED);
                     }
                     // if both i and j mod 2 is not 0 make it red 
                     else if (i % 2 != 0 && j % 2 != 0) {
                         rectangle.setFill(Color.RED);
                     }
                     //else make it black
                     else {
                         rectangle.setFill(Color.BLACK);
                     }


                     //set action 
					 rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

						 @Override
						 public void handle(MouseEvent event) {
							 //if the button has been pressed
							 if (buttonPressed[0] != -1 && buttonPressed[1] != -1) {
								 //array to send
								 int[] target = new int[2];
								 
								 //add values to array
								 target[0] = GridPane.getRowIndex(rectangle);
								 target[1] = GridPane.getColumnIndex(rectangle);
								 
								 //attempt swap
								 if (otherBoard.doSwap(target, buttonPressed)) {
									 //update board
									 updateBoard(primaryStage);
								 }
							 }
						 }
					 });
                                    
					 //add rectangle
					 grid.getChildren().add(rectangle);

				 }
                                
                                
			 }
                    
			 //for each piece
			 for (int i = 0; i < 8; i++) {
				 for (int j = 0; j < 8; j++) {
                        
					 //if piece is a blue king
					 if (board.getPiece(i, j) == 'K') {
						 //make new button for king
						 Button bt = new Button("K");
						 bt.setShape(new Circle(width/8));
						 bt.setPrefSize(80, 65);
						 bt.setStyle("-fx-background-color: Blue;" +  "-fx-font-size:25;");
                            
						 //if it is blue's turn and blue is human
						 if (playerBlue == true && board.getTurn() == 'X') {
							 bt.setOnAction(new EventHandler<ActionEvent>() {
								 @Override public void handle(ActionEvent e) { 
									 //set button pressed to be the values in the gridPane
									 buttonPressed[0] = GridPane.getRowIndex(bt);
									 buttonPressed[1] = GridPane.getColumnIndex(bt);
								 }
							 });
						 }
	                         
						 //add to the grid
						 grid.add(bt, i, j);
					 } 
					 //if we have a white king
					 if (board.getPiece(i, j) == 'k') {
						 //make button for king
						 Button bt = new Button("K");
						 bt.setShape(new Circle(width/8));
						 bt.setPrefSize(80, 65);
						 bt.setStyle("-fx-background-color: White;" + "-fx-font-size:25;");
                        
						 //if it is white's turn and white is human
						 if (playerWhite == true && board.getTurn() == 'x') {
							 bt.setOnAction(new EventHandler<ActionEvent>() {
								 @Override public void handle(ActionEvent e) {
									 //set values of button pressed
									 buttonPressed[0] = GridPane.getRowIndex(bt);
									 buttonPressed[1] = GridPane.getColumnIndex(bt);
                                
								 }
							 });
						 }
                        
						 //add button to grid
						 grid.add(bt, i, j);
					 }
					 //if piece is blue
					 if (board.getPiece(i, j) == 'X') {
						 //create button for blue piece
						 Button bt = new Button();
						 bt.setShape(new Circle(width/8));
						 bt.setPrefSize(80, 65);
						 bt.setStyle("-fx-background-color: Blue");
                        
						 //if blue's turn and blue is human
						 if (playerBlue == true && board.getTurn() == 'X') {
							 bt.setOnAction(new EventHandler<ActionEvent>() {
								 @Override public void handle(ActionEvent e) {
									 //set values for button pressed
									 buttonPressed[0] = GridPane.getRowIndex(bt);
									 buttonPressed[1] = GridPane.getColumnIndex(bt);
	
								 }
							 });
						 }
						 
						 //add button to grid
						 grid.add(bt, i, j);                          
					 }
					 //if white piece
					 if (board.getPiece(i, j) == 'x') {
						 //create white button
						 Button bt = new Button();
						 bt.setShape(new Circle(width/8));
						 bt.setPrefSize(80, 65);
						 bt.setStyle("-fx-background-color: White");
                        
						 //if white's turn and white is human
						 if (playerWhite == true && board.getTurn() == 'x') {
							 bt.setOnAction(new EventHandler<ActionEvent>() {
								 @Override public void handle(ActionEvent e) {
                                     //set button pressed values
									 buttonPressed[0] = GridPane.getRowIndex(bt);
									 buttonPressed[1] = GridPane.getColumnIndex(bt);
        
								 }
							 });
						 }

						 //add button to grid
						 grid.add(bt, i, j);
                            
					 }
				 }
			 }
            
        	//add grid to scene
            Scene scene = new Scene(grid,height,width);
            
            //set and show scene
            primaryStage.setScene(scene);
            primaryStage.show();
            
            //if white is AI and is white's turn
            if (playerWhite == false && board.getTurn() == 'x') {
            	//do the next board
                board = players.AlphaBeta(board, 'x');
                //update the board
                updateBoard(primaryStage);
                
            } 
                                           
        }
                             
    }

}
