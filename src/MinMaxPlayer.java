import java.util.ArrayList;
import java.util.Random;

class Node {
    Board board;
    Move move;

    boolean isMax;

    int value;
    int alpha = Integer.MAX_VALUE;
    int beta = Integer.MIN_VALUE;

    Node parent = null;
    ArrayList<Node> children = new ArrayList<>();

    Node(Board board, boolean isMax) {
        this.isMax = isMax;
        this.board = board;
    }

    Node(Node parent) {
        this.parent = parent;
        this.board = new Board(parent.board); // copy board
        this.isMax = !parent.isMax;
    }

    Node(Node parent, Move m) {
        this.move = m;
        this.parent = parent;
        this.board = new Board(parent.board); // copy board
        this.isMax = !parent.isMax;

        this.board.doMove(m);
    }

    //get all possible moves
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

    ArrayList<Node> getChildren(boolean isFox) {
        ArrayList<Move> validMoves = getValidMoves(isFox);
        for (Move move : validMoves) {
            makeChild(move);
        }
        return children;
    }

    Node makeChild(Move m) {
        Node newNode = new Node(this, m);
        children.add(newNode);
        return newNode;
    }
}


public class MinMaxPlayer {
    boolean isFox;
    int maxdepth;
    Random ran;


    //Configuration
    int ROW_MULTIPLIER = 10; //Bonus that a fox gets per row away from bottom
    int GOOSE_ATTACK_VALUE = 15; //Each attacking (adjacent) goose subtracts this to the eval score
    int PAST_THE_GOOSE_BONUS = 5;

    MinMaxPlayer(boolean isFox, int maxdepth) {
        this.maxdepth = maxdepth;
        this.isFox = isFox;

        ran = new Random();
    }

    MinMaxPlayer(boolean isFox, int maxdepth, int rm, int gav, int ptgb) {
        this.maxdepth = maxdepth;
        this.isFox = isFox;

        this.ROW_MULTIPLIER = rm;
        this.GOOSE_ATTACK_VALUE = gav;
        this.PAST_THE_GOOSE_BONUS = ptgb;

        ran = new Random();
    }

    Move doAIStuff(Board board) {
        if (maxdepth == 0) {
            return randomMove(board);
        }

        return AlphaBetaSearch(board);
    }

    Move AlphaBetaSearch(Board board) {
        Node n = new Node(board, isFox);
        int v;
        if (isFox) {
            v = Max_Value(n, Integer.MIN_VALUE, Integer.MAX_VALUE, maxdepth*2);
        } else {
            v = Min_Value(n, Integer.MIN_VALUE, Integer.MAX_VALUE, maxdepth*2);
        }

        for (Node child : n.children) {
            if (child.value == v) {
                return child.move;
            }
        }

        return null;
    }

    int Max_Value(Node n, int alpha, int beta, int depth) {
        if (depth == 0 || n.board.checkWinGoose() || n.board.checkWinFox()) {
            n.value = evalFunc(n.board);
            return n.value;
        }

        int v = Integer.MIN_VALUE;
        ArrayList<Node> children = n.getChildren(true); //Max Nodes can only do fox moves
        for (Node child : children) {
            v = Math.max(v, Min_Value(child, alpha, beta, depth - 1));
            if (v >= beta) {
                n.value = v;
                return n.value;
            }
            alpha = Math.max(alpha, v);
        }
        n.beta = beta;
        n.alpha = alpha;
        n.value = v;
        return n.value;
    }

    int Min_Value(Node n, int alpha, int beta, int depth) {
        if (depth == 0 || n.board.checkWinGoose() || n.board.checkWinFox()) {
            n.value = evalFunc(n.board);
            return n.value;
        }

        int v = Integer.MAX_VALUE;
        ArrayList<Node> children = n.getChildren(false); //Min Nodes can only do goose moves
        for (Node child : children) {
            v = Math.min(v, Max_Value(child, alpha, beta, depth - 1));
            if (v <= alpha) {
                n.value = v;
                return n.value;
            }
            beta = Math.min(beta, v);
        }
        n.beta = beta;
        n.alpha = alpha;
        n.value = v;
        return n.value;
    }

    int evalFunc(Board board) {

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
            //calculate euclidian distance
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

        return distSum + row_bonus + (geesePassed * PAST_THE_GOOSE_BONUS) - (attackingGeese * GOOSE_ATTACK_VALUE);
    }

    Move randomMove(Board board) {
        if (isFox) {
            MoveDir dir;
            do {
                int dirChoice = ran.nextInt(4);
                switch (dirChoice) {
                    case 0:
                        dir = MoveDir.ForwardRight;
                        break;
                    case 1:
                        dir = MoveDir.ForwardLeft;
                        break;
                    case 2:
                        dir = MoveDir.BackwardLeft;
                        break;
                    case 3:
                    default:
                        dir = MoveDir.BackwardRight;
                        break;
                }

            } while (!board.isValidMoveFox(dir));

            return new Move(MovePerson.Fox, dir);
        } else {
            //computer plays as goose
            int goose;
            MoveDir dir;
            do {
                int dirChoice = ran.nextInt(2);
                switch (dirChoice) {
                    case 0:
                        dir = MoveDir.ForwardRight;
                        break;
                    case 1:
                    default:
                        dir = MoveDir.ForwardLeft;
                        break;
                }
                goose = ran.nextInt(4);

            } while (!board.isValidMoveGoose(dir, goose));

            return new Move(MovePerson.values()[goose], dir);
        }
    }

}
