/***************************************************************/
/* Aakif Aslam and Manning Zhao                                */
/* CS-481, Spring 2020                                         */
/* Lab Assignment 2                                            */
/* Move Class: Represents a move for a fox or geese            */
/***************************************************************/

/***************************************************************/
/* MovePerson Enum: Enum for the object that is moving on the  */
/*                  board, there are a total of five, one fox  */
/*                  and four geese                             */
/***************************************************************/
enum MovePerson{
    Goose1,     //ordinal = 0
    Goose2,     //ordinal = 1
    Goose3,     //ordinal = 2
    Goose4,     //ordinal = 3
    Fox         //ordinal = 4
}

/***************************************************************/
/* Move Dir Enum: Enum for the four directions                 */
/***************************************************************/
enum MoveDir {
    ForwardLeft,
    BackwardLeft,
    ForwardRight,
    BackwardRight
}

/***************************************************************/
/* Move Class: Represents a move for a fox or geese            */
/***************************************************************/
public class Move {
    MovePerson person;  //The object to move
    MoveDir dir;        //The direction to move the object in

    /***************************************************************/
    /* Method: Move                                                */
    /* Purpose: Default Constructor                                */
    /* Parameters: MovePerson person: The object moved             */
    /*             MoveDir dir: The direction the object moved in  */
    /* Returns: None                                               */
    /***************************************************************/
    Move(MovePerson person, MoveDir dir){
        this.person = person;
        this.dir = dir;
    }

    /***************************************************************/
    /* Method: toString                                            */
    /* Purpose: Default Constructor                                */
    /* Parameters: None                                            */
    /* Returns: String: Combines the object moved and direction it */
    /*                  moved in as a string                       */
    /***************************************************************/
    @Override
    public String toString() {
        return "Move " + person.name() + " in direction " + dir.name();
    }
}
