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
    static Board board = new Board();
    static int[] buttonPressed = new int[2];
    static boolean playerBlue = false;
    static boolean playerWhite = false;

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {

		try {
			
			
			//TODO IMPLEMTENT START SCREEN THAT SELECTS IF TWO HUMANS ARE PLAYING,
			//OR WHICH COLOR THER WOULD LIKE TO PLAY AGAINST THE COMPUTER
			
			
			updateBoard(primaryStage);

			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();


		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void updateBoard(Stage primaryStage) {
		primaryStage.setTitle("Starting Screen");
		
		char turn = board.getTurn();

		GridPane grid = new GridPane();
		
		buttonPressed[0] = -1;
		buttonPressed[1] = -1;
		
		int blueScore = board.getBlueScore();
		int whiteScore = board.getWhiteScore();
		
		System.out.println("Blue score: " + blueScore + " White score: " + whiteScore);
		
		if (blueScore == 0 && whiteScore == 0) {
			System.out.println("END OF GAME");
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
		        			     if (board.trySwap(target, buttonPressed)) {
		        			    	 updateBoard(primaryStage);
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
	        			
	        			bt.setOnAction(new EventHandler<ActionEvent>() {
	        			    @Override public void handle(ActionEvent e) {
	        			    	
	        			        buttonPressed[0] = GridPane.getRowIndex(bt);
	        			        buttonPressed[1] = GridPane.getColumnIndex(bt);
	        			    }
	        			});
	        			
	        			grid.add(bt, i, j);
	        		}
	        		
	        		if (board.getPiece(i, j) == 'k') {
	        			
	        			Button bt = new Button("K");
	        			bt.setShape(new Circle(width/8));
	        	        bt.setPrefSize(80, 65);
	        			bt.setStyle("-fx-background-color: White;" + "-fx-font-size:25;");
	        			
	        			bt.setOnAction(new EventHandler<ActionEvent>() {
	        			    @Override public void handle(ActionEvent e) {
	        			    	
	        			        buttonPressed[0] = GridPane.getRowIndex(bt);
	        			        buttonPressed[1] = GridPane.getColumnIndex(bt);

	        			    }
	        			});
	        			
	        			grid.add(bt, i, j);
	        		}
	        		if (board.getPiece(i, j) == 'X') {
	        			
	        			Button bt = new Button();
	        			bt.setShape(new Circle(width/8));
	        	        bt.setPrefSize(80, 65);
	        			bt.setStyle("-fx-background-color: Blue");
	        			
	        			bt.setOnAction(new EventHandler<ActionEvent>() {
	        			    @Override public void handle(ActionEvent e) {
	        			    	
	        			        buttonPressed[0] = GridPane.getRowIndex(bt);
	        			        buttonPressed[1] = GridPane.getColumnIndex(bt);

	        			    }
	        			});
	        			
	        			grid.add(bt, i, j);
	        			
	        		}
	        		if (board.getPiece(i, j) == 'x') {
	        			
	        			Button bt = new Button();
	        			bt.setShape(new Circle(width/8));
	        			bt.setPrefSize(80, 65);
	        			bt.setStyle("-fx-background-color: White");
	        			
	        			bt.setOnAction(new EventHandler<ActionEvent>() {
	        			    @Override public void handle(ActionEvent e) {
	        			    	
	        			        buttonPressed[0] = GridPane.getRowIndex(bt);
	        			        buttonPressed[1] = GridPane.getColumnIndex(bt);
	 
	        			        
	        			    }
	        			});
	        			grid.add(bt, i, j);
	        			
	        		}
	        	
	        	}
	        }

			Scene scene = new Scene(grid,height,width);

			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		
	}
	
	public static void computerPlayer() {
		
	}

}
