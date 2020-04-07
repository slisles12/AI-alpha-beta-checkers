import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;

public class Board {

    private char[][] pieces;
    private int value;
    private char turn;
    private int xVal;
    private int XVal;

    public Board() {
        
        turn = 'X';
        
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
              
    public Board(Board board) {
                             
        char[][] grid = board.getPieces();
        
        turn = board.getTurn();
        
        this.pieces = new char[8][8];
        
        xVal = 12;
        XVal = 12;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = grid[i][j];
            }
        }
        
        updateScore();

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
        if ((bpx + 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety+1] == 'x') || (pieces[targetx-1][targety+1] == 'k')) &&
            (pieces[bpx][bpy] == 'X') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
        if ((bpx + 2 == targetx) && (bpy - 2 == targety) && (pieces[targetx][targety] == 'O') &&
            ((pieces[targetx-1][targety+1] == 'x') || (pieces[targetx-1][targety+1] == 'k')) &&
            (pieces[bpx][bpy] == 'K') && (turn == 'X')) {
            char replace = pieces[bpx][bpy];
            newPlacement(targetx, targety, replace);
            pieces[targetx-1][targety+1] = 'O';
            pieces[bpx][bpy] = 'O';
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
            updateScore();
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
                updateScore();
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
                updateScore();
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
                updateScore();
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
                updateScore();
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
                updateScore();
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
            
    public ArrayList<Board> nextBoards(ArrayList<Integer> current){
                    
        ArrayList<Board> listOfBoard = new ArrayList<Board>();
        
        int cur[] = new int[2];
        int tar[] = new int[2];
                    
        int bpx = current.get(0);
        int bpy = current.get(1);
        

        System.out.println(bpx + " " + bpx);

        
        cur[0] = bpx;
        cur[1] = bpy;

        Board temp = new Board(this);

        tar[0] = bpx - 1;
        tar[1] = bpy - 1;
        
        
        if (this.turn == 'x') {
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
        
        return null;
    }
                    
    
}
