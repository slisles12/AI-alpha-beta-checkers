import javafx.scene.paint.Color;

public class Board {
	
	private char[][] pieces;
	private int value;
	private char turn;
	private int xVal;
	private int XVal;

	public Board() {
		
		turn = 'x';
		
		this.pieces = new char[8][8];
		
		xVal = 12;
		XVal = 12;
		
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

	}
	
	public char[][] getPieces(){
		return pieces;
	}
	
	public char getPiece(int i, int j) {
		return pieces[i][j];
	}
	
	public int getValue(){
		
		value = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] == 'X') {
					value++;
				}
			}
		}
		
		return value;
	}
	
	
	public boolean trySwap(int[] target, int[] buttonPressed) {
		
		int bpx = buttonPressed[1];
		int bpy = buttonPressed[0];
		int targetx = target[1];
		int targety = target[0];
		
		char typeOfPiece = pieces[bpx][bpy];
		
		//handling single moves of reg pieces
		if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of reg pieces
		if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of reg pieces
		if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O')  &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of reg pieces
		if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes of reg pieces
		if ((bpx + 2 == targetx) && (bpy - 2 == targety) &&
				((pieces[targetx-1][targety+1] == 'x') || (pieces[targetx-1][targety+1] == 'k')) &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes of reg pieces
		if ((bpx - 2 == targetx) && (bpy - 2 == targety) &&
				((pieces[targetx+1][targety+1] == 'x') || (pieces[targetx+1][targety+1] == 'k')) &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes
		if ((bpx - 2 == targetx) && (bpy + 2 == targety) &&
				((pieces[targetx+1][targety-1] == 'X') || (pieces[targetx+1][targety-1] == 'K')) &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes of reg pieces
		if ((bpx + 2 == targetx) && (bpy + 2 == targety) &&
				((pieces[targetx-1][targety-1] == 'X') || (pieces[targetx-1][targety-1] == 'K')) &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		
		//handling single moves of k pieces
		if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of k pieces
		if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of k pieces
		if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O')  &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of k pieces
		if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		
		//handling single moves of k pieces
		if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		
		//handling single moves of k pieces
		if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		
		//handling single moves of K pieces
		if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of k pieces
		if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O')  &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling single moves of k pieces
		if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
				
		//handling jumps and takes of king pieces
		if ((bpx + 2 == targetx) && (bpy - 2 == targety) &&
				((pieces[targetx-1][targety+1] == 'x') || (pieces[targetx-1][targety+1] == 'k')) &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes with king pieces
		if ((bpx - 2 == targetx) && (bpy - 2 == targety) &&
				((pieces[targetx+1][targety+1] == 'x') || (pieces[targetx+1][targety+1] == 'k')) &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes with king pieces
		if ((bpx - 2 == targetx) && (bpy + 2 == targety) &&
				((pieces[targetx+1][targety-1] == 'x') || (pieces[targetx+1][targety-1] == 'k')) &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes with king pieces
		if ((bpx + 2 == targetx) && (bpy + 2 == targety) &&
				((pieces[targetx-1][targety-1] == 'x') || (pieces[targetx-1][targety-1] == 'k')) &&
				(pieces[bpx][bpy] == 'K') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		
		//handling jumps and takes of king pieces
		if ((bpx + 2 == targetx) && (bpy - 2 == targety) &&
				((pieces[targetx-1][targety+1] == 'X') || (pieces[targetx-1][targety+1] == 'K')) &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes with king pieces
		if ((bpx - 2 == targetx) && (bpy - 2 == targety) &&
				((pieces[targetx+1][targety+1] == 'X') || (pieces[targetx+1][targety+1] == 'K')) &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes with king pieces
		if ((bpx - 2 == targetx) && (bpy + 2 == targety) &&
				((pieces[targetx+1][targety-1] == 'X') || (pieces[targetx+1][targety-1] == 'K')) &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		//handling jumps and takes with king pieces
		if ((bpx + 2 == targetx) && (bpy + 2 == targety) &&
				((pieces[targetx-1][targety-1] == 'X') || (pieces[targetx-1][targety-1] == 'K')) &&
				(pieces[bpx][bpy] == 'k') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			updateScore();
			return true;
		}
		
		return false;
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
	
	public void updateScore() {
		xVal = 0;
		XVal = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] == 'x') {
					XVal++;
				}
				if (pieces[i][j] == 'k') {
					XVal+= 2;
				}
				if (pieces[i][j] == 'X') {
					xVal++;
				}
				if (pieces[i][j] == 'K') {
					xVal+= 2;
				}
			}
		}
		
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
		}
		else if (replace == 'x' && y == 7) {
			if (turn == 'x') {
				turn = 'X';
			}
			else {
				turn = 'x';
			}
			pieces[x][y] = 'k';
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
}
