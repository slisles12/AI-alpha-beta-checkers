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

		
		char[][] temp = nextBoard.getPieces();
	 
		System.out.println("\n\n\n");
		
		for (int t = 0; t < 8; t++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(" " + temp[j][t] + " ");
			}
			System.out.println();
		}
        System.out.println(nextBoard.getTotalScore('x'));
        
		return nextBoard;
	}
	
	
	
	public Board computeBluePlayer(Board board) {

        
		Board nextBoard = AlphaBeta(board, 'X');

		char[][] temp = nextBoard.getPieces();
	 
		System.out.println("\n\n\n");
		
		for (int t = 0; t < 8; t++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(" " + temp[j][t] + " ");
			}
			System.out.println();
		}
		

		return nextBoard;
	
	}
	
	
	public Board AlphaBeta(Board board, char player) {
		
		 ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(board.getTurn());
         
		 HashMap<Integer, Board> nextBoards = new HashMap<Integer, Board>();                       
		
		 for (int i = 0; i < piecesPossible.size(); i++) {          
		     if (board.nextBoards(piecesPossible.get(i))!= null) {
		         for (Board b: board.nextBoards(piecesPossible.get(i))) {
		        	 
		        	 System.out.println(b.getTotalScore(player));
		        	 
		        	 nextBoards.put(MaxValue(b, 1, player, -1000, 1000), b);
		        	 
		         }
		     }
		 }

		 Integer maxKey = Collections.max(nextBoards.keySet());
		 
		 
		 System.out.println(maxKey + "maxKey");
		 
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
	
	public int MaxValue(Board board, int depth, char player, int alpha, int beta) {
		
		if (depth == 7 || board.getBlueScore() == 0 || board.getWhiteScore() == 0) {	
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
			                         
			int value = -1000; 
			 
			for (Board aBoard: nextBoards) {
				int temp = MinValue(aBoard, depth+1, player, alpha, beta);
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
	
	public int MinValue(Board board, int depth, char player, int alpha, int beta) {

		if (depth == 7 || board.getBlueScore() == 0 || board.getWhiteScore() == 0) {
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

			 int value = 1000;
			 
			 for (Board aBoard: nextBoards) {
					int temp = MaxValue(aBoard, depth+1, player, alpha, beta);
					
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

	
	
	
	
	
	
	/*GREEDY VERSION FOR TESTING
	public Board computeWhitePlayer(Board board) {
	
	 ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft('x');
	             
	 ArrayList<Board> nextBoards = new ArrayList<Board>();                       
	
	 for (int i = 0; i < piecesPossible.size(); i++) {          
	     if (board.nextBoards(piecesPossible.get(i))!= null) {
	         for (Board b: board.nextBoards(piecesPossible.get(i))) {
	        	 nextBoards.add(b);
	         }
	     }
	 }
	                          
	
	 Board highestBoard = new Board(board);
	 	
	 if (nextBoards.size() != 0) {
	     Random rand = new Random();
	
	     highestBoard = nextBoards.get(rand.nextInt(nextBoards.size()));
	
	     for (int i = 0; i < nextBoards.size(); i++) {
	         if (highestBoard.getBlueScore() > nextBoards.get(i).getBlueScore()) {
	             highestBoard = nextBoards.get(i);
	         }
	     }
	
	
	     board = new Board(highestBoard);
	     return board;
	     
	 }
	 else {
	     System.out.println("ERORRRRRRR WHITE");
	     return board;
	 }
	
	
	}*/
}
