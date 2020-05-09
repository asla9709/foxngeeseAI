import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		int playerID = 0;
		int searchDepth = 0;

		//ask the user if they want to be fox or goose
		System.out.println("Welcome to Fox and Geese!");
		System.out.println("Do you want to play as the Fox or the Geese?(enter 1 for Fox and 2 for Geese): ");
		
		//scan the user input
		if(scan.hasNextInt()){	
			playerID = scan.nextInt();
		}
		
		//ask the user for search depth
		System.out.println("What is the depth of seach allowed: ");
		
		//scan the user input
		if(scan.hasNextInt()){	
			searchDepth = scan.nextInt();
		}

		//create player objects - fox or geese
		//create computer objects - fox or geese
		
		boolean gameOver = false;
		while(!gameOver){
			int playerMove = 0;
			
			//give player the menu of choices for input
			if(playerID == 1){
				//fox
				System.out.println("Current available moves:\n" +
						"1. right forward\n" +
						"2. left forward\n" +
						"3. right backward\n" +
						"4. left backward");
			}
			else{
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
			
			//scan the user input
			if(scan.hasNextInt()){	
				playerMove = scan.nextInt();
			}
			
			//validate the move
			
			//make the move on the board
			
			//print board
			
			//computer do a minimax search and make the move
			
			//print the computer move
			
			//print board
			
		}
	}
}
