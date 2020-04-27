import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.paint.Color;

public class Board {

    private char[][] pieces; //grid of pieces
    private char turn; //turn of game
    private int xVal; //raw value for white
    private int XVal; //raw value for blue
    private double totalScorex; //heuristic value for white
    private double totalScoreX; //heuristic value for blue
    private boolean mustCapture = false; //does the board have to capture piece
    private boolean isK = false; //do we have a blue king
    private boolean isk = false; //do we have a white king
    private Queue<Board> previousBoards; //ArrayList of 6 previous boards

	/**
	 * Constructor for basic board
	 */
    public Board() {
    	
        pieces = new char[8][8]; //init pieces
        previousBoards = new LinkedList<Board>(); //init previousBoards
        isk = false; //no white kings
        isK = false; //no Blue kings
        xVal = 36; //all pieces are on
        XVal = 36; //all pieces are on
        turn = 'X'; //start with blue
        
        //for each piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                
                if (i % 2 != 0 && j % 2 == 0 && j > 4) {
                	//place blue piece
                    pieces[i][j] = 'X';
                }
                else if (i % 2 != 0 && j % 2 == 0 && j < 3) {
                	//place white piece
                    pieces[i][j] = 'x';
                }
                
                else if (i % 2 == 0 && j % 2 != 0 && j > 4) {
                	//place blue piece
                    pieces[i][j] = 'X';
                }
                else if (i % 2 == 0 && j % 2 != 0 && j < 3) {
                	//place white piece
                    pieces[i][j] = 'x';
                }
                else {
                	//place emtpy piece
                    pieces[i][j] = 'O';
                }

            }
        }
        
        //update the game values
        updateGame();
    }
          
	/**
	 * Constructor based on other board
	 * @param Board board is the board we are using to construct this one
	 */
    public Board(Board board) {
    	
    	//new previous boards
    	previousBoards = new LinkedList<Board>();
    	
    	//for each board in pr3evious boards
    	for (Board b: board.previousBoards) {
    		//add to this previousBoards
    		this.previousBoards.add(b);
    	}

    	//this king values are boards king values
    	this.isK = board.isK;
    	this.isk = board.isk;

    	//this turn is boards turn
        turn = board.getTurn();
        
        //init pieces
        this.pieces = new char[8][8];
        
        //for each piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	//assign this boards pieces to other boards pieces
                pieces[i][j] = board.getPieces()[i][j];
            }
        }
        
        //if queue is not full
        if (previousBoards.size() <= 16) {
        	//add board
        	previousBoards.add(board);
        }
        //if queue is full
        else {
        	//poll
        	previousBoards.poll();
        	//add board
        	previousBoards.add(board);
        }
        
        //update game values
        updateGame();

    }
    
    
	/**
	* getter for pieces
	* @return pieces array
	*/
    public char[][] getPieces(){
        return pieces;
    }
      
	/**
	* getter for piece
	* @return a single piece from pieces
	*/
    public char getPiece(int i, int j) {
        return pieces[i][j];
    }
    
    public Queue<Board> getPreviousBoards(){
    	return previousBoards;
    }
    
	/**
	* getter for turn
	* @return the turn the board is on
	*/
    public char getTurn()  {
        return turn;
    }
    
	/**
	* getter for raw blue score
	* @return the raw blue score
	*/
    public int getBlueScore() {
        return xVal;
    }
    
	/**
	* getter for raw white score
	* @return the raw white score
	*/
    public int getWhiteScore() {
        return XVal;
    }

	/**
	* Method that updates the game values for things such as
	* do we have to take a piece on this board?
	*/
    public void updateGame() {
    	
    	//reset xVal/XVal
        xVal = 0;
        XVal = 0;
        
        //temps for checking king stuff
        boolean doubleCheckk = false;
        boolean doubleCheckK = false;
        
        //for each piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	//if white piece
                if (pieces[i][j] == 'x') {
                	//add 3
                    XVal+= 3;
                }
                //if white king
                if (pieces[i][j] == 'k') {
                	//we know there is a white king
                	doubleCheckk = true;
                	//add 5
                    XVal+= 5;
                }
                //if blue piece
                if (pieces[i][j] == 'X') {
                	//add 3
                    xVal+= 3;
                }
                //if blue king
                if (pieces[i][j] == 'K') {
                	//we know there is a blue king
                	doubleCheckK = true;
                	//add 5
                    xVal+= 5;
                }
            }
        } 
        
        //if we did not find a white king
        if (doubleCheckk == false) {
        	//isk is false
        	isk = false;
        }
        //if we did not find a blue king
        if (doubleCheckK == false) {
        	//isK is false
        	isK = false;
        }
        
        //variables to know if white or blue must take a piece
        boolean killConfirmx = false;
        boolean killConfirmX = false;
        
        //for each piece
        for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				//if we have a white piece
				if (pieces[i][j] == 'x') {
					
					//if not out of bounds
					if (i < 6 && j < 6){
						//if we can take piece
						if (((pieces[i+1][j+1] == 'X') || (pieces[i+1][j+1] == 'K')) && (pieces[i+2][j+2] == 'O') ){
							//we must take piece
							killConfirmx = true;
						}
					}
			
					//if not out of bounds
					if (i > 1 && j < 6) {
						//if we can take piece
						if (((pieces[i-1][j+1] == 'X') || (pieces[i-1][j+1] == 'K')) && (pieces[i-2][j+2] == 'O') ) {
							//we must take piece
							killConfirmx = true;
						}
					}				
				}
				//if we have a blue piece
				if (pieces[i][j] == 'X') {
					//if not out of bounds
					if  (i > 1 && j > 1) {
						//if we can take piece
						if (((pieces[i-1][j-1] == 'x') || (pieces[i-1][j-1] == 'k')) && (pieces[i-2][j-2] == 'O')){
							//we must take piece
							killConfirmX = true;
						}
					}
					//if not out of bounds
					if (i < 6 && j > 1) {
						//if we can take piece
						if (((pieces[i+1][j-1] == 'x') || (pieces[i+1][j-1] == 'k')) && (pieces[i+2][j-2] == 'O')){
							//we must take piece
							killConfirmX = true;
						}
					}	
				}
				//if we have a white king
				if (pieces[i][j] == 'k') {
					//if not out of bounds
					if (i < 6 && j < 6) {
						//if we can take piece
						if (((pieces[i+1][j+1] == 'X') || (pieces[i+1][j+1] == 'K')) && (pieces[i+2][j+2] == 'O')){
							//we must take piece
							killConfirmx = true;
						}
					}
					//if not out of bounds
					if  (i < 6 && j > 1) {
						//if we can take piece
						if (((pieces[i+1][j-1] == 'X') || (pieces[i+1][j-1] == 'K')) && (pieces[i+2][j-2] == 'O')) {
							//we must take piece
							killConfirmx = true;
						}
					}
					//if not out of bounds
					if (i > 1 && j < 6) {
						//if we can take piece
						if (((pieces[i-1][j+1] == 'X') || (pieces[i-1][j+1] == 'K')) && (pieces[i-2][j+2] == 'O')){
							//we must take piece
							killConfirmx = true;
						}
					}
					//if not out of bounds
					if (i > 1 && j > 1) {
						//if we can take piece
						if (((pieces[i-1][j-1] == 'X') || (pieces[i-1][j-1] == 'K')) && (pieces[i-2][j-2] == 'O')){
							//we must take piece
							killConfirmx = true;
						}
					}
					
				}
				//if we have a blue king
				if (pieces[i][j] == 'K') {
					//if not out of bounds
					if (i < 6 && j < 6) {
						//if we can take piece
						if (((pieces[i+1][j+1] == 'x') || (pieces[i+1][j+1] == 'k')) && (pieces[i+2][j+2] == 'O')){
							//we must take piece
							killConfirmX = true;
						}
					}
					//if not out of bounds
					if (i < 6 && j > 1) {
						//if we can take piece
						if (((pieces[i+1][j-1] == 'x') || (pieces[i+1][j-1] == 'k')) && (pieces[i+2][j-2] == 'O')) {
							//we must take piece
							killConfirmX = true;
						}
					}
					//if not out of bounds
					if (i > 1 && j < 6) {
						//if we can take piece
						if (((pieces[i-1][j+1] == 'x') || (pieces[i-1][j+1] == 'k')) && (pieces[i-2][j+2] == 'O')){
							//we must take piece
							killConfirmX = true;
						}
					}
					//if not out of bounds
					if (j > 1 && i > 1) {
						//if we can take piece
						if (((pieces[i-1][j-1] == 'x') || (pieces[i-1][j-1] == 'k')) && (pieces[i-2][j-2] == 'O')){
							//we must take piece
							killConfirmX = true;
						}
					}
				}
			}
		}
        
        //if turn is white and we must take piece as white
        if (turn == 'x' && killConfirmx == true) {
        	//we must capture this turn
        	mustCapture = true;
        }
        //if turn is blue and we must take piece as blue
        else if (turn == 'X' && killConfirmX == true) {
        	//we must capture this turn
        	mustCapture = true;
        }
        else {
        	//we don't capture this turn
        	mustCapture = false;
        }
    	
    }
    
	/**
	* method to return and compute the heuristic score of a player
	* @param char player is the player we are calculating for
	* @return double is the heuristic score for the player
	*/
    public double getTotalScore(char player) {
        
    	//temp vals
    	int tempx = 0;
        int tempX = 0;
        
        //numKings we have 
        int numKings = 0;
        int numkings = 0;
        
        double distancex = 0;
        double distanceX = 0;
        
        //for each piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	//if a white piece then the score is 3 + every row it advances 
                if (pieces[i][j] == 'x') {
                	tempx+= 10 - (i);
                }
                //if king then score is 10 
                if (pieces[i][j] == 'k') {
                    tempx+= 10;
                    
                    //increment number of kings we have
                    numkings++;
                }
                //if a blue piece then the score is 3 + every row is advances
                if (pieces[i][j] == 'X') {
                    tempX+= 3 + (i);
                }
                //if king then score is 10
                if (pieces[i][j] == 'K') {
                    tempX+= 10;
                    
                    //increment the number of kings we have 
                	numKings++;
                }
            }
        }
        
        //if we have kings of any kind
        if (numKings != 0 || numkings != 0) {
        	//if we have more white kings than blue kings
            if (numkings >= numKings) {
            	
            	//get all of the kings
            	ArrayList<ArrayList<Integer>> kings = getKingsLeft('x');
            	//temp distance if for the distance we will calculate
            	double tempDist = 0;
            	
            	//for each king white has
            	for (ArrayList<Integer> piece: kings) {
            		//reset tempDist
                	tempDist = 100;
                	//for each piece on the board
            		for (int i = 0; i < 8; i++) {
            			for (int j = 0; j < 8; j++) {
            				//if we find a blue king
            	            if (pieces[i][j] == 'K') {
            	            	//calculate the distance from white king to the nearest blue king
            	            	double xSquared = Math.pow((piece.get(0) - j), 2);
            	            	double ySquared = Math.pow((piece.get(1) - i), 2);
            	            	double temp = Math.sqrt((xSquared + ySquared));
           	            	
            	            	//find the smallest distance
	           	            	if (temp <= tempDist) {
	           	            		tempDist = temp;
	           	            		
	           	            	}
        	                }
            			}
            		}
            		//subtract smallest distance for each king
            		distancex -= tempDist;
            	}
            }
            //if we have more blue kings than white kings
            else if (numKings >= numkings){
            	//get all of the kings
            	ArrayList<ArrayList<Integer>> Kings = getKingsLeft('X');
            	
            	//temp to store distances
            	double tempDist = 100;
            	//for each king we have
            	for (ArrayList<Integer> piece: Kings) {
            		//reset tempDist
            		tempDist = 0;
            		//for each piece
            		for (int i = 0; i < 8; i++) {
            			for (int j = 0; j < 8; j++) {
            				//if we find a white king
            	            if (pieces[i][j] == 'k') {
            	            	//find the distance
            	            	double xSquared = Math.pow((piece.get(0) - j), 2);
            	            	double ySquared = Math.pow((piece.get(1) - i), 2);
            	            	double temp = Math.sqrt((xSquared + ySquared));

            	            	//find the smallest distance
            	            	if (temp <= tempDist) {
            	            		tempDist = temp;
            	            	}
        	                }
            			}
            		}
            		//subtract smallest distance for each king
            		distanceX -= tempDist;
            	}
            }
        }
   		
        //if we have a stale mate coming up
        if (isStale()) {
        	//the scores are the sums of each piece - the distance of all kings from the other pieces kings - 10000
            totalScorex = tempx - tempX - distancex - 10000;
            totalScoreX = tempX - tempx - distancex - 10000;
        }
        else {
        	//the scores are the sums of each piece - the distance of all kings from the other pieces kings
            totalScorex = tempx - tempX - distancex;
            totalScoreX = tempX - tempx - distanceX;
        }
        
        //if player is white
    	if (player == 'x') {
    		//return the white score
    		return totalScorex;
    	}
    	
    	//return blue score
    	return totalScoreX;
                         
    }
         
	/**
	* method to get the pieces that are left for the given player
	* @param char player whose turn it is
	* @return ArrayList<ArrayList<Integer>> is the list of the positions of each peace
	*/
    public ArrayList<ArrayList<Integer>> getPiecesLeft(char player) {
             
    	//list of pieces to be returned
        ArrayList<ArrayList<Integer>> pReturned = new ArrayList<ArrayList<Integer>>();
        
        //counter starts at 0
        int pieceCounter = 0;
        
        //if player is white
        if (player == 'x') {
        	//check the grid of all the pieces
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                	//if the piece is white
                    if (pieces[i][j] == 'x') {   
                    		//add the piece to the lsit
	                        pReturned.add(new ArrayList<>());
	                        pReturned.get(pieceCounter).add(j);
	                        pReturned.get(pieceCounter).add(i);
	                        pieceCounter++;
                        }
                    //if the piece is a white king
                    if (pieces[i][j] == 'k') {
                    	//add the piece to the list
                    	pReturned.add(new ArrayList<>());
                    	pReturned.get(pieceCounter).add(j);
                    	pReturned.get(pieceCounter).add(i);
                    	pieceCounter++;
                    }
                }
            }
        }
        //if the player is blue
        else {
        	//check each piece in the grid
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                	//if the piece is blue
                    if (pieces[i][j] == 'X') {
                    	//add the piece to the list
                        pReturned.add(new ArrayList<>());
                        pReturned.get(pieceCounter).add(j);
                        pReturned.get(pieceCounter).add(i);
                        pieceCounter++;
                    }
                    //if the piece is a blue king
                    if (pieces[i][j] == 'K') {
                    	//add the piece to the list
                        pReturned.add(new ArrayList<>());
                        pReturned.get(pieceCounter).add(j);
                        pReturned.get(pieceCounter).add(i);
                        pieceCounter++;
                    }
                }
            }
        }
        
        //return the list
        return pReturned;
                        
    }
    
	/**
	* method to get the kings left for the given player
	* @param char player is the player we are calculating for
	* @return ArrayList<ArrayList<Integer>> is the list of positions of the king pieces
	*/
    public ArrayList<ArrayList<Integer>> getKingsLeft(char player) {
        
    	//list of the positions of the kings to be returned
        ArrayList<ArrayList<Integer>> pReturned = new ArrayList<ArrayList<Integer>>();
        
        //counter starts at 0
        int pieceCounter = 0;
        
        //if the player is white
        if (player == 'x') {
        	//for each piece in the grids
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                	//if the piece is a white king
                	if (pieces[i][j] == 'k') {
                		//add the piece to the list
                		pReturned.add(new ArrayList<>());
                		pReturned.get(pieceCounter).add(j);
                		pReturned.get(pieceCounter).add(i);
                		pieceCounter++;
                	}
                }
            }
        }
        //if the player is blue
        else {
        	//for each piece in the grid
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                	//if the peace is a blue king
                    if (pieces[i][j] == 'K') {
                    	//add the piece to the list
                        pReturned.add(new ArrayList<>());
                        pReturned.get(pieceCounter).add(j);
                        pReturned.get(pieceCounter).add(i);
                        pieceCounter++;
                    }
                }
            }
        }
        
        //return the list of positions of kings
        return pReturned;
                        
    }
        
	/**
	* method to determine the new placement of a piece
	* @param int x is initial x position 
	* @param int y is initial y position
	* @param char replace is the character we are replacing with
	*/
    public void newPlacement(int x, int y, char replace) {
        //if we are replacing with a blue piece at king position             
        if (replace == 'X' && y == 0) {
        	//if our turn is white
            if (turn == 'x') {
            	//flop the turn
                turn = 'X';
            }
	        //if we are replacing with a white piece    
	        else {
	        	//flop turn
	            turn = 'x';
	        }
            //replace the piece with a blue king
            pieces[x][y] = 'K';
            //notify the var
            isK = true;
        }
        //if we are replacing with a white piece at king position
        else if (replace == 'x' && y == 7) {
        	//if turn is white
            if (turn == 'x') {
            	//flop turn
                turn = 'X';
            }
            //turn is white
            else {
                turn = 'x';
            }
            //make white king
            pieces[x][y] = 'k';
            //notify the var
            isk = true;
        }
        //if no king move
        else {
        	//if turn is white
            if (turn == 'x') {
            	//turn is blue
                turn = 'X';
            }
            //if turn is blue
            else {
            	//turn is white
                turn = 'x';
            }
            //replace the piece
            pieces[x][y] = replace;
        }
                        
    }
            
	/**
	* method to return a list of the boards possible based on moves
	* @param ArrayList<Integer> is the pieces to be attempted to moved
	* @return return a list of the boards possible based on moves
	*/
    public ArrayList<Board> nextBoards(ArrayList<Integer> current){
             
    	//list of new boards
        ArrayList<Board> listOfBoard = new ArrayList<Board>();
        
        //targets and current
        int cur[] = new int[2];
        int tar[] = new int[2];
                    
        //current
        int bpx = current.get(0);
        int bpy = current.get(1);
        
        //set currents
        cur[0] = bpx;
        cur[1] = bpy;

        //temp is a new board of this
        Board temp = new Board(this);

        //new position
        tar[0] = bpx - 1;
        tar[1] = bpy - 1;
        
        //try to swap
        if (temp.doSwap(tar, cur)) {
        	//if we could then add board to list
            listOfBoard.add(new Board(temp));
        }
        
        //try new positions
        tar[0] = bpx + 1;
        tar[1] = bpy - 1;
        
        //new board of this
        temp = new Board(this);

        //try to swap
        if (temp.doSwap(tar, cur)) {
        	//if swap then add board to list
            listOfBoard.add(new Board(temp));
        }
        
        //try new position
        tar[0] = bpx - 1;
        tar[1] = bpy + 1;
        //make new board with this
        temp = new Board(this);

        //try to swap
        if (temp.doSwap(tar, cur)) {
        	//if we swapped then add board to list
            listOfBoard.add(new Board(temp));
        }
        
        //try new position
        tar[0] = bpx + 1;
        tar[1] = bpy + 1;
        
        //make new board with this
        temp = new Board(this);
        
        //try to do swap
        if (temp.doSwap(tar, cur)) {
        	//if swapped then add to board
            listOfBoard.add(new Board(temp));
        }
        
        //new position
        tar[0] = bpx + 2;
        tar[1] = bpy + 2;
        //new board with this
        temp = new Board(this);

        //try to do swap
        if (temp.doSwap(tar, cur)) {
        	//if swapped then add to board
            listOfBoard.add(new Board(temp));
        }
        
        //new positions 
        tar[0] = bpx + 2;
        tar[1] = bpy - 2;
        //new board with this
        temp = new Board(this);

        //try to do swap
        if (temp.doSwap(tar, cur)) {
        	//if swapped then add to board
            listOfBoard.add(new Board(temp));
        }
        
        //new positions
        tar[0] = bpx - 2;
        tar[1] = bpy + 2;
        //new board with this
        temp = new Board(this);
        
        //try to do swap
        if (temp.doSwap(tar, cur)) {
        	//if swapped then add to list
            listOfBoard.add(new Board(temp));
        }
        
        //new positions
        tar[0] = bpx - 2;
        tar[1] = bpy - 2;
        //new board with this
        temp = new Board(this);
    
        //try to do swap
        if (temp.doSwap(tar, cur)) {
        	//if swapped then add to list
            listOfBoard.add(new Board(temp));
        }             

        //the list of the possible boards
        return listOfBoard;

    }
    

    
	/**
	* method to see if the boards are equal
	* @param Board otherboard is the board we are comparing to 
	* @return boolean if the boards are equal
	*/
	public boolean isEqual(Board otherBoard) {
		
		//for every piece in the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	//if the pieces are not equal
                if (this.getPiece(i, j) != otherBoard.getPiece(i, j)) {
                	//return false
                	return false;
                }
            }
        }
        
        //return true if they are equals
        return true;
	}
                    
	
    
	/**
	* method to determine if stale mate
	* @return boolean if there is a stale mate detected
	*/
	public boolean isStale() {

		//counter set at 0
		int counter = 0;

		//for each board in the queue
		for (Board b : this.previousBoards) {
			//if the boards are equal
			if (b.isEqual(getPreviousBoards().peek())) {
				//increment the counter
				counter++;
			}
		}
		
		//if the counter is greater than or equal to 3
		if (counter >= 3) {
			//return true
			return true;
		}
		
		return false;
	}

	/**
	* method to swap pieces
	* @param int[] target is the target piece
	* @param int[] buttonPressed is the button we want to swap with
	* @return boolean true if swap was done
	*/
    public boolean doSwap(int[] target, int[] buttonPressed) {
        
    	//button pressed
        int bpx = buttonPressed[1];
        int bpy = buttonPressed[0];
        
        //target
        int targetx = target[1];
        int targety = target[0];
        
        //do we double
        boolean doubleJump = false;
        
        //if out of bounds return false
        if (bpx < 0 || bpy < 0 || targetx < 0 || targety < 0 || bpx > 7 || bpy > 7 || targetx > 7 || targety > 7) {
            return false;
        }
        
        //handling jumps and takes of reg pieces
        if ((bpx + 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
        	((pieces[targetx-1][targety+1] == 'x') || (pieces[targetx-1][targety+1] == 'k')) &&
             (pieces[bpx][bpy] == 'X') && (turn == 'X')) {
    		 char replace = pieces[bpx][bpy];
             newPlacement(targetx, targety, replace);
             pieces[targetx-1][targety+1] = 'O';
             pieces[bpx][bpy] = 'O';
             updateGame();
             
             //if double jump
             doubleJump = attemptDouble(bpx + 2, bpy - 2, pieces[bpx+2][bpy-2]);
             if (doubleJump) {
                 if (turn == 'x'){
                     turn = 'X';
                 }
                 else {
                     turn = 'x';
                 }
             }
             return true;
         
        }
        
        //handling jumps and takes of reg pieces
        if ((bpx - 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety+1] == 'x') || (pieces[targetx+1][targety+1] == 'k')) &&
            (pieces[bpx][bpy] == 'X') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
        
        //handling jumps and takes
        if ((bpx - 2 == targetx) && (bpy + 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety-1] == 'X') || (pieces[targetx+1][targety-1] == 'K')) &&
            (pieces[bpx][bpy] == 'x') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy + 2, pieces[bpx-2][bpy+2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
        
        //handling jumps and takes of reg pieces
        if ((bpx + 2 == targetx) && (bpy + 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety-1] == 'X') || (pieces[targetx-1][targety-1] == 'K')) &&
            (pieces[bpx][bpy] == 'x') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
              
        //handling jumps and takes of king pieces
        if ((bpx + 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety+1] == 'x') || (pieces[targetx-1][targety+1] == 'k')) &&
            (pieces[bpx][bpy] == 'K') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy - 2, pieces[bpx+2][bpy-2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
                        
        //handling jumps and takes with king pieces 
        if ((bpx - 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety+1] == 'x') || (pieces[targetx+1][targety+1] == 'k')) &&
            (pieces[bpx][bpy] == 'K') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
                            
        //handling jumps and takes with king pieces
        if ((bpx - 2 == targetx) && (bpy + 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety-1] == 'x') || (pieces[targetx+1][targety-1] == 'k')) &&
            (pieces[bpx][bpy] == 'K') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy + 2, pieces[bpx-2][bpy+2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
                            
        //handling jumps and takes with king pieces
        if ((bpx + 2 == targetx) && (bpy + 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety-1] == 'x') || (pieces[targetx-1][targety-1] == 'k')) &&
            (pieces[bpx][bpy] == 'K') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                	turn = 'X';
                }
                else {
                	turn = 'x';
                }
            }
            return true;
        }
                            
                                
        //handling jumps and takes of king pieces
        if ((bpx + 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety+1] == 'X') || (pieces[targetx-1][targety+1] == 'K')) &&
            (pieces[bpx][bpy] == 'k') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy - 2, pieces[bpx+2][bpy-2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                	turn = 'X';
                }
                else {
                	turn = 'x';
                }
            }
            return true;
        }

        //handling jumps and takes with king pieces
        if ((bpx - 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety+1] == 'X') || (pieces[targetx+1][targety+1] == 'K')) &&
            (pieces[bpx][bpy] == 'k') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
                                
        //handling jumps and takes with king pieces
        if ((bpx - 2 == targetx) && (bpy + 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety-1] == 'X') || (pieces[targetx+1][targety-1] == 'K')) &&
            (pieces[bpx][bpy] == 'k') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy + 2, pieces[bpx-2][bpy+2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }

        //handling jumps and takes with king pieces
        if ((bpx + 2 == targetx) && (bpy + 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety-1] == 'X') || (pieces[targetx-1][targety-1] == 'K')) &&
            (pieces[bpx][bpy] == 'k') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
            
            //if double jump
            if (doubleJump) {
                if (turn == 'x'){
                    turn = 'X';
                }
                else {
                    turn = 'x';
                }
            }
            return true;
        }
        
        //handling single moves of reg pieces
        if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'X') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';  
            updateGame();
            return true;
        }
        
        //handling single moves of reg pieces
        if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'x') && (turn == 'x')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
        //handling single moves of reg pieces
        if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
            (pieces[targetx][targety] == 'O')  &&
            ((pieces[bpx][bpy] == 'X') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
                        
        //handling single moves of reg pieces
        if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'x') && (turn == 'x')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true; 
        }
        
        //handling single moves of k pieces
        if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'k') && (turn == 'x')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
        //handling single moves of k pieces
        if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'k') && (turn == 'x')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
                        
        //handling single moves of k pieces
        if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
            (pieces[targetx][targety] == 'O')  &&
            ((pieces[bpx][bpy] == 'k') && (turn == 'x')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
        //handling single moves of k pieces
        if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'k') && (turn == 'x')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
                        
        //handling single moves of k pieces
        if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'K') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
                        
                        
        //handling single moves of k pieces
        if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'K') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
        
        //handling single moves of K pieces
        if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'K') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
                        
        //handling single moves of k pieces
        if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
            (pieces[targetx][targety] == 'O')  &&
            ((pieces[bpx][bpy] == 'K') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
        //handling single moves of k pieces
        if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
            (pieces[targetx][targety] == 'O') &&
            ((pieces[bpx][bpy] == 'K') && (turn == 'X')) && (mustCapture == false)) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[bpx][bpy] = 'O';
            updateGame();
            return true;
        }
        
        //we could not swap
        return false;
    }
              
	/**
	* method to attempt double jumps
	* @param int bpx x position
	* @param int bpy y position
	* @param char current is the current player we are trying to double jump with
	* @return boolean true if swap was done
	*/
    public boolean attemptDouble(int bpx, int bpy, char current) {

    //try for out of bounds stuff
    try {
    	//handling doubles of big bois
        if ((pieces[bpx-2][bpy+2] == 'O') && 
            ((pieces[bpx-1][bpy+1] == 'X') || (pieces[bpx-1][bpy+1] == 'K')) &&
            (((current == 'k') || (current == 'x')) && turn != 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(bpx-2, bpy+2, replace);
            pieces[bpx-1][bpy+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            attemptDouble(bpx-2, bpy+2, current);
            return true;
        }

        }catch(Exception e) {
        }
        

    	//try for out of bounds stuff
        try {
        //handling doubles of big bois
        if ((pieces[bpx-2][bpy-2] == 'O') &&
            ((pieces[bpx-1][bpy-1] == 'X') || (pieces[bpx-1][bpy-1] == 'K')) &&
            (((current == 'k') || (current == 'x')) && turn != 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(bpx-2, bpy-2, replace);
            pieces[bpx-1][bpy-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            attemptDouble(bpx - 2, bpy - 2, current);
            return true;
        }
        }catch(Exception e) {
        }


        //try for out of bounds stuff
        try {
        //handling doubles of big bois
        if ((pieces[bpx+2][bpy-2] == 'O') &&
            ((pieces[bpx+1][bpy-1] == 'X') || (pieces[bpx+1][bpy-1] == 'K')) &&
            (((current == 'k') || (current == 'x')) && turn != 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(bpx+2, bpy-2, replace);
            pieces[bpx+1][bpy-1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateGame();
            attemptDouble(bpx + 2, bpy - 2, current);
            return true;
        }
        }catch (Exception e) {
        }

        //try for out of bounds stuff
        try {
            //handling doubles of big bois
            if ((pieces[bpx+2][bpy+2] == 'O') &&
                ((pieces[bpx+1][bpy+1] == 'X') || (pieces[bpx+1][bpy+1] == 'K')) &&
                (((current == 'k') || (current == 'x')) && turn != 'x')) {
                char replace = pieces[bpx][bpy];
                newPlacement(bpx+2, bpy+2, replace);
                pieces[bpx+1][bpy+1] = 'O';
                pieces[bpx][bpy] = 'O';
                updateGame();
                attemptDouble(bpx + 2, bpy + 2, current);
                return true;
            }
        } catch (Exception e) {
        }
             
        //try for out of bounds stuff
        try {
            //handling doubles of small bois
            if ((pieces[bpx-2][bpy+2] == 'O') &&
                ((pieces[bpx-1][bpy+1] == 'x') || (pieces[bpx-1][bpy+1] == 'k')) &&
                (((current == 'K') || (current == 'X')) && turn != 'X')) {
                char replace = pieces[bpx][bpy];
                newPlacement(bpx-2, bpy+2, replace);
                pieces[bpx-1][bpy+1] = 'O';
                pieces[bpx][bpy] = 'O';
                updateGame();
                attemptDouble(bpx-2, bpy+2, current);
                return true;
            }
        } catch (Exception e) {
        }

        //try for out of bounds stuff
        try {
            //handling doubles of small bois
            if ((pieces[bpx-2][bpy-2] == 'O') &&
                ((pieces[bpx-1][bpy-1] == 'x') || (pieces[bpx-1][bpy-1] == 'k')) &&
                (((current == 'K') || (current == 'X')) && turn != 'X')) {
                char replace = pieces[bpx][bpy];
                newPlacement(bpx-2, bpy-2, replace);
                pieces[bpx-1][bpy-1] = 'O';
                pieces[bpx][bpy] = 'O';
                updateGame();
                attemptDouble(bpx - 2, bpy - 2, current);
                return true;
            }
        } catch (Exception e) {

        }               

        //try for out of bounds stuff           
        try {
            //handling doubles of small bois
            if ((pieces[bpx+2][bpy-2] == 'O') &&
                ((pieces[bpx+1][bpy-1] == 'x') || (pieces[bpx+1][bpy-1] == 'k')) &&
                (((current == 'K') || ((current == 'X'))) && turn != 'X')) {
                char replace = pieces[bpx][bpy];
                newPlacement(bpx+2, bpy-2, replace);
                pieces[bpx+1][bpy-1] = 'O';
                pieces[bpx][bpy] = 'O';
                updateGame();
                attemptDouble(bpx + 2, bpy - 2, current);
                return true;
        }
        } catch (Exception e) {
        }

        //try for out of bounds stuff
        try {
            //handling doubles of small bois
            if ((pieces[bpx+2][bpy+2] == 'O') && 
                ((pieces[bpx+1][bpy+1] == 'x') || (pieces[bpx+1][bpy+1] == 'k')) &&
                (((current == 'K') || (current == 'X'))) && turn != 'X') {
                char replace = pieces[bpx][bpy];
                newPlacement(bpx+2, bpy+2, replace);
                pieces[bpx+1][bpy+1] = 'O';
                pieces[bpx][bpy] = 'O';
                updateGame();
                attemptDouble(bpx + 2, bpy + 2, current);
                return true;
        }

        } catch (Exception e) {
        }
              
        //we could not double jump
        return false;

    }
	
}
