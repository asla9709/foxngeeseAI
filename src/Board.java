/***************************************************************/
/* Aakif Aslam and Manning Zhao                                */
/* CS-481, Spring 2020                                         */
/* Lab Assignment 2                                            */
/* Board Class: Generate the game board. Can check the fox and */
/*              geese locations, valid moves, wins/loses.      */
/***************************************************************/


public class Board{
    Location[] GeeseLocs;   //The location of all the geese
    Location FoxLoc;        //The location of the fox

    /***************************************************************/
    /* Method: Board                                               */
    /* Purpose: Default Constructor, make a starter board          */
    /* Parameters: None                                            */
    /* Returns: None                                               */
    /***************************************************************/
    Board(){
        FoxLoc = new Location(7,2);

        GeeseLocs = new Location[4];
        for (int goose = 0; goose < 4; goose++) {
            GeeseLocs[goose] = new Location(0, (goose*2) + 1);
        }
    }

    /***************************************************************/
    /* Method: Board                                               */
    /* Purpose: Copy Constructor                                   */
    /* Parameters: Board originalBoard: The board to make a copy of*/
    /* Returns: None                                               */
    /***************************************************************/
    Board(Board originalBoard){
        GeeseLocs = new Location[4];
        for(int goose = 0; goose < 4; goose++){
            GeeseLocs[goose] = new Location(originalBoard.GeeseLocs[goose]);
        }

        FoxLoc = new Location(originalBoard.FoxLoc);
    }

    /***************************************************************/
    /* Method: printSpot                                           */
    /* Purpose: Returns a char to represent the item at a location */
    /*          on the board                                       */
    /* Parameters: Location loc: A location on the game board      */
    /* Returns: char: The item at a location on the game board     */
    /***************************************************************/
    char printSpot(Location loc){
        //Checkerboard Pattern
        if(( loc.row + loc.col)%2 == 0){
            return '□';
        }
        //Fox location
        if (FoxLoc.equals(loc)){
            return 'f';
        }
        //Geese Locations
        for (int goose = 0; goose < 4; goose++) {
            if(GeeseLocs[goose].equals(loc)){
                return Integer.toString(goose + 1).charAt(0);
            }
        }

        //Empty Spot
        return '■';
    }

    /***************************************************************/
    /* Method: toString                                            */
    /* Purpose: Returns the game board as a string                 */
    /* Parameters: None                                            */
    /* Returns: String: The string representation of the game board*/
    /***************************************************************/
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

    /***************************************************************/
    /* Method: checkWinFox                                         */
    /* Purpose: Check the game board to see if the fox won         */
    /* Parameters: None                                            */
    /* Returns: Boolean: True if the fox reached the top row of    */
    /*                   the game board, false otherwise           */
    /***************************************************************/
    public boolean checkWinFox(){
       return (FoxLoc.row == 0);
    }

    /***************************************************************/
    /* Method: checkWinGoose                                       */
    /* Purpose: Check the game board to see if the geese won       */
    /* Parameters: None                                            */
    /* Returns: Boolean: True if the fox cannot make any valid     */
    /*                   moves, false otherwise                    */
    /***************************************************************/
    public boolean checkWinGoose(){
        //check every direction to see if the fox can move
        for(MoveDir dir: MoveDir.values()){
            if (isValidMoveFox(dir)) return false;
        }
        //If the fox cannot move, the geese have won
        return true;
    }

    /***************************************************************/
    /* Method: isValidMove                                         */
    /* Purpose: Check if a move on the board is valid              */
    /* Parameters: Move move: The move to be validated             */
    /* Returns: Boolean: True if the move is valid, false otherwise*/
    /***************************************************************/
    public boolean isValidMove(Move move){
        if (move.person == MovePerson.Fox) {
            return isValidMoveFox(move.dir);
        }
        return isValidMoveGoose(move.dir, move.person.ordinal());
    }

