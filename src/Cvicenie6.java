import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Cvicenie6 {

    public static int[] loadRow(String path) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        String line = sc.nextLine().trim();
        int [] myArray = new int[line.length()];
        for(int i = 0; i < myArray.length; i++)
        {
            myArray[i] = Integer.parseInt("" + line.charAt(i));
        }
        return myArray;
    }

    public static int solve(int[] A)
    {
        int[][] MV = new int[A.length][A.length];

        int bob = 0;

        for (int interval = 0; interval < A.length; interval++) {
            for (int i = 0, j = interval; j < A.length; i++, j++) {
                // a = MV(i+2,j) - Alice chooses i Bob chooses i+1
                // b = MV(i+1,j-1)- Alice chooses i , Bob chooses j OR
                // Alice chooses j , Bob chooses i
                // c = MV(i,j-2)- Alice chooses j , Bob chooses j-1
                int a, b, c;
                if (i + 2 <= j)
                    a = MV[i + 2][j];
                else
                    a = 0;

                if (i + 1 <= j - 1)
                {
                    b = MV[i + 1][j - 1];
                }
                else
                {
                    b = 0;
                }
                if (i <= j - 2)
                {
                    c = MV[i][j - 2];
                }
                else
                    c = 0;
                // return value
                MV[i][j] = Math
                        .max(A[i] + Math.min(a, b), A[j] + Math.min(b, c));
            }
        }
        return MV[0][A.length - 1];
    }


    public static int optimalStrategy(int[] arr, int n)
    {
        int[][]values = new int[n][n];

        for(int k = 0; k < n; k++)
        {
            for(int j = k; j < n; j++)
            {
                int i = j - k;

                if(j == i)
                {
                    values[i][j] = arr[i];
                }
                else
                {
                    int a = 0;
                    int b = 0;
                    // vyberam i
                    if(arr[j] >= arr[i+1])
                    {
                        a = arr[i] + values[i+1][j-1];
                    }
                    else
                    {
                        a = arr[i] + values[i + 2][j];
                    }
                    // vyberam j
                    if (arr[j - 1] >= arr[i])
                    {
                        b = arr[j];
                        if(j-2 >= 0) b+= values[i][j - 2];
                    }
                    else
                    {
                        b = arr[j] + values[i + 1][j - 1];
                    }
                    values[i][j] = Math.max(b, a);
                }
            }
        }
        return values[0][n - 1];
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        int[] row = loadRow("data/cvicenie6data.txt");


        // Crupier equally clever
        int res = solve(row);

        // Stupid crupier - always takes higher coin
        int res2 = optimalStrategy(row, 1000);
        System.out.println("End");
    }
}
