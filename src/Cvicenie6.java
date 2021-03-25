import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Cvicenie6 {

    public class Double
    {
        public int first = -1;
        public int second = -1;
    }
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
                //////////////////////////////////
                if (i + 1 <= j - 1)
                    b = MV[i + 1][j - 1];
                else
                    b = 0;
                //////////////////////////////////
                if (i <= j - 2)
                    c = MV[i][j - 2];
                else
                    c = 0;
                //////////////////////////////////
                MV[i][j] = Math
                        .max(A[i] + Math.min(a, b), A[j] + Math.min(b, c));
            }
        }
        return MV[0][A.length - 1];
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        int[] row = loadRow("data/cvicenie6data.txt");
        int meSum = 0;
        int crupierSum = 0;
        int start = 0;
        int end = row.length - 1;
        int numOfCoins =0;
        for(int i = 0; i < row.length / 2; i++)
        {
            if(row[start + 1] > row[end - 1])
            {
                meSum += row[end];
                numOfCoins++;
                end --;
            }
            else
            {
                meSum += row[start];
                numOfCoins++;
                start++;
            }
        }

        int res = solve(row);
        System.out.println("End");
    }
}
