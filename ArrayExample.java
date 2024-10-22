package edu.njit.cs114;

public class ArrayExample {
    // Array a is assumed to be non-empty
    public static double [] [] manipulate(double [] [] a) {
// Perform step (a)
        double [] [] a1 = extract(a);
        System.out.println("Content of array after step (a):");
        printArray(a1);
// Perform step (b)
        double [] [] a2 = replace(a1);
        System.out.println("Content of array after step (b):");
        printArray(a2);
        return a2;
    }
    // prints 2D array
    private static void printArray(double [] [] a) {
        for (int i=0; i< a.length; i++) {
            for (int j=0; j< a[i].length;j++) {
                System.out.print(a[i][j]+",");
            }
            System.out.println();
        }
    }
    // copy elements from column indexed fromCol in a to column indexed toCol in b
    private static void copyCol(double [] [] a, double [] [] b, int fromCol, int
            toCol) {
        for (int i=0; i < a.length; i++){
            b[i][toCol] = a[i][fromCol];
        }
    }
    private static double [][] extract (double [][] a) {
        int nCols = a[0].length;
        int halfNumRows = (int) Math.ceil((float) a.length / 2);
        int noOfNegativesInCol =0;
        int noOfColsRemoved = 0;
        boolean [] removeCol = new boolean[nCols];
// Loop through each column of a to check if number of negatives is at least halfNumRows

        /**
         * To be completed
         * Loop through each row of the j-th column to update
         noOfNegativesInCol
         * If noOfNegativesInCol is at least halfNumRows, set removeCol[i] to
         true
         * and also update noOfColsRemoved
         */
        for (int j=0; j < nCols; j++)
        {
            noOfNegativesInCol = 0;
            for (int i=0; i< a.length; i++)
            {
                if (a[i][j] < 0)
                {
                noOfNegativesInCol++;
                }
        }
            if (noOfNegativesInCol >= halfNumRows)
            {
             removeCol[j] = true;
             noOfColsRemoved++;
            }
        }
// allocate a new array
        double [][] b = new double[a.length][];
        int nNewCols = nCols - noOfColsRemoved;
        for (int i = 0; i < a.length; i++)
        {
            b[i] = new double[nNewCols];
        }
        int validColIndex = 0;
/**
 * To be completed
 * Loop through each column j of a and if removeCol[j] is false, add it to
 b
 * using copyCol() function; use validColIndex for the same next column of
 b
 */

        for(int j=0; j < nCols; j++)
        {
            if (removeCol[j] == false)
            {
                copyCol(a,b,j,validColIndex);
                validColIndex++;
            }
        }
        return b;
    }
    private static double [][] replace(double [][] b)
    {
        double rowSum = 0.0; //Row sum
        double rowAverage = 0.0;//Row average
        int numNonNegatives = 0; // number of 0 or positive values in the row
//Loop through each row i to replace negative with average of 0 or positives in the same row
        for (int i=0; i < b.length; i++) {
            rowSum = 0.0;
            numNonNegatives = 0;

            for (int j=0; j <b[i].length; j++)
            {
                if (b[i][j] >= 0)
                {
                    rowSum += b[i][j];
                    numNonNegatives++;
                }

            }


            rowAverage = numNonNegatives > 0 ? rowSum/numNonNegatives : 0;
            /**
             * To be completed
             * First find rowSum and numNonNegatives using non-negative values of
             * the i-th row of b.
             * This is done by looping through each column of row i
             */
/**
 * To be completed
 * Now replace all negative numbers of i-th row of b with the row
 average
 */
            for (int j = 0; j<b[0].length; j++)
            {
                if (b[i][j] < 0)
                {
                    b[i][j] = rowAverage;
                }

            }

        }
        return b;
    }
    //-----------------------------------------------------------------
//
//-----------------------------------------------------------------
    public static void main (String[] args)
    {
        double [][] a ={{-1,4,3,2,-3,3},{-2,3,5,-4,0,0},{-1,-3,-4,1,-1,0},{-1,2,-
                3,6,5,3},{-3,2,-3,-5,1,-6}}; //A 5 x 6 Dimension;
        System.out.println("Printing input array...");
        printArray(a);
        double [] [] b = manipulate(a);
        System.out.println("Printing original array...");
        printArray(a);
    }
}
