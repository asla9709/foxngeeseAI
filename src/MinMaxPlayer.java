/***************************************************************/
/* Aakif Aslam and Manning Zhao                                */
/* CS-481, Spring 2020                                         */
/* Lab Assignment 2                                            */
/* MinMaxPlayer Class: Generate a computer AI player for fox   */
/*                     and geese. Depending on the search depth*/
/*                     allowed by the user, it could use random*/
/*                     move or a move generated from a minimax */
/*                     search using alpha beta pruning.        */
/***************************************************************/
import java.util.ArrayList;
import java.util.Random;

public class MinMaxPlayer {
    boolean isFox;  //decides whether the current player is fox or geese
    int maxDepth;   //the max depth allowed for the minimax search
    Random ran;     //random number generator

    //Configuration
    int ROW_MULTIPLIER = 100;       //Bonus that a fox gets per row away from bottom
    int GOOSE_ATTACK_VALUE = 20;    //Each attacking (adjacent) goose subtracts this to the eval score
    int PAST_THE_GOOSE_BONUS = 15;   //Each goose the fox passes adds to the eval score
    int EDGE_PENALTY_VALUE = 10;    //Subtracts from the eval score if fox is on col 0 or col 7

    /***************************************************************/
    /* Method: MinMaxPlayer                                        */
    /* Purpose: Constructor                                        */
    /* Parameters: boolean isFox: Whether the computer is playing  */
    /*                            as the fox(MAX) or the geese(MIN)*/
    /*             integer maxDepth: The depth of the search       */
    /* Returns: None                                               */
    /***************************************************************/
    MinMaxPlayer(boolean isFox, int maxDepth) {
        this.maxDepth = maxDepth;
        this.isFox = isFox;

        ran = new Random();
    }

    /***************************************************************/
    /* Method: MinMaxPlayer                                        */
    /* Purpose: Constructor                                        */
    /* Parameters: boolean isFox: Whether the computer is playing  */
    /*                            as the fox(MAX) or the geese(MIN)*/
    /*             integer maxDepth: The depth of the search       */
    /*             integer gav: Goose attack value                 */
    /*             integer ptgb: Pass the goose bonus value        */
    /*             integer epv: Edge negative value                */
    /* Returns: None                                               */
    /***************************************************************/
    MinMaxPlayer(boolean isFox, int maxDepth, int rm, int gav, int ptgb, int epv) {
        this.maxDepth = maxDepth;
        this.isFox = isFox;

        this.ROW_MULTIPLIER = rm;
        this.GOOSE_ATTACK_VALUE = gav;
        this.PAST_THE_GOOSE_BONUS = ptgb;
        this.EDGE_PENALTY_VALUE = epv;

        ran = new Random();
    }

    /***************************************************************/
    /* Method: doAIStuff                                           */
    /* Purpose: Perform a random move is maxDepth is 0, otherwise  */
    /*          perform a minimax search using alpha beta pruning. */
    /* Parameters: Board board: The current game board             */
    /* Returns: Move:                                              */
    /***************************************************************/
    Move runSearch(Board board) {
        if (maxDepth == 0) {
            return randomMove(board);
        }

        return AlphaBetaSearch(board);
    }

    /***************************************************************/
    /* Method: AlphaBetaSearch                                     */
    /* Purpose: Perform a minimax search using alpha beta pruning. */
    /* Parameters: Board board: The current game board             */
    /* Returns: Move:                                              */
    /***************************************************************/
    Move AlphaBetaSearch(Board board) {
        Node n = new Node(board, isFox);
        int v;
        if (isFox) {
            //if we are fox, try to maximize value
            v = Max_Value(n, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth*2);
        } else {
            //geese try to minimize value
            v = Min_Value(n, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth*2);
        }

        //get move with same value as v
        for (Node child : n.children) {
            if (child.value == v) {
                return child.move;
            }
        }

        //if we can't find a move with the returned value, return null (Not expected)
        return null;
    }

    /***************************************************************/
    /* Method: cleanupNode                                         */
    /* Purpose: delete a node's children (if necessary)            */
    /*          and return the value                               */
    /* Parameters: Node node: node to clean up                     */
    /*             integer depth: The current depth of the search  */
    /* Returns: integer: The value of node                         */
    /***************************************************************/
    private int cleanupNode(Node node, int depth) {
        if (depth != maxDepth) {
            //clean children if node is not a top level node
            //if a node has a value assigned, we no longer have a use for its children, unless..
            //it is a top level node.
            //we need the children of the top level node to select which move to make once the search completes.
            node.children.clear();
            //clearing unneeded nodes keeps the space complexity of the algorithm to O(bm)
        }
        return node.value;
    }

