import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int searchDepth = 3;
//        MinMaxPlayer playerFox = new MinMaxPlayer(true, 4, 1000, 50, 100,0);
//        MinMaxPlayer playerGoose = new MinMaxPlayer(false, 4, 1000, 50, 100,0);

        //for the values below
        //depth 1 --> geese won, 2 --> fox won, 3 --> fox won, 4 --> geese won, 5 --> fox won, >6 = geese won
        MinMaxPlayer playerFox = new MinMaxPlayer(true, 5, 250, 50, 75, 1000);
        MinMaxPlayer playerGoose = new MinMaxPlayer(false, 5, 250, 50, 75, 1000);

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
