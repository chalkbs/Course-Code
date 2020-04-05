/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicsquare;

/**
 *
 * @author berna
 */
public class MagicSquare {

    
    public static void main(String[] args) {
        //code to check command line arguments 
        int size = 0;
        if (args.length > 0) {
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        } else {
            System.out.println("You must provide an odd integer value for the square size");
            System.exit(0);
        }
        //check its an odd integer
        if(size % 2 == 0) {
            System.out.println("You must provide an odd integer value for the square size");
            System.exit(0);
        }
        //check if its at least size 3
        if(size < 3) {
            System.out.println("The square size must be at least 3");
            System.exit(0);
        }
    
        SquareMatrix matrix = new SquareMatrix(size);
        
        matrix.display();
        
    }
    
  }//end class MagicSquare

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
    
    //utility method
    protected int inc(int index) {
        //this is protected against negative index values
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
       

