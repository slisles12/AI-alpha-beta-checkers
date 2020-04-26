import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class Player {
	
	public Player(Board b) {

	}
	
	public Board computeWhitePlayer(Board board) { 
        
		Board nextBoard = AlphaBeta(board, 'x');
		return nextBoard;
	}
	
	
	
	public Board computeBluePlayer(Board board) {
        
		Board nextBoard = AlphaBeta(board, 'X');
		return nextBoard;
	
	}
	
	
	public Board AlphaBeta(Board board, char player) {
		
		 ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());
         
		 HashMap<Double, Board> nextBoards = new HashMap<Double, Board>();                       
		
		 for (int i = 0; i < piecesPossible.size(); i++) {          
		     if (board.nextBoards(piecesPossible.get(i))!= null) {
		         for (Board b: board.nextBoards(piecesPossible.get(i))) {
		        	 
		        	 double value = MaxValue(b, 1, player, -1000.0, 1000.0);
		        	 
		        	 System.out.println("value chosen: " + value);
		        	 
		        	 nextBoards.put(value, b);
		        	 
		         }
		     }
		 }

		 Double maxKey = Collections.max(nextBoards.keySet());
		 
		 
		 System.out.println(maxKey + ": maxKey");
		 
		 char[][] temp =  nextBoards.get(maxKey).getPieces();
	 
		System.out.println("\n\n\n");
		
		for (int t = 0; t < 8; t++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(" " + temp[j][t] + " ");
			}
			System.out.println();
		}
		
		 return nextBoards.get(maxKey);

	}
	
	public double MaxValue(Board board, int depth, char player, Double alpha, Double beta) {
		
		if (depth == 6 || board.getBlueScore() == 0 || board.getWhiteScore() == 0) {	
			return board.getTotalScore(player);
		}
		else {

			 ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());
	         
			 ArrayList<Board> nextBoards = new ArrayList<Board>();                       

			 
			 
			 for (int i = 0; i < piecesPossible.size(); i++) {          
			     if (board.nextBoards(piecesPossible.get(i))!= null) {
			         for (Board b: board.nextBoards(piecesPossible.get(i))) {			 
			        	 nextBoards.add(b);
			         }
			     }
			 }
			                         
			Double value = (double) -1000; 
			 
			for (Board aBoard: nextBoards) {
				Double temp = MinValue(aBoard, depth+1, player, alpha, beta);
				if (temp > value) {
					value = temp;
				}
				
				if (value >= beta) {
					return value;
				}
				
				if (value > alpha) {
					alpha = value;
				}
			}
			 
			return value;

		}
	}
	
	public double MinValue(Board board, int depth, char player, Double alpha, Double beta) {

		if (depth == 6 || board.getBlueScore() == 0 || board.getWhiteScore() == 0) {		
			return board.getTotalScore(player);
			
		}
		else {
			 ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());
	         
			 ArrayList<Board> nextBoards = new ArrayList<Board>();                       

			 for (int i = 0; i < piecesPossible.size(); i++) {          
			     if (board.nextBoards(piecesPossible.get(i))!= null) {
			         for (Board b: board.nextBoards(piecesPossible.get(i))) {
			        	 nextBoards.add(b);
			         }
			     }
			 }

			 Double value = (double) 1000;
			 
			 for (Board aBoard: nextBoards) {
					Double temp = MaxValue(aBoard, depth+1, player, alpha, beta);
					
					if (temp < value) {
						value = temp;
					}
					
					if (value <= alpha) {
						return value;
					}
					
					if (value < beta) {
						beta = value;
					}
				}
			
				return value;
		}
	}
}
