import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Cvicenie1 {

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

    private static int returnMinAbsValue(int[] arr)
    {
        int min = Integer.MAX_VALUE;
        for(int i = 1; i < arr.length; i++)
        {
            if(min > Math.abs(arr[i]))
            {
                min = Math.abs(arr[i]);
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

    private static int greedySumAlgo1(int[][] arr, List<Integer> numbers)
    {
        int sum = 0;
        int tmp;
        int addedNumber;
        for (int[] ints : arr) {
            // select first member of row and add to sum
            tmp = sum + ints[0];
            addedNumber = ints[0];
            for (int col = 1; col < ints.length; col++) {
                // try to find sum 'sum + ints[col]' nearest to 0
                if(compareAbsoluteValues(tmp, sum + ints[col]))
                {
                    tmp = sum + ints[col];
                    addedNumber = ints[col];
                }
            }
            sum = tmp;
            numbers.add(addedNumber);
        }

        return sum;
    }

    /**
     * TODO
     *
     * */
    private static int greedySumAlgo2(int[][] arr)
    {
        int sum = 0;
        int tmp = 0;
        for (int[] ints : arr) {
            // select first member of row and add to sum
            sum += sum + ints[0];
            for (int col = 1; col < ints.length; col++) {
                // try to find sum 'sum + ints[col]' nearest to 0
                if(compareAbsoluteValues(tmp, sum + ints[col]))
                {
                    tmp = sum + ints[col];
                }
            }
            sum = tmp;
        }

        return sum;
    }

    public static void main(String[] args) {
        try
        {
            int sum;
            int iter = 0;
            int[][] arr = loadMattrix("data/cvicenie1data.txt");
            List<Integer> addedNumbers = new LinkedList<>();
            System.out.println(Arrays.deepToString(arr));

            do
            {
                addedNumbers.clear();
                sum = greedySumAlgo1(arr, addedNumbers);
                System.out.println("iter: " + iter + " sum:" + sum);
                arr = shuffleRows(arr);
                iter++;
            } while(sum != 0);

            int checkSum = 0;
            for(int i : addedNumbers){
                checkSum += i;
            }
            System.out.println(checkSum);

            checkSum = addedNumbers.stream().mapToInt(Integer::intValue).sum();
            System.out.println(checkSum);

        }
        catch(Exception e)
        {
            e.printStackTrace(); //TODO in future rework to meaningful solution
        }
    }
}
