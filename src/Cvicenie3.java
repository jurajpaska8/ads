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

    private static int[] indexesOfMinimalNumbers(int[] arr)
    {
        List<Integer> idxs = new LinkedList<>();
        // fin minimum
        int min = Arrays.stream(arr)
                .min()
                .orElse(Integer.MAX_VALUE);

        // put all indexes of minimum values into list and return it as array
        for(int i = 0; i < arr.length; i++) if(arr[i] == min) idxs.add(i);
        return idxs
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    int returnMinFromNeighborsAndCurrent(int[] arr, int currentIdx)
    {
        int min = currentIdx > 0 ? Math.min(arr[currentIdx], arr[currentIdx - 1]) : arr[currentIdx];
        return currentIdx < (arr.length - 1) ? Math.min(min, arr[currentIdx + 1]) : min;
    }

    private static int returnIndexOfMinFromNeighborsAndCurrent(int[] arr, int currentIdx)
    {
        int minIdx = currentIdx;
        if(currentIdx > 0)
        {
            minIdx = (arr[currentIdx] <= arr[currentIdx - 1]) ? currentIdx : currentIdx - 1;
        }
        if(currentIdx < arr.length - 1)
        {
            minIdx = (arr[currentIdx] <= arr[currentIdx + 1]) ? currentIdx : currentIdx + 1;
        }
        return minIdx;
    }

    private static int calculateDeformationNaive(int[][] mattrix)
    {
        // initialize
        int idx = 0;
        int[] indexes = indexesOfMinimalNumbers(mattrix[0]);
        List<Integer> sums = new LinkedList<>();
        int sum = 0;

        for(int firstRowIndex = 0; firstRowIndex < indexes.length; firstRowIndex++) {
            // select index with minimum from first row
            idx = indexes[firstRowIndex];
            sum += mattrix[firstRowIndex][idx];
            for (int i = 1; i < mattrix.length; i++) {
                // calculate minimum from 3 neighboring numbers from next row
                idx = returnIndexOfMinFromNeighborsAndCurrent(mattrix[i], idx);
                sum += mattrix[i][idx];
            }
            sums.add(sum);
            sum = 0;
        }
        return sums.stream().min(Integer::compareTo).orElse(Integer.MAX_VALUE);
    }

    public static int calculateDeformation(int[][] mattrix)
    {
        int[] deformations = mattrix[mattrix.length - 1];
        for(int i = mattrix.length - 1; i > 0; i--)
        {
            if(i == 2)
            {
                System.out.println("BREAK");
            }
            for(int j = 0; j < mattrix[i].length; j++)
            {
                int idx = returnIndexOfMinFromNeighborsAndCurrent(mattrix[i - 1], j);
                deformations[j] += mattrix[i - 1][idx];
            }
        }
        return Arrays
                .stream(deformations)
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
            int[] idxs = indexesOfMinimalNumbers(mattrix[0]);
            int deformation = calculateDeformationNaive(mattrix);
            //int def = calculateDeformation(mattrix);
            int def2 = shortestPath(mattrix);
            System.out.println("DONE");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

