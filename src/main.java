/***************************************************************/
/* Aakif Aslam and Manning Zhao                                */
/* CS-481, Spring 2020                                         */
/* Lab Assignment 2                                            */
/* Purpose: Create a fox and geese game AI                     */
/* main Class: Get input from user and then generate an AI     */
/*             player to play fox and geese with the user      */
/***************************************************************/
import java.util.Scanner;

public class main {

    /***************************************************************/
    /* Method: main                                                */
    /* Purpose: Play a fox and geese game with the user            */
    /* Parameters: String[]args: String input                      */
    /* Returns: None                                               */
    /***************************************************************/
    public static void main(String[] args) {
        //create a scanner for user input
        Scanner scan = new Scanner(System.in);

        //create a MinMaxPlayer
        MinMaxPlayer player;

        boolean userIsFox;    //decides whether user is fox or geese
        int searchDepth = 0;  //max search depth allowed by the user

        //ask the user if they want to be fox or goose
        System.out.println("Welcome to Fox and Geese!");
        System.out.println("Do you want to play as the Fox or the Geese?(enter 1 for Fox and 2 for Geese): ");
        int userID = 0;     //user ID = 1 --> fox, user ID = 2 --> geese
        if (scan.hasNextInt()) {
            userID = scan.nextInt();
        }
        userIsFox = (userID == 1);

        //ask the user for search depth
        System.out.println("What is the depth of search allowed: ");
        if (scan.hasNextInt()) {
            searchDepth = scan.nextInt();
        }

        //create the AI player
        player = new MinMaxPlayer(!userIsFox, searchDepth);

        //create a new game board
        Board board = new Board();
        System.out.println(board);

        //starts playing the game...
        boolean gameOver = false;
        while (!gameOver) {
            //--------------------------------PLAYER MOVING--------------------------------
            int playerMove = 0;

            //give player the menu of choices for input
            if (userIsFox) {
                //fox
                System.out.println("Current available moves:\n" +
                        "1. right forward\n" +
                        "2. left forward\n" +
                        "3. right backward\n" +
                        "4. left backward");
            } else {
                //geese
                System.out.println("Current available moves:\n" +
                        "1. right goose 1\n" +
                        "2. left goose 1\n" +
                        "3. right goose 2\n" +
                        "4. left goose 2\n" +
                        "5. right goose 3\n" +
                        "6. left goose 3\n" +
                        "7. right goose 4\n" +
                        "8. left goose 4");
            }

            //Make a move on the board
            boolean moveMade = false;
            while (!moveMade) {
                if (scan.hasNextInt()) {
                    playerMove = scan.nextInt();
                    if (playerMove == 0) {
                        gameOver = true;
                        break;
                    }
                }
                if (userIsFox) {
                    MoveDir playerDirection;
                    playerDirection = switch (playerMove) {
                        case 1 -> MoveDir.ForwardRight;
                        case 2 -> MoveDir.ForwardLeft;
                        case 3 ->  MoveDir.BackwardRight;
                        default -> MoveDir.BackwardLeft;
                    };

                    moveMade = board.moveFox(playerDirection);

                } else {
                    MoveDir playerDirection;
                    int goose;

                    if (playerMove % 2 == 0) {
                        playerDirection = MoveDir.ForwardLeft;
                    } else {
                        playerDirection = MoveDir.ForwardRight;
                    }
                    goose = (playerMove + 1) / 2 - 1;

                    moveMade = board.moveGoose(playerDirection, goose);
                }

                if(!moveMade){
                    System.out.println("That move is invalid, please select another move");
                }
            }

            //print board
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

            //--------------------------------COMPUTER MOVING--------------------------------
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //generate a move
            Move computerMove = player.doAIStuff(board);
            board.doMove(computerMove);

            //print out the computer's move
            System.out.println("The computer did the move " + computerMove);
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
