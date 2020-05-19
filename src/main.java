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
        MinMaxPlayer computerPlayer ;

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
        computerPlayer = new MinMaxPlayer(!userIsFox, searchDepth);

        //create a new game board
        Board board = new Board();
        System.out.println(board);

        //fox goes first
        boolean userTurn = userIsFox;

        //starts playing the game...
        boolean gameOver = false;
        while (!gameOver) {
            if(userTurn) {
                //user moves
                //give user the menu of choices for input
                printUserPrompt(userIsFox);

                //Make a move on the board
                boolean moveMade = false;
                //keep asking for input until the user picks a valid input
                while (!moveMade) {
                    //get user input

                    int userInput = 0;
                    if (scan.hasNextInt()) {
                        userInput = scan.nextInt();
                        //if user enters 0, game ends
                        if (userInput == 0) {
                            gameOver = true;
                            break;
                        }
                    }

                    //Turn user selection into move
                    Move userMove = parseMoveSelection(userIsFox, userInput);

                    //Try to do move if it is valid
                    moveMade = board.doMove(userMove);

                    if (!moveMade) {
                        //If move not valid, ask for another selection
                        System.out.println("That move is invalid, please select another move");
                    }
                }
            } else {
                //Computer moves
                Move computerMove = computerPlayer .runSearch(board);
                board.doMove(computerMove);

                System.out.println("The computer moved " + (userIsFox? computerMove.person : "the fox") + " in the direction " + computerMove.dir);
            }

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

            userTurn = !userTurn; //switch turns
        }
    }

    private static Move parseMoveSelection(boolean userIsFox, int userInput) {
        Move userMove;
        if (userIsFox) {
            //fox move
            MoveDir userDirection;
            userDirection = switch (userInput) {
                case 1 -> MoveDir.ForwardLeft;
                case 2 -> MoveDir.ForwardRight;
                case 3 -> MoveDir.BackwardLeft;
                default -> MoveDir.BackwardRight;
            };

            userMove = new Move(MovePerson.Fox, userDirection);

        } else {
            MoveDir userDirection;
            int goose;

            if (userInput % 2 == 0) {
                userDirection = MoveDir.ForwardRight;
            } else {
                userDirection = MoveDir.ForwardLeft;
            }
            goose = (userInput + 1) / 2 - 1;

            userMove = new Move(MovePerson.values()[goose], userDirection);
        }
        return userMove;
    }

    private static void printUserPrompt(boolean userIsFox) {
        if (userIsFox) {
            //fox
            System.out.println("Current available moves:\n" +
                    "1. left forward\n" +
                    "2. right forward\n" +
                    "3. left backward\n" +
                    "4. right backward");
        } else {
            //geese
            System.out.println("Current available moves:\n" +
                    "1. left goose 1\n" +
                    "2. right goose 1\n" +
                    "3. left goose 2\n" +
                    "4. right goose 2\n" +
                    "5. left goose 3\n" +
                    "6. right goose 3\n" +
                    "7. left goose 4\n" +
                    "8. right goose 4");
        }
    }
}
