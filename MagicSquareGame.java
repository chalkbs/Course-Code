/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicsquaregame;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author berna
 */
public class MagicSquareGame {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) { 
        int size = 3; //default size
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        //  prompt for the size of the Magic Square
        System.out.println("Please enter a size for your Magic Square (an odd number between 3 and 9)");
        size = scanner.nextInt();
        //check its an odd integer
        if(size % 2 == 0) {
            System.out.println("You must provide an odd integer value for the square size");
            System.exit(0);
        }
        //check if its at least size 3
        if(size < 3) {
            System.out.println("The square size must be at least 3");
            System.exit(0);
        } else if(size > 9) {
            System.out.println("The square size must not be greater than 9");
            System.exit(0);
        }
        
        //create the Magic Square
    
        SquareMatrix matrix = new SquareMatrix(size);
        //keep a copy
        SquareMatrix originalMatrix = matrix;
        //now scramble
        matrix.shuffle();
        System.out.println("Here is your Shuffled Square");
        matrix.display();
        System.out.println();
        System.out.println("Please attempt to reconstruct a Magic Square by swapping numbers");
        System.out.println("To do this enter row, column and direction (U, D, L, R representing up, down, left right)");
        System.out.println("For example 2, 3, D would would swap " + matrix.getValue(2, 3) + " with " + matrix.getValue(3, 3));
        System.out.println("______________________________");
        System.out.println("Enter Q to quit the program");
        System.out.println("Enter A to see solution and quit the program");
        System.out.println("______________________________");
        
        int x, y;
        String direction;
        
        scanner = new Scanner(System.in); //clears old scanner
        while(true) {
            String s = scanner.nextLine().replaceAll("\\s", "");
            if((s.equals("Q")) || (s.equals("q"))) {
                System.out.println("Thank you for playing the Magic Square Game");
                System.exit(0);
            }
            if((s.equals("A")) || (s.equals("a"))) {
                originalMatrix.display();
                System.out.println("Thank you for playing the Magic Square Game");
                System.exit(0);
            }
            //check if the input matches this pattern
            Pattern p = Pattern.compile("^[1-9],[1-9],[UuDdLlRr]");
            Matcher m = p.matcher(s);
            boolean b = m.find();
            if (!b) {
                System.out.println("You must use a move pattern like 1,2,U");
                System.out.println("INVALID MOVE!");
                //System.exit(0);
                continue;
            } 
            String[] parts = s.split(",");
            
            try {
                x = Integer.parseInt(parts[0]);
                y = Integer.parseInt(parts[1]);
                if(x > size || y > size) {
                   System.out.println("Your row or column values is greater than " + size);
                   System.out.println("INVALID MOVE!");
                   continue;
                }
                
            } catch(NumberFormatException e) {
                 System.out.println("you must give your move in the form of 2,3,D");
                 System.exit(0);
            }
            
            System.out.println("we are now ready to make the move");
     
        }
        
        
    }
    
  }//end class MagicSquareGame

class SquareMatrix {
    int[][] matrix;
    int size;
    protected SquareMatrix(int size){
        this.size = size;
        //we will make the array bigger and ignore the zero row and column
        matrix = new int [size + 1][size + 1];
        int row, col;
        //initialise array values
        for(row = 1; row <= size; row++ ) {
            for(col = 1; col <= size; col++) {
                matrix[row][col] = 0;
            }
        }
        row = 1; col = (size + 1)/2;
        matrix[row][col] = 1;
        for(int value = 2; value <= size*size; value++) {
            if (matrix[dec(row)][dec(col)] == 0) {
                row = dec(row); col = dec(col);
            } else {
                row = inc(row);
            }
            insert(value, row, col);
        }
        
       
    }
    protected int inc(int index) {
        if(index < size) {
            return index + 1;
        } else {
            return 1;
        }
    }
    protected int dec(int index) {
        if(index > 1) {
            return index - 1;
        } else {
            return size;
        }
    }
    
    protected void insert(int value, int row, int col) {
        matrix[row][col] = value;
    }
    
    protected int getValue(int row, int col) {
        return matrix[row][col];
    }
    
    protected void swap(int[]square1, int[]square2){
        //swap values in the two square
        //note that x = int[1] and y = int[2]
        int value1 = matrix[square1[1]][square1[2]];
        int value2 = matrix[square2[1]][square2[2]];
        matrix[square1[1]][square1[2]] = value2;
        matrix[square2[1]][square2[2]] = value1;
    }
    
    protected void shuffle() {
        //randomly shuffles elements size*size times
        int times = (int) Math.pow(size,2);
        for(int i = 1; i <= times; i++){
            int[]randomSquare = getRandomSquare();
            //randomSquare is an array organised as {0, x, y}
            int value1 = matrix[randomSquare[1]][randomSquare[2]];
            //
            int[]randomNeighbour = getRandomNeighbour(randomSquare);
            //randomNeighbour is an array organised as {0, x, y}
            int value2 = matrix[randomNeighbour[1]][randomNeighbour[2]];
            //now swap them
            int temp = value1;
            matrix[randomSquare[1]][randomSquare[2]] = value2;
            matrix[randomNeighbour[1]][randomNeighbour[2]] = temp;
        }
        
    }
    
    protected int[] getRandomSquare() {
      int x = (int) (Math.random() * size + 1);
      int y = (int) (Math.random() * size + 1);
      int[] randomSquare = new int[]{0, x, y};
      return (randomSquare);   
   }
    
    protected int[] getRandomNeighbour(int[] square) {
     
      //create a default random neighbour
      int[] randomNeighbour = new int[] {0, 1, 1};
      int x = square[1];
      int y = square[2];
      //create four integers to represent Above, Right, Down, Left
      int randomDirection = (int) ( Math.random() * 3 + 1);
      
      switch(randomDirection) {
          case 1:
            // Above
            randomNeighbour[2] = dec(y);
            randomNeighbour[1] = x;     
            break;
          case 2:
            // Right
            randomNeighbour[2] = y;
            randomNeighbour[1] = inc(x);      
            break;
          case 3:
            // Down
            randomNeighbour[2] = inc(y);
            randomNeighbour[1] = x;   
            break;
          case 4:
            // Left
            randomNeighbour[2] = y;
            randomNeighbour[1] = dec(x);      
            break;
          default:
            //do nothing
        }
      
        return (randomNeighbour);   
   }

    protected void display() {
       int max = size*size;
       for(int row = 1; row <= size; row++) {
           for(int col = 1; col <= size; col++) {
               if((getValue(row,col) < 10) && max > 10 ) {
                   System.out.print(" ");
               } 
               System.out.print(matrix[row][col]);
               System.out.print("  ");     
           }
           System.out.println();
       }
    }
    
}// end class SquareMatrix
    
