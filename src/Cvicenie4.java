import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Cvicenie4 {

    public static int[][] loadMatrix(String path, String delimiter, int rows, int cols) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        int [][] myArray = new int[rows][cols];
        while(sc.hasNextLine())
        {
            for (int i=0; i<myArray.length; i++)
            {
                String[] line = sc.nextLine().trim().split(delimiter);
                for (int j=0; j<line.length; j++)
                {
                    myArray[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        return myArray;
    }

    /**
     * input mattrix contains 4 numbers in row. Function resizes this matrix. 2 numbers in row [i], 2 numbers in [i + 1] row in  outputMatrix.
     * Therefore has outputMatrix 2 times more rows
     * */
    private static int [][] transformMatrix(int[][] inputMatrix)
    {
        int [][] outputMattrix = new int [inputMatrix.length * 2][inputMatrix[0].length / 2];
        for(int i = 0; i < inputMatrix.length; i++)
        {
            outputMattrix[2 * i][0] = inputMatrix[i][0];
            outputMattrix[2 * i][1] = inputMatrix[i][1];
            outputMattrix[2 * i + 1][0] = inputMatrix[i][2];
            outputMattrix[2 * i + 1][1] = inputMatrix[i][3];
        }
        return outputMattrix;
    }

    private static int knappSackProblemBasic(int W, int[] values, int[] weights, int n)
    {
        // row with zeros, column with zeros
        int[][] arr = new int[n + 1][W + 1];

        // construct table
        for(int i = 0; i <= n; i++)
        {
            for(int w = 0; w <= W; w++)
            {
                // first row and first column with zeros
                if(i == 0 || w ==0)
                {
                    arr[i][w] = 0;
                }
                // if we can not take item due to its weight
                else if(weights[i - 1] > w)
                {
                    arr[i][w] = arr[i - 1][w];
                }
                // else we choose maximum with/without selecting item
                else
                {
                    arr[i][w] = Math.max(arr[i - 1][w], values[i - 1] + arr[i - 1][w - weights[i - 1]]);
                }
            }
        }
        return arr[n][W];
    }

    /**
     * Limitations in contrasts with classic problem:
     * we can not count values and weights from every row, but we can ony choose best from [0],[1] / [2],[3] / ... / [2*i],[2*i+1] / ...
     *
     * */
    private static int knappSackProblemExercise(int W, int[] values, int[] weights, int n)
    {
        // we have 2000 rows, but we can choose only from doubles (0th or 1th, 2th or 3th...)
        // therefore matrix with n / 2 + 1 rows is sufficient
        int[][] arr = new int[n / 2 + 1][W + 1];

        // construct table
        for(int i = 0; i <= n / 2; i++)
        {
            for(int w = 0; w <= W; w++)
            {
                // first row and first column with zeros
                if(i == 0 || w ==0)
                {
                    arr[i][w] = 0;
                }

                // if we can not take items due to its weight
                else if((weights[2 * i - 1] > w) && (weights[2 * i - 2] > w))
                {
                    arr[i][w] = arr[i - 1][w];
                }

                // if we can afford only item with higher index
                else if((weights[2 * i - 1] <= w)  && (weights[2 * i - 2] > w) )
                {
                    arr[i][w] = Math.max(arr[i - 1][w], values[2 * i - 1] + arr[i - 1][w - weights[2 * i - 1]]);
                }

                // if we can afford only item with lower index
                else if((weights[2 * i - 1] > w)  && (weights[2 * i - 2] <= w) )
                {
                    arr[i][w] = Math.max(arr[i - 1][w], values[2 * i - 2] + arr[i - 1][w - weights[2 * i - 2]]);
                }

                // else we choose maximum with/without selecting item
                else
                {
                    arr[i][w] = Math.max(arr[i - 1][w],
                                    Math.max(values[2 * i - 2] + arr[i - 1][w - weights[2 * i - 2]],
                                            values[2 * i - 1] + arr[i - 1][w - weights[2 * i - 1]]));
                }
            }
        }
        return arr[n / 2][W];
    }

    public static void main(String[] args) {
        try
        {
            int rows = 1000;
            int cols = 4;
            int[][] matrix = loadMatrix("data/cvicenie4data.txt", ",", rows, cols);
            int[][] transformedMatrix = transformMatrix(matrix);

            // values, weights, number of rows n, max Weight W
            int[] values = new int[transformedMatrix.length];
            int[] weights = new int[transformedMatrix.length];
            int W = 2000;
            int n = transformedMatrix.length;

            for(int i = 0; i < transformedMatrix.length; i++)
            {
                values[i] = transformedMatrix[i][0];
                weights[i] = transformedMatrix[i][1];
            }
            int maxKnapsackValue = knappSackProblemBasic(W, values, weights, n);
            int maxKnapsackValueExercise = knappSackProblemExercise(W, values, weights, n);
            System.out.println("Value = " + maxKnapsackValueExercise);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
