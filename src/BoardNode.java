import java.util.ArrayList;
import java.util.Arrays;

enum BoardSpot{
    Invalid,
    Empty,
    Goose,
    Fox
}

class Location{
    int row;
    int col;
    Location(int row, int col){
        this.row = row;
        this.col = col;
    }
    public boolean equals(Location b){
        return (this.row == b.row && this.col == b.col);
    }
}

public class BoardNode {
    BoardNode parent;
    ArrayList<BoardNode> children = new ArrayList<>();

    Location[] GeeseLocs;
    Location FoxLoc;

    //Initial constructor, make a starter board
    BoardNode(){
        this.parent = null;

        FoxLoc = new Location(7,2);

        GeeseLocs = new Location[4];
        for (int goose = 0; goose < 4; goose++) {
            GeeseLocs[goose] = new Location(0, (goose*2) + 1);
        }
    }

    char printSpot(Location s){
        if(( s.row + s.col)%2 == 0){
            return '□';
        }
        if (FoxLoc.equals(s)){
            return 'f';
        }
        for (int goose = 0; goose < 4; goose++) {
            if(GeeseLocs[goose].equals(s)){
                return Integer.toString(goose + 1).charAt(0);
            }
        }

        return '■';
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Location loc = new Location(row, col);
                output.append(printSpot(loc));
                output.append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }

    public Boolean checkWinFox(){
       return (FoxLoc.row == 0);
    }

    public boolean checkWinGoose(){
        //check every direction
        for(MoveDir dir: MoveDir.values()){
            if (isValidMoveFox(dir)) return false;
        }
        return true;
    }

    public Boolean isValidMoveFox(MoveDir dir){
        Location new_loc;
        switch (dir){
            case ForwardLeft:
            	new_loc = new Location(FoxLoc.row - 1, FoxLoc.col - 1);
            	break;
            case BackwardLeft:
            	new_loc = new Location(FoxLoc.row + 1, FoxLoc.col - 1);
            	break;
            case ForwardRight:
            	new_loc = new Location(FoxLoc.row - 1, FoxLoc.col + 1);
            	break;
            case BackwardRight: 
            	new_loc = new Location(FoxLoc.row + 1, FoxLoc.col + 1);
            	break;
            default:
            	//forward right
            	new_loc = new Location(FoxLoc.row - 1, FoxLoc.col + 1);
            	break;
        }

        //check that it is valid
        if (new_loc.row < 0 || new_loc.row > 7){
            return false;
        }
        if (new_loc.col < 0 || new_loc.col > 7){
            return false;
        }

        //check that no geese are there
        for (int goose = 0; goose < 4; goose++) {
            if(GeeseLocs[goose].equals(new_loc)) {
                return false;
            }
        }

        return true;
    }

    public Boolean moveFox(MoveDir dir){
        if(!isValidMoveFox(dir)) return false;

        switch (dir){
            case ForwardLeft:
            	 FoxLoc = new Location(FoxLoc.row - 1, FoxLoc.col - 1);
            	 break;
            case BackwardLeft:
            	 FoxLoc = new Location(FoxLoc.row + 1, FoxLoc.col - 1);
            	 break;
            case ForwardRight:
            	 FoxLoc = new Location(FoxLoc.row - 1, FoxLoc.col + 1);
            	 break;
            case BackwardRight:
            	 FoxLoc = new Location(FoxLoc.row + 1, FoxLoc.col + 1);
            	 break;
        }
        return true;
    }
    
    public Boolean moveGoose(MoveDir dir, int mygoose){
        Location new_loc = new Location(0,0);
        Location goose_loc = GeeseLocs[mygoose];
        switch (dir){
            case ForwardLeft:
                new_loc.row = goose_loc.row + 1;
                new_loc.col = goose_loc.col - 1;
                break;
            case ForwardRight:
                new_loc.row = goose_loc.row + 1;
                new_loc.col = goose_loc.col + 1;
                break;
            case BackwardRight:
                return false;
            case BackwardLeft:
                return false;
        }

        //check that it is valid
        if (new_loc.row < 0 || new_loc.row > 7){
            return false;
        }
        if (new_loc.col < 0 || new_loc.col > 7){
            return false;
        }

        //check that no geese are there
        for (int goose = 0; goose < 4; goose++) {
            if(goose == mygoose) continue;
            if(GeeseLocs[goose].equals(new_loc)) {
                return false;
            }
        }

        //check that fox aint there
        if(FoxLoc.equals(new_loc)){
            return false;
        }

        GeeseLocs[mygoose] = new_loc;
        return true;
    }
}
