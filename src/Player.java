import java.util.ArrayList;
import java.util.Random;

public class Player {
	    
	public Board computeWhitePlayer(Board board) {


		Board nextBoard = Minimax(board, 'x');

		
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
	
	
	
	public Board computeBluePlayer(Board board) {

		Board nextBoard = Minimax(board, 'X');

		
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
	
	
	public Board Minimax(Board board, char player) {
		
		System.out.println(board.getScore(player));
		
		 ArrayList<ArrayList<Integer>> piecesPossible = board.getPiecesLeft(player);
         
		 ArrayList<Board> nextBoards = new ArrayList<Board>();                       

		 System.out.println(piecesPossible.size());
		 for (int i = 0; i < piecesPossible.size(); i++) {          
		     if (board.nextBoards(piecesPossible.get(i))!= null) {
		         for (Board b: board.nextBoards(piecesPossible.get(i))) {
		        	 nextBoards.add(b);
		         }
		     }
		 }
		 

		Board maxBoard = new Board(board);

        
		for (Board aBoard: nextBoards) {
			if ((MinValue(aBoard, 1, player) >= maxBoard.getScore(player)) && !board.isEqual(aBoard)) {
				maxBoard = aBoard;
			}
		}
		
		System.out.println(maxBoard.getScore(player));

		return maxBoard;

	}
	
	public int MaxValue(Board board, int depth, char player) {
		
		if (depth == 5 || board.getBlueScore() == 0 || board.getWhiteScore() == 0) {
			return board.getScore(player);
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
			                          
			
			 Board maxBoard = new Board(board);

			for (Board aBoard: nextBoards) {
				if (MinValue(aBoard, depth+1, player) >= maxBoard.getScore(player)) {
					maxBoard = aBoard;
				}
			}


			return maxBoard.getScore(player);
		}
	}
	
	public int MinValue(Board board, int depth, char player) {

		
		if (depth == 5 || board.getBlueScore() == 0 || board.getWhiteScore() == 0) {
			return board.getScore(player);
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

			 Board minBoard = new Board(board);

			for (Board aBoard: nextBoards) {
				if (MaxValue(aBoard, depth+1, player) <= minBoard.getScore(player)) {
					minBoard = aBoard;
				}
			}


			return minBoard.getScore(player);
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
