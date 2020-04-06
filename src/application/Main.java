package application;
	
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;




public class Main extends Application {
	
	/**
	 * class that starts running
	 * 
	 */
	public static class Logic implements Runnable {
		
		//list of all pieces
		List<char[][]> listOfPieces = new ArrayList<char[][]>();

		@Override
		public void run() {

			//filling a list of all pieces
			listOfPieces.add(pieces.get("O"));
			listOfPieces.add(pieces.get("O"));
			listOfPieces.add(pieces.get("O"));
			listOfPieces.add(pieces.get("O"));
			listOfPieces.add(pieces.get("L1"));
			listOfPieces.add(pieces.get("L2"));
			listOfPieces.add(pieces.get("L3"));
			listOfPieces.add(pieces.get("L4"));
			listOfPieces.add(pieces.get("S1"));
			listOfPieces.add(pieces.get("S2"));
			listOfPieces.add(pieces.get("S3"));
			listOfPieces.add(pieces.get("S4"));
			listOfPieces.add(pieces.get("J1"));
			listOfPieces.add(pieces.get("J2"));
			listOfPieces.add(pieces.get("J3"));
			listOfPieces.add(pieces.get("J4"));
			listOfPieces.add(pieces.get("Z1"));
			listOfPieces.add(pieces.get("Z2"));
			listOfPieces.add(pieces.get("Z3"));
			listOfPieces.add(pieces.get("Z4"));
			listOfPieces.add(pieces.get("I1"));
			listOfPieces.add(pieces.get("I2"));
			listOfPieces.add(pieces.get("I1"));
			listOfPieces.add(pieces.get("I2"));
			listOfPieces.add(pieces.get("T1"));
			listOfPieces.add(pieces.get("T2"));
			listOfPieces.add(pieces.get("T3"));
			listOfPieces.add(pieces.get("T4"));
			
			//call backtrack
			boolean success = backTrack(0);
			
		}

		/**
		 * Try to place a piece in the grid, and then return the new board if it's successful
		 * 
		 * @param ret is the new grid we got
		 * @return if it is full --> true
		 */
		public boolean isFull(char[][] ret) {
			//check if the board is full
			for (int i = 0; i < numBlocksX; i++) {
				for (int j = 0; j < numBlocksY; j++) {
					if (ret[i][j] == 'N') {
						return false;
					}
				}
			}
			
			//return that the board is full
			return true;

		}
		
		/**
		 * Try to place a piece in the grid, and then return the new board if it's successful
		 * 
		 * @param int pieceOn is the current piece number we are on
		 * @return The updated grid if the pieces fit onto it
		 */
		public boolean backTrack(int pieceOn) {
			
		// create instance of Random class 
	        Random rand = new Random(); 
			try {
				//sleep to make it not crazy
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//save old grid
			char[][]oldGrid = new char[numBlocksX][numBlocksY];
			
			//deep copy
			for(int i = 0; i < numBlocksX; i++) {
				for(int j = 0; j < numBlocksY; j++) {
					oldGrid[i][j] = blockGrid[i][j];
				}
			}
			
			//booleans for checking
			boolean success = false;
			boolean isDone = false;
			
			int numTried = 0;

			Collections.shuffle(listOfPieces, new Random());
			
			//for all x pos
			for(int i = 0; i < numBlocksX; i++) {
				//for all y pos
				for(int j = 0; j < numBlocksY; j++) {
					//for each rotation
					for (int z = 0; z < 27; z++) {
						//check if full
						if (isFull(oldGrid)) {
							//if full return and print solved
							System.out.println("solved");
							return true;
						}
						char[][] piece = listOfPieces.get(z);
						//get the success of the attempt to place piece
						success = tryPut(i, j, piece);
						//if we can place piece
						if (success) {
					
							if (checkWorked(oldGrid)) {
								//rotate piece
								numTried++;
								
								//backtrack
								isDone = backTrack((pieceOn+1));
								//if returned true
								if (isDone == true) {
									//return
									return isDone;
								}
								
								if (numTried >= 27) {
									return false;
								}
								
							}
							else {
								return false;
							}
						}
						//else
						else {
							//return to our old grid
							for(int w = 0; w < numBlocksX; w++) {
								for(int t = 0; t < numBlocksY; t++) {
									blockGrid[w][t] = oldGrid[w][t];
								}
							}
						}
					}
				}
			}
			//return not possible
			return false;
		}
		
	}
	
