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
              

    static int height = 600;
    static int width = 600;
    static int[] buttonPressed = new int[2];
    static boolean playerBlue = false;
    static boolean playerWhite = false;
    static boolean readyPlay = false;
    static Board board;
    static Player players;

    public static void main(String[] args) {
     launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

    try {
    	players = new Player(board);

    	board = new Board();
        primaryStage.setTitle("Starting Screen");

        Label label = new Label("Checkers!");
        label.setFont(new Font("Arial", 60));
        label.setLayoutX(80);

        Button button1 = new Button("Human vs Human");
        Button button2 = new Button("Human vs AI");
        Button button3 = new Button("AI vs AI");
        button1.setStyle("-fx-font-size:20;");
        button2.setStyle("-fx-font-size:20;");
        button3.setStyle("-fx-font-size:20;");
        
        button1.setPrefHeight(40);
        button1.setPrefWidth(220);
        button2.setPrefHeight(40);
        button2.setPrefWidth(220);
        button3.setPrefHeight(40);
        button3.setPrefWidth(220);


        Pane root = new Pane();
        button1.setLayoutX(140);
        button1.setLayoutY(200);
        button2.setLayoutX(140);
        button2.setLayoutY(250);
        button3.setLayoutX(140);
        button3.setLayoutY(300);


        root.getChildren().add(button1);
        root.getChildren().add(button2);
        root.getChildren().add(button3);
        root.getChildren().add(label);

        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
	            playerBlue = true;
	            playerWhite = true;
	            updateBoard(primaryStage, board);
            }
        });

        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
	            playerBlue = true;
	            playerWhite = false;
	            updateBoard(primaryStage, board);
            }
        });
        
        

        button3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
	            playerBlue = false;
	            playerWhite = false;
	            readyPlay = true;
            }
        });

        Scene scene = new Scene(root,500,500);

        primaryStage.setScene(scene);
        primaryStage.show();


        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	 if (readyPlay == true) {
            		 

        			char[][] temp = board.getPieces();
        		 
        			System.out.println("\n\n\n");
        			
        			for (int t = 0; t < 8; t++) {
        				for (int j = 0; j < 8; j++) {
        					System.out.print(" " + temp[j][t] + " ");
        				}
        				System.out.println();
        			}

                	 primaryStage.setTitle("Starting Screen");

                     GridPane grid = new GridPane();
                     
                     buttonPressed[0] = -1;
                     buttonPressed[1] = -1;
                     
                     
                     int blueScore = board.getBlueScore();
                     int whiteScore = board.getWhiteScore();

                     ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());

             		 ArrayList<Board> nextBoards = new ArrayList<Board>();    
             		 
             		 for (int i = 0; i < piecesPossible.size(); i++) {          
             		     if (board.nextBoards(piecesPossible.get(i))!= null) {
             		         for (Board b: board.nextBoards(piecesPossible.get(i))) {		
             		        	 
             		        	 nextBoards.add(b);
             		        	 
             		         }
             		     }
             		 }	 
             		 
             	if (blueScore == 0 || whiteScore == 0 || nextBoards.size() == 0) {
             			
        	            primaryStage.setTitle("Ending Screen");
        	
        	            Label label;
        	            if (blueScore < whiteScore) {
        		            label = new Label("END OF GAME! \n\n" + "White wins!");
        	            }
        	            else {
        		            label = new Label("END OF GAME! \n\n" + "Blue wins!");
        	            }
        	            
        	            label.setFont(new Font("Arial", 50));
        	            label.setLayoutX(80);

                        System.out.println("END OF GAME");
                        
                        Pane root = new Pane();
                        
                        root.getChildren().add(label);
                        
                        Scene scene = new Scene(root,500,500);

                        primaryStage.setScene(scene);
                        primaryStage.show();

             		}
             		
             		 else {
                         
                         for (int i = 0; i < 8; i++) {
                             
                             for (int j = 0; j < 8; j++) {

                                 //Creating a rectangle object         
                                 Rectangle rectangle = new Rectangle();
                                         
                                 rectangle.setHeight(height/8);
                                 rectangle.setWidth(width/8);
                                     
                                 GridPane.setRowIndex(rectangle, j);
                                 GridPane.setColumnIndex(rectangle, i);
                                 

                                 if (i % 2 == 0 && j % 2 == 0) {
                                     rectangle.setFill(Color.RED);
                                 }
                                 else if (i % 2 != 0 && j % 2 != 0) {
                                     rectangle.setFill(Color.RED);
                                 }
                                 else {
                                     rectangle.setFill(Color.BLACK);
                                 }
                                 
                                 rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

                                 	@Override
                                 	public void handle(MouseEvent event) {
                                         if (buttonPressed[0] != -1 && buttonPressed[1] != -1) {
                                             int[] target = new int[2];
                                             target[0] = GridPane.getRowIndex(rectangle);
                                             target[1] = GridPane.getColumnIndex(rectangle);
                                             board.doSwap(target, buttonPressed);
                                         }
                                     }
                                 });
                                                     
                                 grid.getChildren().add(rectangle);

                             }
                                             
                                             
                         }
                                     
                         for (int i = 0; i < 8; i++) {
                             for (int j = 0; j < 8; j++) {
                                         
                                 if (board.getPiece(i, j) == 'K') {
                     
                                     Button bt = new Button("K");
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: Blue;" +  "-fx-font-size:25;");
                                         
                                     if (playerBlue == true && board.getTurn() == 'X') {
             	                        bt.setOnAction(new EventHandler<ActionEvent>() {
             	                            @Override public void handle(ActionEvent e) {         
             	                                buttonPressed[0] = GridPane.getRowIndex(bt);
             	                                buttonPressed[1] = GridPane.getColumnIndex(bt);
             	                            }
             	                        });
                                     }
             	                            
                                     grid.add(bt, i, j);
                                 }
                                                         
                                 if (board.getPiece(i, j) == 'k') {
                                     Button bt = new Button("K");
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: White;" + "-fx-font-size:25;");
                                     
                                 if (playerWhite == true && board.getTurn() == 'x') {
                                     bt.setOnAction(new EventHandler<ActionEvent>() {
                                             @Override public void handle(ActionEvent e) {
                                                 buttonPressed[0] = GridPane.getRowIndex(bt);
                                                 buttonPressed[1] = GridPane.getColumnIndex(bt);
                                             
                                             }
                                         });
                                     }
                                     
                                     grid.add(bt, i, j);
                                 }
                                 if (board.getPiece(i, j) == 'X') {
              
                                     Button bt = new Button();
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: Blue");
                                     
                                     if (playerBlue == true && board.getTurn() == 'X') {
             	                        bt.setOnAction(new EventHandler<ActionEvent>() {
             	                            @Override public void handle(ActionEvent e) {
             	                                        
             	                                buttonPressed[0] = GridPane.getRowIndex(bt);
             	                                buttonPressed[1] = GridPane.getColumnIndex(bt);
             	
             	                            }
             	                        });
                                     }
                                     
                                     grid.add(bt, i, j);
                                                                         
                                 }
                                 if (board.getPiece(i, j) == 'x') {
                                                                         
                                     Button bt = new Button();
                                     bt.setShape(new Circle(width/8));
                                     bt.setPrefSize(80, 65);
                                     bt.setStyle("-fx-background-color: White");
                                     
                                     if (playerWhite == true && board.getTurn() == 'x') {
                                         bt.setOnAction(new EventHandler<ActionEvent>() {
                                             @Override public void handle(ActionEvent e) {
                                                         
                                                 buttonPressed[0] = GridPane.getRowIndex(bt);
                                                 buttonPressed[1] = GridPane.getColumnIndex(bt);
                     
                                             }
                                         });
                                     }

                                     grid.add(bt, i, j);
                                         
                                 }
                             }
                         }

                         System.out.println("showing scene");
                         
                         primaryStage.setScene(new Scene(grid,height,width));

                         primaryStage.show();
                         
                         if (playerWhite == false && board.getTurn() == 'x') {
                             board = players.computeWhitePlayer(board);
                         } 
                         else if (playerBlue == false && board.getTurn() == 'X') {
                             board = players.computeBluePlayer(board);
                             
                         }
                        
                         
                         try {
                         	Thread.sleep(200);
                         } catch (InterruptedException e) {
                         	e.printStackTrace();
                         }
                         
             		 }
                }
            }
				 
        }.start();


        } catch(Exception e) {
                    e.printStackTrace();
        }

    }
    
    public static void updateBoard(Stage primaryStage, Board board) {

    	final Board otherBoard = board;
    	
        primaryStage.setTitle("Starting Screen");
        
        char[][] temp = board.getPieces();

        GridPane grid = new GridPane();
        
        buttonPressed[0] = -1;
        buttonPressed[1] = -1;
        
        
        int blueScore = board.getBlueScore();
        int whiteScore = board.getWhiteScore();
        
        
        if (blueScore == 0 || whiteScore == 0 || board.getPiecesLeft('X').size() == 0 || board.getPiecesLeft('x').size() == 0) {
	            primaryStage.setTitle("Ending Screen");
	
	            Label label;
	            if (blueScore < whiteScore) {
		            label = new Label("END OF GAME! \n\n" + "White wins!");
	            }
	            else {
		            label = new Label("END OF GAME! \n\n" + "Blue wins!");
	            }
	            
	            label.setFont(new Font("Arial", 50));
	            label.setLayoutX(80);

                System.out.println("END OF GAME");
                
                Pane root = new Pane();
                
                root.getChildren().add(label);
                
                Scene scene = new Scene(root,500,500);

                primaryStage.setScene(scene);
                primaryStage.show(); 

        }
        else{
                                    
            for (int i = 0; i < 8; i++) {
                
                for (int j = 0; j < 8; j++) {

                    //Creating a rectangle object         
                    Rectangle rectangle = new Rectangle();
                            
                    rectangle.setHeight(height/8);
                    rectangle.setWidth(width/8);
                        
                    GridPane.setRowIndex(rectangle, j);
                    GridPane.setColumnIndex(rectangle, i);
                    

                    if (i % 2 == 0 && j % 2 == 0) {
                        rectangle.setFill(Color.RED);
                    }
                    else if (i % 2 != 0 && j % 2 != 0) {
                        rectangle.setFill(Color.RED);
                    }
                    else {
                        rectangle.setFill(Color.BLACK);
                    }
                    
                    rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    	@Override
                    	public void handle(MouseEvent event) {
                            if (buttonPressed[0] != -1 && buttonPressed[1] != -1) {
                                int[] target = new int[2];
                                target[0] = GridPane.getRowIndex(rectangle);
                                target[1] = GridPane.getColumnIndex(rectangle);
                                if (otherBoard.doSwap(target, buttonPressed)) {
                                    updateBoard(primaryStage, otherBoard);
                                }
                            }
                        }
                    });
                                        
                    grid.getChildren().add(rectangle);

                }
                                
                                
            }
                        
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                            
                    if (board.getPiece(i, j) == 'K') {
        
                        Button bt = new Button("K");
                        bt.setShape(new Circle(width/8));
                        bt.setPrefSize(80, 65);
                        bt.setStyle("-fx-background-color: Blue;" +  "-fx-font-size:25;");
                            
                        if (playerBlue == true && board.getTurn() == 'X') {
	                        bt.setOnAction(new EventHandler<ActionEvent>() {
	                            @Override public void handle(ActionEvent e) {         
	                                buttonPressed[0] = GridPane.getRowIndex(bt);
	                                buttonPressed[1] = GridPane.getColumnIndex(bt);
	                            }
	                        });
                        }
	                            
                        grid.add(bt, i, j);
                    }
                                            
                    if (board.getPiece(i, j) == 'k') {
                        Button bt = new Button("K");
                        bt.setShape(new Circle(width/8));
                        bt.setPrefSize(80, 65);
                        bt.setStyle("-fx-background-color: White;" + "-fx-font-size:25;");
                        
                    if (playerWhite == true && board.getTurn() == 'x') {
                        bt.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                    buttonPressed[0] = GridPane.getRowIndex(bt);
                                    buttonPressed[1] = GridPane.getColumnIndex(bt);
                                
                                }
                            });
                        }
                        
                        grid.add(bt, i, j);
                    }
                    if (board.getPiece(i, j) == 'X') {
 
                        Button bt = new Button();
                        bt.setShape(new Circle(width/8));
                        bt.setPrefSize(80, 65);
                        bt.setStyle("-fx-background-color: Blue");
                        
                        if (playerBlue == true && board.getTurn() == 'X') {
	                        bt.setOnAction(new EventHandler<ActionEvent>() {
	                            @Override public void handle(ActionEvent e) {
	                                        
	                                buttonPressed[0] = GridPane.getRowIndex(bt);
	                                buttonPressed[1] = GridPane.getColumnIndex(bt);
	
	                            }
	                        });
                        }
                        
                        grid.add(bt, i, j);
                                                            
                    }
                    if (board.getPiece(i, j) == 'x') {
                                                            
                        Button bt = new Button();
                        bt.setShape(new Circle(width/8));
                        bt.setPrefSize(80, 65);
                        bt.setStyle("-fx-background-color: White");
                        
                        if (playerWhite == true && board.getTurn() == 'x') {
                            bt.setOnAction(new EventHandler<ActionEvent>() {
                                @Override public void handle(ActionEvent e) {
                                            
                                    buttonPressed[0] = GridPane.getRowIndex(bt);
                                    buttonPressed[1] = GridPane.getColumnIndex(bt);
        
                                }
                            });
                        }

                        grid.add(bt, i, j);
                            
                    }
                }
            }
            
        	
            Scene scene = new Scene(grid,height,width);
            System.out.println("showing scene");
            primaryStage.setScene(scene);
            primaryStage.show();
            

            if (playerWhite == false && board.getTurn() == 'x') {
                board = players.computeWhitePlayer(board);
                
                updateBoard(primaryStage, board);
                
            } 
                                           
        }
                             
    }

}
