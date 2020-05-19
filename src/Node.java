/***************************************************************/
/* Aakif Aslam and Manning Zhao                                */
/* CS-481, Spring 2020                                         */
/* Lab Assignment 2                                            */
/* Node Class: Store the information of a node on the Minimax  */
/*             search tree.                                    */
/***************************************************************/
import java.util.ArrayList;

public class Node {
    Board board;    //the current game board
    Move move;      //the move made at this node

    boolean isMax;  //decides whether the current node is min or max

    int value;      //the value of the node

    ArrayList<Node> children = new ArrayList<>();   //the list of child nodes

    /***************************************************************/
    /* Method: Node                                                */
    /* Purpose: Constructor, create a new node                     */
    /* Parameters: Board board: The game board at this node        */
    /*             boolean isMax: Decides whether the current node */
    /*                            is a max node or a min node      */
    /* Returns: None                                               */
    /***************************************************************/
    Node(Board board, boolean isMax) {
        this.isMax = isMax;
        this.board = board;
    }

    /***************************************************************/
    /* Method: Node                                                */
    /* Purpose: Constructor, create a copy of the parent node and  */
    /*          then perform a move on the game board              */
    /* Parameters: Node parent: The parent of the current node     */
    /*             Move newMove: The move to be made on the board  */
    /* Returns: None                                               */
    /***************************************************************/
    Node(Node parent, Move newMove) {
        this.move = newMove;
        this.board = new Board(parent.board); // copy board
        this.isMax = !parent.isMax;

        this.board.doMove(newMove);
    }

    /***************************************************************/
    /* Method: getValidMoves                                       */
    /* Purpose: Get all possible valid moves of the current player */
    /*          on the current node's game board                   */
    /*          on the game board of the current node.             */
    /* Parameters: boolean isFox: Whether the computer is playing  */
    /*                            as the fox or the geese          */
    /* Returns: ArrayList<Move>: A list of valid possible moves    */
    /***************************************************************/
    ArrayList<Move> getValidMoves(boolean isFox) {
        ArrayList<Move> validMoves = new ArrayList<>();
        if (isFox) {
            //check each move
            for (MoveDir d : MoveDir.values()) {
                //If the fox can move in that direction, add it to list
                Move newMove = new Move(MovePerson.Fox, d);
                if (board.isValidMove(newMove)) {
                    validMoves.add(newMove);
                }
            }
        }
        else {
            //For each 'goose'...
            for (MovePerson p : MovePerson.values()) {
                if (p == MovePerson.Fox) continue; //Not the fox, only geese

                //For each direction...
                for (MoveDir d : MoveDir.values()) {
                    //If the goose can move in that direction, add it to list
                    Move newMove = new Move(p, d);
                    if (board.isValidMove(newMove)) {
                        validMoves.add(newMove);
                    }
                }
            }
        }
        return validMoves;
    }

    /***************************************************************/
    /* Method: getChildren                                         */
    /* Purpose: Create a child node for each possible valid moves  */
    /*          on the game board of the current node.             */
    /* Parameters: boolean isFox: Whether the computer is playing  */
    /*                            as the fox or the geese          */
    /* Returns: ArrayList<Node>: A list of child nodes             */
    /***************************************************************/
    ArrayList<Node> getChildren(boolean isFox) {
        ArrayList<Move> validMoves = getValidMoves(isFox);
        for (Move move : validMoves) {
            makeChild(move);
        }
        return children;
    }

    /***************************************************************/
    /* Method: makeChild                                           */
    /* Purpose: Create a child node of the current node. This      */
    /*          child node performs a new move on the game board.  */
    /* Parameters: Move newMove: The move to be made on the board  */
    /* Returns: Node newNode: The new child node                   */
    /***************************************************************/
    Node makeChild(Move newMove) {
        Node newNode = new Node(this, newMove);
        children.add(newNode);
        return newNode;
    }
}