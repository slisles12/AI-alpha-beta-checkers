import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.paint.Color;

public class Board {

    private char[][] pieces;
    private char turn;
    private int xVal;
    private int XVal;
    private double totalScorex;
    private double totalScoreX;
    private boolean mustCapture = false;
    private boolean isK = false;
    private boolean isk = false;
    private int pieceChanged;
    private Queue<Board> previousBoards;

    public Board() {

        this.pieces = new char[8][8];
        isK = false;
        isk = false;
        previousBoards = new LinkedList<Board>();
        pieceChanged = 0;
        turn = 'X';
        xVal = 0;
        XVal = 0;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                            
                if (i % 2 != 0 && j % 2 == 0 && j > 4) {
                    pieces[i][j] = 'X';
                }
                else if (i % 2 != 0 && j % 2 == 0 && j < 3) {
                    pieces[i][j] = 'x';
                }
                
                else if (i % 2 == 0 && j % 2 != 0 && j > 4) {
                    pieces[i][j] = 'X';
                }
                else if (i % 2 == 0 && j % 2 != 0 && j < 3) {
                    pieces[i][j] = 'x';
                }
                else {
                    pieces[i][j] = 'O';
                }

            }
        }
        
        updateGame();
    }
              
    public Board(Board board) {
    	
    	this.pieceChanged = board.pieceChanged;
    	
    	//for (Board b: board.previousBoards) {
    	//	this.previousBoards.add(b);
    	//}

    	this.isK = board.isK;
    	this.isk = board.isk;
                             
        char[][] grid = board.getPieces();
        
        turn = board.getTurn();
        
        this.pieces = new char[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = grid[i][j];
            }
        }
        
        /*
        if (previousBoards.size() < 6) {
        	previousBoards.add(board);
        }
        else {
        	previousBoards.poll();
        	previousBoards.add(board);
        }*/
        
        updateGame();

    }
    
    
    public void updateGame() {
        xVal = 0;
        XVal = 0;
        
        boolean doubleCheckk = false;
        boolean doubleCheckK = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] == 'x') {
                    XVal+= 3;
                }
                if (pieces[i][j] == 'k') {
                	doubleCheckk = true;
                    XVal+= 5;
                }
                if (pieces[i][j] == 'X') {
                    xVal+= 3;
                }
                if (pieces[i][j] == 'K') {
                	doubleCheckK = true;
                    xVal+= 5;
                }
            }
        } 
        
        if (doubleCheckK == false) {
        	isK = false;
        }
        if (doubleCheckk == false) {
        	isk = false;
        }
        
        boolean killConfirmx = false;
        boolean killConfirmX = false;
        
        for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] == 'x') {
					
					if (i < 6 && j < 6){
						if (((pieces[i+1][j+1] == 'X') || (pieces[i+1][j+1] == 'K')) && (pieces[i+2][j+2] == 'O') ){
							killConfirmx = true;
						}
					}
			
					if (i > 1 && j < 6) {
						if (((pieces[i-1][j+1] == 'X') || (pieces[i-1][j+1] == 'K')) && (pieces[i-2][j+2] == 'O') ) {
							killConfirmx = true;
						}
					}				
				}
				if (pieces[i][j] == 'X') {

					if  (i > 1 && j > 1) {
						if (((pieces[i-1][j-1] == 'x') || (pieces[i-1][j-1] == 'k')) && (pieces[i-2][j-2] == 'O')){
							killConfirmX = true;
						}
					}
					
					if (i < 6 && j > 1) {
						if (((pieces[i+1][j-1] == 'x') || (pieces[i+1][j-1] == 'k')) && (pieces[i+2][j-2] == 'O')){
							killConfirmX = true;
						}
					}	
				}
				if (pieces[i][j] == 'k') {
					
					if (i < 6 && j < 6) {
						if (((pieces[i+1][j+1] == 'X') || (pieces[i+1][j+1] == 'K')) && (pieces[i+2][j+2] == 'O')){
							killConfirmx = true;
						}
					}
					
					if  (i < 6 && j > 1) {
						if (((pieces[i+1][j-1] == 'X') || (pieces[i+1][j-1] == 'K')) && (pieces[i+2][j-2] == 'O')) {
							killConfirmx = true;
						}
					}
					
					if (i > 1 && j < 6) {
						if (((pieces[i-1][j+1] == 'X') || (pieces[i-1][j+1] == 'K')) && (pieces[i-2][j+2] == 'O')){
							killConfirmx = true;
						}
					}
					
					if (i > 1 && j > 1) {
						if (((pieces[i-1][j-1] == 'X') || (pieces[i-1][j-1] == 'K')) && (pieces[i-2][j-2] == 'O')){
							killConfirmx = true;
						}
					}
					
				}
				if (pieces[i][j] == 'K') {
					
					if (i < 6 && j < 6) {
						if (((pieces[i+1][j+1] == 'x') || (pieces[i+1][j+1] == 'k')) && (pieces[i+2][j+2] == 'O')){
							killConfirmX = true;
						}
					}
			
					if (i < 6 && j > 1) {
						if (((pieces[i+1][j-1] == 'x') || (pieces[i+1][j-1] == 'k')) && (pieces[i+2][j-2] == 'O')) {
							killConfirmX = true;
						}
					}
				
					if (i > 1 && j < 6) {
						if (((pieces[i-1][j+1] == 'x') || (pieces[i-1][j+1] == 'k')) && (pieces[i-2][j+2] == 'O')){
							killConfirmX = true;
						}
					}
					
					if (j > 1 && i > 1) {
						if (((pieces[i-1][j-1] == 'x') || (pieces[i-1][j-1] == 'k')) && (pieces[i-2][j-2] == 'O')){
							killConfirmX = true;
						}
					}
				}
			}
		}
        
        if (turn == 'x' && killConfirmx == true) {
        	mustCapture = true;
        }
        else if (turn == 'X' && killConfirmX == true) {
        	mustCapture = true;
        }
        else {
        	mustCapture = false;
        }
    	
    }
    
    
    public double getTotalScore(char player) {
        
    	int tempx = 0;
        int tempX = 0;
        
        int numKings = 0;
        int numkings = 0;
        
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] == 'x') {
                	tempx+= 11 - (i + 3);
                }
                if (pieces[i][j] == 'k') {
                    tempx+= 10;
                    numkings++;
                }
                if (pieces[i][j] == 'X') {
                    tempX+= 3 + (i - 3);
                }
                if (pieces[i][j] == 'K') {
                    tempX+= 10;
                	numKings++;
                }
            }
        }
        
        
        
        if (numKings != 0 || numkings != 0) {
            if (numkings >= numKings) {
            	ArrayList<ArrayList<Integer>> kings = getKingsLeft('x');
            	
            	for (ArrayList<Integer> piece: kings) {
                	double tempDist = 0;
            		for (int i = 0; i < 8; i++) {
            			for (int j = 0; j < 8; j++) {
            	            if (pieces[i][j] == 'K') {
            	            	double xSquared = Math.pow((piece.get(0) - j), 2);
            	            	double ySquared = Math.pow((piece.get(1) - i), 2);
            	            	tempDist = Math.sqrt((xSquared + ySquared));
        	                }
            			}
            		}
                	tempx -= tempDist;
            	}
            }
            else if (numkings <= numKings){
            	ArrayList<ArrayList<Integer>> Kings = getKingsLeft('X');
            	
            	for (ArrayList<Integer> piece: Kings) {
            		double tempDist = 0;
            		for (int i = 0; i < 8; i++) {
            			for (int j = 0; j < 8; j++) {
            	            if (pieces[i][j] == 'k') {
            	            	double xSquared = Math.pow((piece.get(0) - j), 2);
            	            	double ySquared = Math.pow((piece.get(1) - i), 2);
            	            	 tempDist = Math.sqrt((xSquared + ySquared));
        	                }
            			}
            		}
	            	tempX -= tempDist;
            	}
            }
        }

        
        if (isStale()) {
            totalScoreX = tempX - tempx - 10000;
            totalScorex = tempx - tempX - 10000;
        }
        else {
            totalScoreX = tempX - tempx;
            totalScorex = tempx - tempX;
        }
        
    	if (player == 'x') {
    		return totalScorex;
    	}
    	
    	return totalScoreX;
                         
    }
    
    public void setTotalScore(char player, int score) {
        
    	if (player == 'x') {
    		totalScorex = score;
    	}
    	else {
        	totalScoreX = score;
    	}
                         
    }
      
    public boolean getisk() {
    	return isk;
    }
    
    public boolean getisK() {
    	return isK;
    }
    
    public char[][] getPieces(){
        return pieces;
    }
              
    public char getPiece(int i, int j) {
        return pieces[i][j];
    }

    public boolean doSwap(int[] target, int[] buttonPressed) {
        
        int bpx = buttonPressed[1];
        int bpy = buttonPressed[0];
        int targetx = target[1];
        int targety = target[0];
        boolean doubleJump = false;
        
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
             //pieceChanged = 0;
             updateGame();
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy + 2, pieces[bpx-2][bpy+2]);
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
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
            //pieceChanged = 0;
            updateGame();
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
                        
        //handling jumps and takes with king pieces 
        if ((bpx - 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety+1] == 'x') || (pieces[targetx+1][targety+1] == 'k')) &&
            (pieces[bpx][bpy] == 'K') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy + 2, pieces[bpx-2][bpy+2]);
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
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
            //pieceChanged = 0;
            updateGame();
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

        //handling jumps and takes with king pieces
        if ((bpx - 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx+1][targety+1] == 'X') || (pieces[targetx+1][targety+1] == 'K')) &&
            (pieces[bpx][bpy] == 'k') && (turn == 'x')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx+1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx - 2, bpy + 2, pieces[bpx-2][bpy+2]);
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
            //pieceChanged = 0;
            updateGame();
            doubleJump = attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
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
            pieceChanged++;
            updateGame();
            return true;
        }
                           
        return false;
    }
              
              
    public boolean attemptDouble(int bpx, int bpy, char current) {

                    
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
            attemptDouble(bpx-2, bpy+2, pieces[bpx-2][bpy+2]);
            return true;
        }

        }catch(Exception e) {
        }
        
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
            attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
            return true;
        }
        }catch(Exception e) {
        }

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
            attemptDouble(bpx + 2, bpy - 2, pieces[bpx+2][bpy-2]);
            return true;
        }
        }catch (Exception e) {
        }

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
                attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
                return true;
            }
        } catch (Exception e) {
        }
                                                          
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
                attemptDouble(bpx-2, bpy+2, pieces[bpx-2][bpy+2]);
                return true;
            }
        } catch (Exception e) {
        }

                             
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
                attemptDouble(bpx - 2, bpy - 2, pieces[bpx-2][bpy-2]);
                return true;
            }
        } catch (Exception e) {

        }
                             
                             
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
                attemptDouble(bpx + 2, bpy - 2, pieces[bpx+2][bpy-2]);
                return true;
        }
        } catch (Exception e) {
        }

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
                attemptDouble(bpx + 2, bpy + 2, pieces[bpx+2][bpy+2]);
                return true;
        }

        } catch (Exception e) {
        }
              
        return false;

    }
    
    public void setTurn(char c) {
        turn = c;
    }
    
    public char getTurn()  {
        return turn;
    }
    
    public int getBlueScore() {
        return xVal;
    }
    
    public int getWhiteScore() {
        return XVal;
    }
               
    public ArrayList<ArrayList<Integer>> getPiecesLeft(char side) {
                         
        ArrayList<ArrayList<Integer>> pReturned = new ArrayList<ArrayList<Integer>>();
        int pieceCounter = 0;
        
        if (side == 'x') {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (pieces[i][j] == 'x') {         
	                        pReturned.add(new ArrayList<>());
	                        pReturned.get(pieceCounter).add(j);
	                        pReturned.get(pieceCounter).add(i);
	                        pieceCounter++;
                        }
                        if (pieces[i][j] == 'k') {
                            pReturned.add(new ArrayList<>());
                            pReturned.get(pieceCounter).add(j);
                            pReturned.get(pieceCounter).add(i);
                            pieceCounter++;
                        }
                	}
            	}
        	}
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (pieces[i][j] == 'X') {
                        pReturned.add(new ArrayList<>());
                        pReturned.get(pieceCounter).add(j);
                        pReturned.get(pieceCounter).add(i);
                        pieceCounter++;
                    }
                    if (pieces[i][j] == 'K') {
                        pReturned.add(new ArrayList<>());
                        pReturned.get(pieceCounter).add(j);
                        pReturned.get(pieceCounter).add(i);
                        pieceCounter++;
                    }
                }
            }
        }
        
        return pReturned;
                        
    }
    
    public ArrayList<ArrayList<Integer>> getKingsLeft(char side) {
        
        ArrayList<ArrayList<Integer>> pReturned = new ArrayList<ArrayList<Integer>>();
        int pieceCounter = 0;
        
        if (side == 'x') {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                        if (pieces[i][j] == 'k') {
                            pReturned.add(new ArrayList<>());
                            pReturned.get(pieceCounter).add(j);
                            pReturned.get(pieceCounter).add(i);
                            pieceCounter++;
                        }
                	}
            	}
        	}
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (pieces[i][j] == 'K') {
                        pReturned.add(new ArrayList<>());
                        pReturned.get(pieceCounter).add(j);
                        pReturned.get(pieceCounter).add(i);
                        pieceCounter++;
                    }
                }
            }
        }
        
        return pReturned;
                        
    }
        
            
    public void newPlacement(int x, int y, char replace) {
                        
        if (replace == 'X' && y == 0) {
            if (turn == 'x') {
                turn = 'X';
            }
        else {
            turn = 'x';
        }
            pieces[x][y] = 'K';
            isK = true;
            pieceChanged = 0;
        }
        else if (replace == 'x' && y == 7) {
            if (turn == 'x') {
                turn = 'X';
            }
            else {
                turn = 'x';
            }
            pieces[x][y] = 'k';
            isk = true;
            pieceChanged = 0;
        }
        else {
            if (turn == 'x') {
                turn = 'X';
            }
            else {
                turn = 'x';
            }
            pieces[x][y] = replace;
        }
                        
    }
            
    public ArrayList<Board> nextBoards(ArrayList<Integer> current){
                    
        ArrayList<Board> listOfBoard = new ArrayList<Board>();
        
        int cur[] = new int[2];
        int tar[] = new int[2];
                    
        int bpx = current.get(0);
        int bpy = current.get(1);
        
        cur[0] = bpx;
        cur[1] = bpy;

        Board temp = new Board(this);

        tar[0] = bpx - 1;
        tar[1] = bpy - 1;
        
        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        tar[0] = bpx + 1;
        tar[1] = bpy - 1;
        temp = new Board(this);

        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        tar[0] = bpx - 1;
        tar[1] = bpy + 1;
        temp = new Board(this);

        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        
        tar[0] = bpx + 1;
        tar[1] = bpy + 1;
        temp = new Board(this);
        
        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        
        tar[0] = bpx + 2;
        tar[1] = bpy + 2;
        temp = new Board(this);

        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        tar[0] = bpx + 2;
        tar[1] = bpy - 2;
        temp = new Board(this);

        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        tar[0] = bpx - 2;
        tar[1] = bpy + 2;
        temp = new Board(this);
        

        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }
        
        tar[0] = bpx - 2;
        tar[1] = bpy - 2;
        temp = new Board(this);
        

        if (temp.doSwap(tar, cur)) {
            listOfBoard.add(new Board(temp));
        }             

        return listOfBoard;

    }
    

	public boolean isEqual(Board otherBoard) {
		
		char[][] temp = otherBoard.getPieces();
		
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != temp[i][j]) {
                	return false;
                }
            }
        }
        
        return true;
	}
                    
	
	public boolean isStale() {

		int counter = 0;

		//for (Board b : this.previousBoards) {
		//	if (b.isEqual(this)) {
		//		counter++;
		//	}
		//}
		
		if (counter == 3) {
			return true;
		}
		
		return false;
	}
	
}
