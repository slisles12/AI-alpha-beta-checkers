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
    static char turn;

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {

		try {
			
			turn = 'x';
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

		GridPane grid = new GridPane();
		
		buttonPressed[0] = -1;
		buttonPressed[1] = -1;
        
        
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
	        			     System.out.println(target[0] + " " + target[1]);
	        			     if (board.trySwap(target, buttonPressed, turn)) {
	        			    	 if (turn == 'x') {
	        			    		 turn = 'X';
	        			    	 }
	        			    	 else {
	        			    		 turn = 'x';
	        			    	 }
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
        		
        		if (board.getPiece(i, j) == 'Q') {
        			
        			Button bt = new Button();
        			bt.setShape(new Circle(width/8));
        	        bt.setPrefSize(80, 65);
        			bt.setStyle("-fx-background-color: Blue");
        			
        			bt.setOnAction(new EventHandler<ActionEvent>() {
        			    @Override public void handle(ActionEvent e) {
        			    	
        			        buttonPressed[0] = GridPane.getRowIndex(bt);
        			        buttonPressed[1] = GridPane.getColumnIndex(bt);
        			        
        			        System.out.println(buttonPressed[0] + " " + buttonPressed[1] + " " + 'X');
        			    }
        			});
        			
        			grid.add(bt, i, j);
        		}
        		
        		if (board.getPiece(i, j) == 'q') {
        			
        			Button bt = new Button();
        			bt.setShape(new Circle(width/8));
        	        bt.setPrefSize(80, 65);
        			bt.setStyle("-fx-background-color: Blue");
        			
        			bt.setOnAction(new EventHandler<ActionEvent>() {
        			    @Override public void handle(ActionEvent e) {
        			    	
        			        buttonPressed[0] = GridPane.getRowIndex(bt);
        			        buttonPressed[1] = GridPane.getColumnIndex(bt);
        			        
        			        System.out.println(buttonPressed[0] + " " + buttonPressed[1] + " " + 'X');
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
        			        
        			        System.out.println(buttonPressed[0] + " " + buttonPressed[1] + " " + 'X');
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
        			        
        			        System.out.println(buttonPressed[0] + " " + buttonPressed[1] + " " + 'x');
        			        
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
