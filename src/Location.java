/***************************************************************/
/* Method: Location                                            */
/* Purpose: Enum to represent the type of spots on the board   */
/* Parameters: None                                            */
/* Returns: None                                               */
/***************************************************************/
public class Location{
    int row;    //row number
    int col;    //column number

    /***************************************************************/
    /* Method: Location                                            */
    /* Purpose: Default Constructor                                */
    /* Parameters: integer row: The row number of new location     */
    /*             integer col: The column number of new location  */
    /* Returns: None                                               */
    /***************************************************************/
    Location(int row, int col){
        this.row = row;
        this.col = col;
    }

    /***************************************************************/
    /* Method: Location                                            */
    /* Purpose: Copy Constructor                                   */
    /* Parameters: Location locA: The location to copy             */
    /* Returns: None                                               */
    /***************************************************************/
    Location(Location locA){
        this.row = locA.row;
        this.col = locA.col;
    }

    /***************************************************************/
    /* Method: equals                                              */
    /* Purpose: Check if two locations are the same                */
    /* Parameters: Location locB: The other location to compare to */
    /* Returns: Boolean: True if the other location is the same as */
    /*                   the current location, false otherwise     */
    /***************************************************************/
    public boolean equals(Location locB){
        return (this.row == locB.row && this.col == locB.col);
    }
}