    /***************************************************************/
    /* Method: Max_Value                                           */
    /* Purpose: Find the max value by looking at its child nodes   */
    /* Parameters: node maxNode: A max node                        */
    /*             integer alpha: The alpha value(highest)         */
    /*             integer beta: The beta value(lowest)            */
    /*             integer depth: The remaining depth to search for*/
    /* Returns: integer: The max value of the current node         */
    /***************************************************************/
    int Max_Value(Node maxNode, int alpha, int beta, int depth) {
        //Check end condition and return board evaluation
        if (depth == 0 || maxNode.board.checkWinGoose() || maxNode.board.checkWinFox()) {
            maxNode.value = evalFunc(maxNode.board);
            return maxNode.value;
        }

        //run max search on children
        maxNode.value = Integer.MIN_VALUE;
        ArrayList<Node> children = maxNode.getChildren(true); //Max Nodes can only do fox moves
        for (Node child : children) {
            maxNode.value = Math.max(maxNode.value, Min_Value(child, alpha, beta, depth - 1));
            if (maxNode.value >= beta) {
                return cleanupNode(maxNode, depth);
            }
            alpha = Math.max(alpha, maxNode.value);
        }

        //set node values and return
        return cleanupNode(maxNode, depth);
    }

    /***************************************************************/
    /* Method: Min_Value                                           */
    /* Purpose: Find the min value by looking at its child nodes   */
    /* Parameters: node minNode: A min node                        */
    /*             integer alpha: The alpha value(highest)         */
    /*             integer beta: The beta value(lowest)            */
    /*             integer depth: The remaining depth to search for*/
    /* Returns: integer: The min value of the current node         */
    /***************************************************************/
    int Min_Value(Node minNode, int alpha, int beta, int depth) {
        //Check end condition and return board evaluation
        if (depth == 0 || minNode.board.checkWinGoose() || minNode.board.checkWinFox()) {
            minNode.value = evalFunc(minNode.board);
            return minNode.value;
        }

        //run min search on children
        minNode.value = Integer.MAX_VALUE;
        ArrayList<Node> children = minNode.getChildren(false); //Min Nodes can only do goose moves
        for (Node child : children) {
            minNode.value = Math.min(minNode.value, Max_Value(child, alpha, beta, depth - 1));
            if (minNode.value <= alpha) {
                return cleanupNode(minNode, depth);
            }
            beta = Math.min(beta, minNode.value);
        }

        return cleanupNode(minNode, depth);
    }


    /***************************************************************/
    /* Method: evalFunc                                            */
    /* Purpose: Find heuristic value of the current board          */
    /* Parameters: Board board: The current game board             */
    /* Returns: integer: The heuristic value of the current board. */
    /***************************************************************/
    int evalFunc(Board board) {

        //Check for win conditions
        if (board.checkWinFox()) {
            return Integer.MAX_VALUE;
        }
        if (board.checkWinGoose()) {
            return Integer.MIN_VALUE;
        }

        //Calculate sum of distance from each goose
        Location fox = board.FoxLoc;

        int distSum = 0;
        for (Location goose : board.GeeseLocs) {
            //calculate euclidean distance
            int dist = Math.round((float) Math.sqrt(Math.pow(Math.abs(goose.row - fox.row), 2) + Math.pow(Math.abs(goose.col - fox.col), 2)));
            distSum += dist;
        }

        //Calculate row bonus for fox
        int row_bonus = ROW_MULTIPLIER * (7 - fox.row);

        //Calculate attacking geese
        int attackingGeese = 0;
        for (Location goose : board.GeeseLocs) {
            if (Math.abs(goose.row - fox.row) != 1) {
                continue;
            }
            if (Math.abs(goose.col - fox.col) != 1) {
                continue;
            }
            attackingGeese++;
        }

        //Calculate Past the Goose Bonus
        int geesePassed = 0;
        for(Location goose : board.GeeseLocs){
            if(goose.row >= fox.row){
                geesePassed++;
            }
        }

        //Calculate the edge negative value
        int foxOnEdgeCol = 0;
        if(fox.col==0 || fox.col==7) foxOnEdgeCol = 1;

        return distSum + row_bonus + (geesePassed * PAST_THE_GOOSE_BONUS)
                - (attackingGeese * GOOSE_ATTACK_VALUE) - (foxOnEdgeCol * EDGE_PENALTY_VALUE);
    }

    /***************************************************************/
    /* Method: randomMove                                          */
    /* Purpose: Generate a random move on the current game board   */
    /* Parameters: Board board: The current game board             */
    /* Returns: Move: A random move on the game board.             */
    /***************************************************************/
    Move randomMove(Board board) {
        if (isFox) {
            //pick random direction
            MoveDir dir;
            do {
                int dirChoice = ran.nextInt(4);
                dir = switch (dirChoice) {
                    case 0 -> MoveDir.ForwardRight;
                    case 1 -> MoveDir.ForwardLeft;
                    case 2 -> MoveDir.BackwardLeft;
                    default -> MoveDir.BackwardRight;
                };

            } while (!board.isValidMoveFox(dir));

            return new Move(MovePerson.Fox, dir);
        } else {
            //pick random goose and direction
            int goose;
            MoveDir dir;
            do {
                int dirChoice = ran.nextInt(2);
                dir = switch (dirChoice) {
                    case 0 -> MoveDir.ForwardRight;
                    default -> MoveDir.ForwardLeft;
                };
                goose = ran.nextInt(4);

            } while (!board.isValidMoveGoose(dir, goose));

            return new Move(MovePerson.values()[goose], dir);
        }
    }

}
