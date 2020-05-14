import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int searchDepth = 3;
//        MinMaxPlayer playerFox = new MinMaxPlayer(true, 4, 1000, 50, 100);
//        MinMaxPlayer playerGoose = new MinMaxPlayer(false, 6, 1000, 50, 100);

        MinMaxPlayer playerFox = new MinMaxPlayer(true, 7, 250, 12, 25);
        MinMaxPlayer playerGoose = new MinMaxPlayer(false, 7, 250, 12, 25);

        Board board = new Board();

        System.out.println(board);

        boolean gameOver = false;
        while (!gameOver) {
            //--------------------------------PLAYER MOVING--------------------------------

            //Make a move on the board
            Move m = playerFox.doAIStuff(board);
            board.doMove(m);

            //print board
            System.out.println("The fox did the move " + m);
            System.out.println(board);

            //check for win conditions
            if(board.checkWinFox()){
                System.out.println("The Fox won!");
                gameOver = true;
                break;
            }
            if(board.checkWinGoose()){
                System.out.println("Geese have won");
                gameOver = true;
                break;
            }

            m = playerGoose.doAIStuff(board);
            board.doMove(m);

            System.out.println("The goose did the move " + m);
            System.out.println(board);

            //check for win conditions
            if(board.checkWinFox()){
                System.out.println("The Fox won!");
                System.out.println("You lost to a computer haha noob");
                gameOver = true;
                break;
            }
            if(board.checkWinGoose()){
                System.out.println("Geese have won");
                gameOver = true;
                break;
            }
        }
    }
}
