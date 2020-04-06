import javafx.scene.paint.Color;

public class Board {
	
	private char[][] pieces;
	private int value;

	public Board() {
		
		this.pieces = new char[8][8];
		
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
	
	
	public boolean trySwap(int[] target, int[] buttonPressed, char turn) {
		
		int bpx = buttonPressed[1];
		int bpy = buttonPressed[0];
		int targetx = target[1];
		int targety = target[0];
		
		char typeOfPiece = pieces[bpx][bpy];
		
		if ((bpx - 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		if ((bpx - 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		if ((bpx + 1 == targetx) && (bpy - 1 == targety) &&
				(pieces[targetx][targety] == 'O')  &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		if ((bpx + 1 == targetx) && (bpy + 1 == targety) &&
				(pieces[targetx][targety] == 'O') &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		
		if ((bpx + 2 == targetx) && (bpy - 2 == targety) &&
				(pieces[targetx-1][targety+1] == 'x') &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		if ((bpx - 2 == targetx) && (bpy - 2 == targety) &&
				(pieces[targetx+1][targety+1] == 'x') &&
				(pieces[bpx][bpy] == 'X') && (turn == 'X')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety+1] = 'O';
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		if ((bpx - 2 == targetx) && (bpy + 2 == targety) &&
				(pieces[targetx+1][targety-1] == 'X') &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx+1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		if ((bpx + 2 == targetx) && (bpy + 2 == targety) &&
				(pieces[targetx-1][targety-1] == 'X') &&
				(pieces[bpx][bpy] == 'x') && (turn == 'x')) {
			char replace = pieces[bpx][bpy];
			newPlacement(targetx, targety, replace);
			pieces[targetx-1][targety-1] = 'O';
			pieces[bpx][bpy] = 'O';
			return true;
		}
		
		System.out.println("hey!");
		
		return false;
	}
	
	
	public void newPlacement(int x, int y, char replace) {
		
		if (replace == 'X' && y == 0) {
			pieces[x][y] = 'Q';
		}
		else if (replace == 'x' && y == 7) {
			pieces[x][y] = 'q';
		}
		else {
			pieces[x][y] = replace;
		}
		
	}
}
