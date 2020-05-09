import java.util.Random;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Random ran = new Random();

        Scanner scan = new Scanner(System.in);

        int playerID = 0;
        int searchDepth = 0;

        //ask the user if they want to be fox or goose
        System.out.println("Welcome to Fox and Geese!");
        System.out.println("Do you want to play as the Fox or the Geese?(enter 1 for Fox and 2 for Geese): ");

        //scan the user input
        if (scan.hasNextInt()) {
            playerID = scan.nextInt();
        }

        //ask the user for search depth
        System.out.println("What is the depth of seach allowed: ");

        //scan the user input
        if (scan.hasNextInt()) {
            searchDepth = scan.nextInt();
        }

        //create player objects - fox or geese
        //create computer objects - fox or geese

        BoardNode bn = new BoardNode();

        System.out.println(bn);

        boolean gameOver = false;
        while (!gameOver) {
            //--------------------------------PLAYER MOVING--------------------------------
            int playerMove = 0;

            //give player the menu of choices for input
            if (playerID == 1) {
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

            //scan for input

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
                if (playerID == 1) {
                    MoveDir playerDirection;
                    //fox
                    playerDirection = switch (playerMove) {
                        case 1 -> MoveDir.ForwardRight;
                        case 2 -> MoveDir.ForwardLeft;
                        case 3 -> MoveDir.BackwardRight;
                        case 4 -> MoveDir.BackwardLeft;
                        default -> MoveDir.ForwardRight;
                    };

                    moveMade = bn.moveFox(playerDirection);

                } else {
                    MoveDir playerDirection;
                    int goose;

                    if (playerMove % 2 == 0) {
                        playerDirection = MoveDir.ForwardLeft;
                    } else {
                        playerDirection = MoveDir.ForwardRight;
                    }
                    goose = (playerMove + 1) / 2 - 1;

                    moveMade = bn.moveGoose(playerDirection, goose);
                }

                if(!moveMade){
                    System.out.println("That move is invalid, please select another move");
                }

            }

            //print board
            System.out.println(bn);

            //check for win conditions
            if(bn.checkWinFox()){
                System.out.println("The Fox won!");
                gameOver = true;
                break;
            }
            if(bn.checkWinGoose()){
                System.out.println("Geese have won");
                gameOver = true;
                break;
            }

            //--------------------------------COMPUTER MOVING--------------------------------
            //computer do a minimax search and make the move
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (playerID == 1){
                //computer plays as goose
                int goose = ran.nextInt(4);
                int dirChoice = ran.nextInt(2);
                MoveDir dir = switch(dirChoice){
                    case 0 ->MoveDir.ForwardRight;
                    case 1 -> MoveDir.ForwardLeft;
                    default -> MoveDir.ForwardRight;
                };
                while(!bn.moveGoose(dir, goose)){
                    goose = ran.nextInt(4);
                    dirChoice = ran.nextInt(2);
                    dir = switch(dirChoice){
                        case 0 ->MoveDir.ForwardRight;
                        case 1 -> MoveDir.ForwardLeft;
                        default -> MoveDir.ForwardRight;
                    };
                }

                System.out.println("Computer moved goose " + (goose + 1) + " direction " + dir.name());

            } else{
               int dirChoice = ran.nextInt(4);
                MoveDir dir = switch(dirChoice){
                    case 0 ->MoveDir.ForwardRight;
                    case 1 -> MoveDir.ForwardLeft;
                    case 2 -> MoveDir.BackwardLeft;
                    case 3 -> MoveDir.BackwardRight;
                    default -> MoveDir.ForwardRight;
                };
                while(!bn.moveFox(dir)){
                    dirChoice = ran.nextInt(4);
                    dir = switch(dirChoice){
                        case 0 ->MoveDir.ForwardRight;
                        case 1 -> MoveDir.ForwardLeft;
                        case 2 -> MoveDir.BackwardLeft;
                        case 3 -> MoveDir.BackwardRight;
                        default -> MoveDir.ForwardRight;
                    };
                }
                System.out.println("Computer moved the fox " + dir.name());
            }

            System.out.println(bn);

            //check for win conditions
            if(bn.checkWinFox()){
                System.out.println("The Fox won!");
                System.out.println("You lost to a computer haha noob");
                gameOver = true;
                break;
            }
            if(bn.checkWinGoose()){
                System.out.println("Geese have won");
                gameOver = true;
                break;
            }
        }
    }
}
