//Name: Tito Thomas
//Student No: 1926374
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class Game{
        public static void main( String[] args ){
            System.out.println( "Welcome to the Magic Square Game!" );
            createCube();}

        public static void createCube() {
                Scanner input = new Scanner( System.in );
                System.out.println( "Enter an odd integer" );

                if (input.hasNextInt()){
                        int n = input.nextInt();
                        if (n%2==0){
                                System.out.println( "Number not odd" );
                                createCube();}
                        else if(n<3){
                                System.out.println( "Number must be greater than 1" );
                                createCube();}
                        else{
                                int x = 0; //row 1
                                int y = ((n+1)/2)-1; //column (n+1)/2
                        
                                int[][] magic = new int[n][n];
                                magic[x][y]=1;

                                for(int i=2;i <= n*n; i++){
                                        int neg_x = (((x-1)%n)+n)%n;
                                        int pos_x = (((x+1)%n)+n)%n;
                                        int neg_y = (((y-1)%n)+n)%n;
                                        
                                        if (magic[neg_x][neg_y]==0){
                                                x = neg_x;
                                                y = neg_y;}                               
                                        else{
                                                x = pos_x;}
                                        magic[x][y]=i;
                                }
                        System.out.println("Original Magic Sqaure:"+"\n");
                        displayCube(magic);
                        shuffle(magic,n);
                        }  
                }     
                else{System.out.println("Please enter only numbers");
                createCube();}
        }
 
        public static void shuffle(int magic[][], int n){
                Random random = new Random();
                int moves = 0;
                for(int s=1;s<=n*n;s++){
                        //generate indexes for random element in square
                        int rand_row = random.nextInt(n);
                        int rand_col = random.nextInt(n);
                        //creating an array of neighbours
  
                        int[] n_right = {rand_row,(((rand_col+1)%n)+n)%n};
                        int[] n_left = {rand_row,(((rand_col-1)%n)+n)%n};
                        int[] n_up = {(((rand_row-1)%n)+n)%n,rand_col};
                        int[] n_down = {(((rand_row+1)%n)+n)%n,rand_col};

                        int[][] neighbours = {n_right,n_left,n_up,n_down};
                        int n_index = random.nextInt(neighbours.length);
                        //swapping values
                        int chosen_row = neighbours[n_index][0]; //randomly chosen neighbour
                        int chosen_col = neighbours[n_index][1];
                        
                        int temp = magic[rand_row][rand_col];
                        magic[rand_row][rand_col] = magic[chosen_row][chosen_col]; //swapping random element with neighbour
                        magic[chosen_row][chosen_col] = temp;
                }
                System.out.println("Shuffled Magic Sqaure:"+"\n");
                displayCube(magic);
                playerMoves(magic, n, moves);
        }

        public static void displayCube(int magic[][]){
                for (int[] row : magic){
                        for(int u : row){
                                System.out.print(u + "\t");}                       
                        System.out.println("\n");} 
        }

        public static void playerMoves(int magic[][],int n, int moves){
      
                boolean inval = false;
                Scanner input = new Scanner(System.in);
                System.out.println("Follow the instructions and try to reconstruct a magic square!");
                while(true){ //infinite loop until square is recontructed
                        Scanner row_input = new Scanner(System.in);
                        Scanner col_input = new Scanner(System.in);
                        Scanner dir_input = new Scanner(System.in);
                        int row = 0;
                        int col = 0;
                        String dir = "";

                       //row
                        System.out.println("Please enter row of element to be swapped");
                        if (row_input.hasNextInt()){
                                row = row_input.nextInt();
                                if(1 <= row && row <= n){
                                        row = row - 1;} //as index starts at 0
                                else{
                                        System.out.println("Invalid row");
                                        inval = true;
                                        break;}
                        }
                        else{System.out.println("Invalid row");
                                inval = true;
                                break;}
          
                        //coloumn
                        System.out.println("Please enter column of element to be swapped");
                        if(col_input.hasNextInt()){
                                col = col_input.nextInt();
                                if(1 <= col && col <= n){
                                        col = col - 1;} //as index starts at 0
                                else{
                                        System.out.println("Invalid column");
                                        inval = true;
                                        break;}
                        }
                        else{System.out.println("Invalid column");
                        inval = true;
                        break;}

                        //direction
                        System.out.println("Please enter direction to swap with");
                        String[][] valid = {{"U","Up"},{"D","Down"},{"L","Left"},{"R","Right"},
                        {"u","Up"},{"d","Down"},{"l","Left"},{"r","Right"}};

                        dir = dir_input.nextLine();
                        boolean present = false;
                        String d_short = "";
                        String d_word = "";
                        for (String[] i:valid){
                                for(String u:i){
                                        if (u.equals(dir)){
                                        present = true;
                                        d_word = i[1];}
                                }
                        }
                        if (present == true){
                                moves+=1;
                                System.out.println("You chose row "+(row+1));
                                System.out.println("You chose column "+(col+1));
                                System.out.println("You chose "+d_word);
                                playerSwap(row, col ,d_word, magic, n);}
                        else{
                                System.out.println("Invalid direction");
                                inval = true;
                                break;}                     
                        
                        if(checkSqaure(magic, n)==true){
                                System.out.println("Congratulations, You completed the game!");
                                System.out.println("Moves made: "+moves);
                                break;}
                }

                if(inval==true){playerMoves(magic,n, moves);}
        }

        public static void playerSwap(int row, int col, String direction, int magic[][], int n){
                int count = 0;
                int swap_row = 0;
                int swap_col = 0;
                if(direction=="Up"){
                        swap_row = (((row-1)%n)+n)%n;
                        swap_col = col;}
                else if(direction=="Down"){
                        swap_row = (((row+1)%n)+n)%n;
                        swap_col = col;}
                else if(direction=="Left"){
                        swap_row = row;
                        swap_col = (((col-1)%n)+n)%n;}
                else if(direction=="Right"){
                        swap_row = row;
                        swap_col = (((col+1)%n)+n)%n;}

                int temp = magic[row][col];
                magic[row][col] = magic[swap_row][swap_col]; 
                magic[swap_row][swap_col] = temp;
                        
                System.out.println("New square:");
                displayCube(magic);
        }

        public static boolean checkSqaure(int magic[][], int n){
                boolean complete = false;
                int diag_one = 0;
                int diag_two = 0;
                //diagonal total top left to bottom right
                for(int i=0;i<n;i++){
                        diag_one+=magic[i][i];
                }
                //diagonal total top right to bottom left
                for(int i=0;i<n;i++){
                        diag_two+=magic[i][(n-1)-i]; 
                }
                if(diag_one == diag_two){
                //check if sum of each row == diagonal 
                        for(int[] row : magic){
                                int row_sum = 0;
                                for(int i : row){
                                        row_sum+=i;} 
                                if(row_sum!=diag_one){
                                        complete = false;}
                                else if(row_sum==diag_one){
                                        complete = true;}
                        }
                        //check if sum of each column == diagonal
                        for(int i =0; i<n;i++){
                                int col_sum = 0; 
                                for (int x = 0; x<n; x++){
                                        col_sum += magic[x][i];}

                                if(col_sum!=diag_one){
                                        complete = false;}
                                else if(col_sum==diag_one){
                                        complete = true;}
                        }
                }
                return complete;
        }
}
     