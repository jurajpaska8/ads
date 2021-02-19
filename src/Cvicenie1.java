import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Cvicenie1 {

    /**
     * Loads mattrix of integers from given path.
     *
     * */
    private static int[][] loadMattrix(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        int rows = sc.nextInt();
        int columns = sc.nextInt();
        sc.nextLine();
        int [][] myArray = new int[rows][columns];
        while(sc.hasNextLine()) {
            for (int i=0; i<myArray.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++) {
                    myArray[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        return myArray;
    }

    /**
     * Returns true if absolute value of v1 is bigger than absolute value of v2.
     *
     * */
    private static boolean compareAbsoluteValues(int v1, int v2)
    {
        return Math.abs(v1) > Math.abs(v2);
    }

    /**
     * Returns min value of array.
     *
     * */
    private static int returnMin(int[] arr)
    {
        int min = Integer.MAX_VALUE;
        for(int i = 1; i < arr.length; i++)
        {
            if(min > arr[i])
            {
                min = arr[i];
            }
        }
        return min;
    }

    /**
     * Shuffles rows of array.
     *
     * */
    private static int[][] shuffleRows(int[][] arr)
    {
        List<int[]> asList = Arrays.asList(arr);
        Collections.shuffle(asList);
        return asList.toArray(new int[0][0]);
    }

    /**
     * This algo. is based on summing numbers with "select lowest absolute value of next sum" rule. It means that
     * it will add number in order to make sum nearest to 0 as possible.
     *
     * */
    private static int greedySumAlgo1(int[][] arr, List<Integer> numbers)
    {
        int sum = 0;
        int tmp;
        int addedNumber;
        for (int[] ints : arr) {
            // select first member of row and add to sum
            addedNumber = ints[0];
            for (int col = 1; col < ints.length; col++) {
                // try to find sum 'sum + ints[col]' nearest to 0
                if(compareAbsoluteValues(sum + ints[0], sum + ints[col]))
                {
                    addedNumber = ints[col];
                }
            }
            sum += addedNumber;
            numbers.add(addedNumber);
        }

        return sum;
    }

    /**
     * TODO idea: select lowest number until we reach minus sum. Then add numbers in order to approach 0
     *
     * */
    private static int greedySumAlgo2(int[][] arr, List<Integer> addedNumbers)
    {
        int sum = 0;
        int tmp = 0;
        for (int[] ints : arr) {
            // add lowest number until we reach minus sum
            if(sum > 0)
            {
                sum += returnMin(ints);
            }
            else
            {
                tmp = sum + ints[0];
                for (int col = 1; col < ints.length; col++) {
                    // try to find sum 'sum + ints[col]' nearest to 0
                    if(compareAbsoluteValues(tmp, sum + ints[col]))
                    {
                        tmp = sum + ints[col];
                    }
                }
                sum = tmp;
            }
        }
        return sum;
    }

    // TODO third approach

    public static void main(String[] args) {
        try
        {
            final int maxIter =100_000;
            int sum;
            int iter = 0;
            int[][] arr = loadMattrix("data/cvicenie1data.txt");
            List<Integer> addedNumbers = new LinkedList<>();
            System.out.println(Arrays.deepToString(arr));

            do
            {
                addedNumbers.clear();
                sum = greedySumAlgo2(arr, addedNumbers);
                System.out.println("iter: " + iter + " sum:" + sum);
                arr = shuffleRows(arr);
                iter++;
            } while(sum != 0 && iter < maxIter);

            int checkSum = addedNumbers.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Sum = " + checkSum);

        }
        catch(Exception e)
        {
            e.printStackTrace(); //TODO in future rework to meaningful solution
        }
    }
}
