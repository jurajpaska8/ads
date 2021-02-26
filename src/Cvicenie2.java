import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Cvicenie2 {

    /**
     * Loads rows of integers from given path.
     *
     * */
    private static int[] loadRows(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        final int NUMBER_OF_ROWS = 1000;
        int [] myArray = new int[NUMBER_OF_ROWS]; //TODO better declaration
        while(sc.hasNextLine())
        {
            for (int i=0; i<myArray.length; i++)
            {
                String line = sc.nextLine();
                myArray[i] = Integer.parseInt(line);
            }
        }
        return myArray;
    }

    public static int findShortestPathNoRecursion(int[] cities)
    {
        int[] visitedMin = new int[cities.length];
        // from second to last
        for(int i = cities.length - 2; i >= 0; i--)
        {
            // initialize minimal penalty
            double penMin = Double.MAX_VALUE;
            // iterate from nearest town to last town
            for(int j = i + 1; j < cities.length; j++)
            {
                // calculate fine if we choose jump from i to j
                double diff = cities[j] - cities[i];
                double fine = (400 - diff) * (400 - diff);
                // find out which jump has minimal penalty
                penMin = Math.min(fine + visitedMin[j], penMin);
            }
            visitedMin[i] = (int)penMin;

            if(i < 2)
            {
                System.out.println("Break");
            }
        }
        return visitedMin[0];
    }


    public static void main(String[] args) {
        //init
        //vzdialenost = 0
        //cyklus
        // 1) prirataj 400
        // 2) najdi mesto, s vzdialenostou mensou alebo rovnou prejdenej vzdialenosti
        // 3) vyrataj rozdiel od prejdenej vzdialenosti a vzdialenosti mesta
        // 4) vyrataj penalizaciu -> rozdiel**2
        // 5) prirataj k vzdialenosti 400 - rozdiel ... TODO takto uloha neznela, mozme prejst viac ako 400

        //.... 397..399.400.401..402.....796...801
        try
        {
            int[] citiesDistances = loadRows("data/cvicenie2data.txt");
            int[] citiesDistancesWithStart = new int[citiesDistances.length + 1];
            for(int i = 1; i<= citiesDistances.length; i++)
            {
                citiesDistancesWithStart[i] = citiesDistances[i - 1];
            }
            int fine = 0;
            //findShortestPath(citiesDistances, 0, false);
            fine = findShortestPathNoRecursion(citiesDistancesWithStart);
            System.out.println("Done. Fine = " + fine);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