	public static boolean checkWorked(char[][] ret) {
		//for each x pos
		for (int i = 0; i < numBlocksX; i++) {
			//for each y pos
			for (int j = 0; j < numBlocksY; j++) {
				//if we are at an empty spot
				if (ret[i][j] == 'N') {
					//array for numNeighbors
					boolean[][] marked = new boolean[numBlocksX][numBlocksY];
					//find numNeighbors
					int numNeighbors = findNeighbors(i, j, marked, ret);
					//if numNeighbors is less than four or not a multiple of 4
					
					if ((numNeighbors < 4) || (numNeighbors % 4 != 0)) {
						//return false
						return false;
					}

				}
			}
		}
		
		return true;
	}
	
	
	int blockSize = 20;
	static int numBlocksX = 20;
	static int numBlocksY = 20;
	static char[][] blockGrid;
	static Map<Character, javafx.scene.paint.Color> pieceColors;
	static Map<String, char[][]> pieces;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("hey");
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			Scene scene = new Scene(grid,blockSize*numBlocksX,blockSize*numBlocksY);
			Logic l = new Logic();
			Thread runner = new Thread(l);
			runner.start();
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//TODO: Update the blockGrid with the appropriate next part of the algorithm
			new AnimationTimer() {
				@Override
				public void handle(long now) {
					//Animation happens every half second
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//Populate JavaFX grid with blocks
					for(int i = 0; i < numBlocksX; i++) {
						for(int j = 0; j < numBlocksY; j++) {
							Rectangle r = new Rectangle();
							r.setWidth(blockSize);
							r.setHeight(blockSize);
							r.setFill(pieceColors.get(blockGrid[i][j]));
							grid.add(r, j, i);
						}
					}
					
					//TODO: Show every iteration until the rectangle is full, so change this to model that, maybe check to make sure there are no 'N's' left in grid 
					//if(something) { stop(); }
					
				}
			}.start();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Try to place a piece in the grid, and then return the new board if it's successful
	 * 
	 * @param upperLeftX X Position of the place to put the upper left hand corner of piece
	 * @param upperLeftY Y Position of the place to put the upper left hand corner of piece
	 * @param curPiece The piece to be put on the grid
	 * @return The updated grid if the pieces fit onto it
	 */
	public static boolean tryPut(int upperLeftX, int upperLeftY, char[][] curPiece) {
		char[][] ret = new char[numBlocksX][numBlocksY];
		//Deep Copy
		for(int i = 0; i < numBlocksX; i++) {
			for(int j = 0; j < numBlocksY; j++) {
				ret[i][j] = blockGrid[i][j];
			}
		}
		
		//Piece cannot be put if the index has gone out of bounds
		if(upperLeftY + curPiece.length > numBlocksY || upperLeftX + curPiece[0].length > numBlocksX) {
			return false;
		}
		
		//Add piece to board
		for(int i = upperLeftY; i < upperLeftY + curPiece.length; i++) {
			for(int j = upperLeftX; j < upperLeftX + curPiece[0].length; j++) {
				//Only mark locations of non-'N' spots
				if(curPiece[i-upperLeftY][j-upperLeftX] != 'N') {
					//If the pieces interfere then the piece cannot be put
					if(ret[i][j] != 'N') {
						return false;
					}
					ret[i][j] = curPiece[i-upperLeftY][j-upperLeftX];
				}
				
			}
		}
		
		
		
		//Deep Copy
		for(int i = 0; i < numBlocksX; i++) {
			for(int j = 0; j < numBlocksY; j++) {
				blockGrid[i][j] = ret[i][j];
			}
		}
		
		return true;
	}
	

	
	/**
	 * Try to place a piece in the grid, and then return the new board if it's successful
	 * 
	 * @param int z the x pos 
	 * @param int t the y pos
	 * @param boolean[][] marked is the array of marked positions
	 * @param char[][] is the array of characters
	 * @return the amount of neighbors
	 */
	public static int findNeighbors(int z, int t, boolean[][] marked, char[][] ret) {
		
		//total neighbors of this spot
		int totalNeighbors = 0;
		
		//if we are out of the grid
		if ((z > numBlocksX-1) || (z < 0) || (t > numBlocksY-1) || (t < 0) || (marked[z][t] == true)) {
			return 0;
		}
		//if we are in the grid
		else {
			//marked = true
			marked[z][t]= true;
			
			//if we are at an empty space
			if (ret[z][t] == 'N') {
				//do the recursive stuff
				totalNeighbors += 1 + findNeighbors(z, t+1, marked, ret)
				+ findNeighbors(z+1, t, marked, ret)
				+ findNeighbors(z, t-1, marked, ret)
				+ findNeighbors(z-1, t, marked, ret);
			}
			//else return 0
			else {
				return 0;
			}
		}

		//return the num neighbors
		return totalNeighbors;
	
	}
	
	
	/**
	 * Fill the HashMap with appropriate colors for the shape ID
	 */
	public static void setColorMap() {
		pieceColors = new HashMap<Character, javafx.scene.paint.Color>();
		pieceColors.put('I',Color.AQUAMARINE);
		pieceColors.put('O',Color.YELLOW);
		pieceColors.put('T',Color.MAGENTA);
		pieceColors.put('J',Color.BLUE);
		pieceColors.put('L',Color.ORANGE);
		pieceColors.put('S',Color.GREEN);
		pieceColors.put('Z',Color.RED);
		pieceColors.put('N',Color.WHITE);
		
	}
	
