import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Stream;

public class Cvicenie3 {
    /**
     * Loads mattrix of integers from given path.
     *
     * */
    private static int[][] loadMattrix(String path) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        final int ROWS = 1000;
        final int COLS = 50;
        int [][] myArray = new int[ROWS][COLS];
        while(sc.hasNextLine())
        {
            for (int i=0; i<myArray.length; i++)
            {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++)
                {
                    myArray[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        return myArray;
    }

    private static int returnMinFromNeighborsAndCurrent(int[] arr, int currentIdx)
    {
        if(currentIdx == 0)
        {
            return Math.min(arr[currentIdx], arr[currentIdx + 1]);
        }
        if(currentIdx == arr.length - 1)
        {
            return Math.min(arr[currentIdx], arr[currentIdx - 1]);
        }
        return Math.min(Math.min(arr[currentIdx - 1], arr[currentIdx]), arr[currentIdx + 1]);
    }


    public static int calculateDeformation(int[][] mattrix)
    {
        for(int i = mattrix.length - 2; i >= 0; i--)
        {
            for(int j = 0; j < mattrix[0].length; j++)
            {
                mattrix[i][j] += returnMinFromNeighborsAndCurrent(mattrix[i + 1], j);
            }
        }
//        for(int i = 1; i < mattrix.length; i++)
//        {
//            for(int j = 0; j < mattrix[0].length; j++)
//            {
//                mattrix[i][j] += returnMinFromNeighborsAndCurrent(mattrix[i - 1], j);
//            }
//        }
        return Arrays
                .stream(mattrix[0])
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    private static int shortestPath(int[][] mattrix)
    {
        int rows = mattrix.length;
        int cols = mattrix[0].length;
        for(int i = 1; i < rows; i++)
        {
            mattrix[i][0] = Math.min(mattrix[i - 1][0], mattrix[i - 1][1]) + mattrix[i][0];
            mattrix[i][cols - 1] = Math.min(mattrix[i - 1][cols - 1], mattrix[i - 1][cols - 2]) + mattrix[i][cols - 1];
            for(int j = 1; j < cols - 1; j++)
            {
                mattrix[i][j] = Math.min(Math.min(mattrix[i - 1][j - 1], mattrix[i - 1][j]), mattrix[i - 1][j + 1]) + mattrix[i][j];
            }
        }
        int min = Integer.MAX_VALUE;
        for(int i = 1; i < cols; i++)
        {
            min = Math.min(Math.min(mattrix[rows - 1][i], mattrix[rows - 1][i - 1]), min);
        }
        return min;
    }

    public static void main(String[] args)
    {
        try
        {
            int[][] mattrix = loadMattrix("data/cvicenie3data.txt");
            int def = calculateDeformation(mattrix);
            System.out.println(def);
            mattrix = loadMattrix("data/cvicenie3data.txt");
            int def2 = shortestPath(mattrix);
            System.out.println(def2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

