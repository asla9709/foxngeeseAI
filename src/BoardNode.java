import java.util.ArrayList;
import java.util.Arrays;

enum BoardSpot{
    Invalid,
    Empty,
    Goose,
    Fox
}

public class BoardNode {
    BoardNode parent;
    BoardSpot[][] boardstate = new BoardSpot[8][8];
    ArrayList<BoardNode> children = new ArrayList<>();

    //Initial constructor, make a starter board
    BoardNode(){
        this.parent = null;

        //initialize checkerboard pattern
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
               if ((row + col) % 2 == 0){
                   boardstate[row][col] = BoardSpot.Invalid;
               }
               else{
                   boardstate[row][col] = BoardSpot.Empty;
               }
            }
        }

        //fox is in second black square from the left on the bottom row of the board
        boardstate[7][2] = BoardSpot.Fox;

        //top row has geese on every valid square
        for(int col = 0; col < 8; col++){
            if (boardstate[0][col] == BoardSpot.Empty){
                boardstate[0][col] = BoardSpot.Goose;
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                output.append(switch (boardstate[row][col]) {
                    case Goose -> "g";
                    case Fox -> "f";
                    case Empty -> "■";
                    case Invalid -> "□";
                });
                output.append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }
}
