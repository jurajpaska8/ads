import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Cvicenie4 {

    public static int[][] loadMattrix(String path, String delimiter, int rows, int cols) throws FileNotFoundException
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
     * input mattrix contains 4 numbers in row. Function resizes this matrix. 2 numbers in row i, 2 numbers in i + 1 row in  outputMatrix.
     *
     * */
    private static int [][] transformMattrix(int[][] inputMattrix)
    {
        int [][] outputMattrix = new int [inputMattrix.length * 2][inputMattrix[0].length / 2];
        for(int i = 0; i < inputMattrix.length; i++)
        {
            outputMattrix[2 * i][0] = inputMattrix[i][0];
            outputMattrix[2 * i][1] = inputMattrix[i][1];
            outputMattrix[2 * i + 1][0] = inputMattrix[i][2];
            outputMattrix[2 * i + 1][1] = inputMattrix[i][3];
        }
        return outputMattrix;
    }

    private static int knapSackProblemBasic(int W, int values[], int weights[], int n, Set<Integer> indexes)
    {
        // row with zeros, column with zeros
        int arr[][] = new int[n + 1][W + 1];
        int firstValueOfItem = -1;

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
                    if(values[i - 1] + arr[i - 1][w - weights[i - 1]] > arr[i - 1][w])
                    {
                        indexes.add(i - 1);
                    }
                    arr[i][w] = Math.max(arr[i - 1][w], values[i - 1] + arr[i - 1][w - weights[i - 1]]);
                }
            }
        }
        return arr[n][W];
    }

    private static int knappSackProblemExercise(int W, int values[], int weights[], int n)
    {
        // we have 2000 rows, but we can choose only from doubles (0th or 1th, 2th or 3th...)
        // therefore matrix with n / 2 + 1 rows is sufficient
        int arr[][] = new int[n / 2 + 1][W + 1];
        int tmp = 0;
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
            int[][] mattrix = loadMattrix("data/cvicenie4data.txt", ",", rows, cols);
            int[][] transformedMattrix = transformMattrix(mattrix);
            Set<Integer> listOfIndexes = new HashSet<>();

            int[] values = new int[transformedMattrix.length];
            int[] weights = new int[transformedMattrix.length];
            int W = 2000;
            for(int i = 0; i < transformedMattrix.length; i++)
            {
                values[i] = transformedMattrix[i][0];
                weights[i] = transformedMattrix[i][1];
            }
            int maxKnapsackValue = knapSackProblemBasic(W, values, weights, 2000, listOfIndexes);
            int maxKnapsackValueExercise = knappSackProblemExercise(W, values, weights, 2000);
            System.out.println("End");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