    /***************************************************************/
    /* Method: doMove                                              */
    /* Purpose: Check the game board to see if the move is valid   */
    /*          and only make the move if it is valid              */
    /* Parameters: Move move: The move to be made on the game board*/
    /* Returns: Boolean: True if the move is made on the board,    */
    /*                   false otherwise                           */
    /***************************************************************/
    public boolean doMove(Move move){
       if (move.person == MovePerson.Fox){
           return moveFox(move.dir);
       }
       return moveGoose(move.dir, move.person.ordinal());
    }

    /***************************************************************/
    /* Method: isValidMoveFox                                      */
    /* Purpose: Check if it is valid for the fox to move on the    */
    /*          game board in a certain direction                  */
    /* Parameters: MoveDir dir: The direction to move in           */
    /* Returns: Boolean: True if the fox can move in that direction*/
    /*                   on the game board, false otherwise        */
    /***************************************************************/
    public boolean isValidMoveFox(MoveDir dir){
        //generate the new location after a move in the specified direction
        Location new_loc;
        new_loc = switch (dir){
            case ForwardLeft -> new Location(FoxLoc.row - 1, FoxLoc.col - 1);
            case BackwardLeft -> new Location(FoxLoc.row + 1, FoxLoc.col - 1);
            case ForwardRight -> new Location(FoxLoc.row - 1, FoxLoc.col + 1);
            case BackwardRight -> new Location(FoxLoc.row + 1, FoxLoc.col + 1);
        };

        //check if the new location is within the game board
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

    /***************************************************************/
    /* Method: moveFox                                             */
    /* Purpose: Move the fox in a specified direction on the game  */
    /*          board if that is a valid move                      */
    /* Parameters: MoveDir dir: The direction to move in           */
    /* Returns: Boolean: True if the fox moved, false otherwise    */
    /***************************************************************/
    public Boolean moveFox(MoveDir dir){
        if(!isValidMoveFox(dir)) return false;

        //make the fox move
        FoxLoc = switch(dir){
            case ForwardLeft -> new Location(FoxLoc.row - 1, FoxLoc.col - 1);
            case BackwardLeft -> new Location(FoxLoc.row + 1, FoxLoc.col - 1);
            case ForwardRight -> new Location(FoxLoc.row - 1, FoxLoc.col + 1);
            case BackwardRight -> new Location(FoxLoc.row + 1, FoxLoc.col + 1);
        };
        return true;
    }

    /***************************************************************/
    /* Method: isValidMoveGoose                                    */
    /* Purpose: Check if a certain goose can move on the game      */
    /*          board in the specified direction                   */
    /* Parameters: MoveDir dir: The direction to move in           */
    /*             int myGoose: The goose to be moved              */
    /* Returns: Boolean: True if that goose can move in that       */
    /*                   direction, false otherwise                */
    /***************************************************************/
    public boolean isValidMoveGoose(MoveDir dir, int myGoose){
        //generate the new location after a move in the specified direction
        Location new_loc = new Location(0,0);
        Location goose_loc = GeeseLocs[myGoose];
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
            case BackwardLeft:
                return false;
        }

        //check that it is within the game board
        if (new_loc.row < 0 || new_loc.row > 7){
            return false;
        }
        if (new_loc.col < 0 || new_loc.col > 7){
            return false;
        }

        //check that no geese are there
        for (int goose = 0; goose < 4; goose++) {
            if(goose == myGoose) continue;
            if(GeeseLocs[goose].equals(new_loc)) {
                return false;
            }
        }

        //check that the fox is not there
        if(FoxLoc.equals(new_loc)){
            return false;
        }

        return true;
    }

    /***************************************************************/
    /* Method: moveGoose                                           */
    /* Purpose: Move a certain goose in a specified direction      */
    /*          on the game board if that is a valid move          */
    /* Parameters: MoveDir dir: The direction to move in           */
    /*             int myGoose: The goose to be moved              */
    /* Returns: Boolean: True if the goose moved, false otherwise  */
    /***************************************************************/
    public Boolean moveGoose(MoveDir dir, int myGoose){
        if(!isValidMoveGoose(dir, myGoose)) return false;

        Location new_loc = new Location(0,0);
        Location goose_loc = GeeseLocs[myGoose];
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

        GeeseLocs[myGoose] = new_loc;
        return true;
    }
}