	/**
	 * Fill hashmaps with each piece type and orientation
	 */
	public static void setPieceMap() {
		pieces = new HashMap<String, char[][]>();
		
		//I Logic
		char[][] iPiece = new char[4][1];
		iPiece[0][0] = 'I';
		iPiece[1][0] = 'I';
		iPiece[2][0] = 'I';
		iPiece[3][0] = 'I';
		pieces.put("I1", iPiece);
		pieces.put("I2", rotateClockWise(iPiece));
		
		//O Logic
		char [][] oPiece = new char[2][2];
		oPiece[0][0] = 'O';
		oPiece[0][1] = 'O';
		oPiece[1][0] = 'O';
		oPiece[1][1] = 'O';
		pieces.put("O", oPiece);
		
		//T Logic
		char[][] tPiece = new char[2][3];
		tPiece[0][0] = 'T';
		tPiece[0][1] = 'T';
		tPiece[0][2] = 'T';
		tPiece[1][0] = 'N';
		tPiece[1][1] = 'T';
		tPiece[1][2] = 'N';
		pieces.put("T1",tPiece);
		pieces.put("T2",rotateClockWise(tPiece));
		pieces.put("T3",rotateClockWise(rotateClockWise(tPiece)));
		pieces.put("T4",rotateClockWise(rotateClockWise(rotateClockWise(tPiece))));
		
		//J Logic
		char[][] jPiece = new char[3][2];
		jPiece[0][0] = 'N';
		jPiece[0][1] = 'J';
		jPiece[1][0] = 'N';
		jPiece[1][1] = 'J';
		jPiece[2][0] = 'J';
		jPiece[2][1] = 'J';
		pieces.put("J1",jPiece);
		pieces.put("J2",rotateClockWise(jPiece));
		pieces.put("J3",rotateClockWise(rotateClockWise(jPiece)));
		pieces.put("J4",rotateClockWise(rotateClockWise(rotateClockWise(jPiece))));
		
		//L Logic
		char[][] lPiece = new char[3][2];
		lPiece[0][0] = 'L';
		lPiece[0][1] = 'N';
		lPiece[1][0] = 'L';
		lPiece[1][1] = 'N';
		lPiece[2][0] = 'L';
		lPiece[2][1] = 'L';
		pieces.put("L1",lPiece);
		pieces.put("L2",rotateClockWise(lPiece));
		pieces.put("L3",rotateClockWise(rotateClockWise(lPiece)));
		pieces.put("L4",rotateClockWise(rotateClockWise(rotateClockWise(lPiece))));
		
		//S Logic
		char[][] sPiece = new char[2][3];
		sPiece[0][0] = 'N';
		sPiece[0][1] = 'S';
		sPiece[0][2] = 'S';
		sPiece[1][0] = 'S';
		sPiece[1][1] = 'S';
		sPiece[1][2] = 'N';
		pieces.put("S1",sPiece);
		pieces.put("S2",rotateClockWise(sPiece));
		pieces.put("S3",rotateClockWise(rotateClockWise(sPiece)));
		pieces.put("S4",rotateClockWise(rotateClockWise(rotateClockWise(sPiece))));
		
		//Z Logic
		char[][] zPiece = new char[2][3];
		zPiece[0][0] = 'Z';
		zPiece[0][1] = 'Z';
		zPiece[0][2] = 'N';
		zPiece[1][0] = 'N';
		zPiece[1][1] = 'Z';
		zPiece[1][2] = 'Z';
		pieces.put("Z1",zPiece);
		pieces.put("Z2",rotateClockWise(zPiece));
		pieces.put("Z3",rotateClockWise(rotateClockWise(zPiece)));
		pieces.put("Z4",rotateClockWise(rotateClockWise(rotateClockWise(zPiece))));
	}
	
	/**
	 * Fill the blockGrid with initial 'N' characters
	 */
	public static void buildBlockGrid() {
		blockGrid = new char[numBlocksX][numBlocksY];
		for(int i = 0; i < numBlocksX; i++) {
			for(int j = 0; j < numBlocksY; j++) {
				blockGrid[i][j] = 'N';
			}
		}
	}
	
	/**
	 * Rotates a piece 90 degrees
	 * 
	 * @param piece an existing piece
	 * @return the new array 90 degrees clockwise
	 */
	public static char[][] rotateClockWise(char[][] piece){
		final int M = piece.length;
		final int N = piece[0].length;
		char[][] ret = new char[N][M];
		for (int r = 0; r < M; r++) {
	        for (int c = 0; c < N; c++) {
	            ret[c][M-1-r] = piece[r][c];
	        }
	    }
		return ret;
	}
	
	/**
	 * Prints a piece for debugging
	 * @param piece array holding a piece
	 */
	public static void printPiece(char[][] piece) {
		for(int i = 0; i < piece.length; i++) {
			for(int j = 0; j < piece[0].length; j++) {
				System.out.print(piece[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		setColorMap();
		setPieceMap();
		buildBlockGrid();
		//TODO: Remove this example 
		launch(args);
	}
}
