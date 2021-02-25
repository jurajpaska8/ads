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

    /**
     * Note: Will fail if distance is 0 and cities start with number bigger than 0.
     * */
    private static int findClosestFromPassed(int[] cities, int distance)
    {
        for(int i = 0; i < cities.length; i++)
        {
            if(distance < cities[i])
            {
                return cities[i - 1];
            }

        }
        return cities[cities.length - 1];
    }

    public static void naiveAlgo() throws FileNotFoundException {
        final int DAY_SPEED = 400;
        final int CITY_COUNT = 1000;
        int[] citiesDistances = loadRows("data/cvicenie2data.txt");
        int distance = 0;
        int pen = 0;
        int cnt = 0;
        while (distance != citiesDistances[CITY_COUNT - 1]) {
            // travel 400 km
            distance += DAY_SPEED;
            System.out.println(cnt + ": Distance before: " + distance);
            // find last passed city
            int closestFromPassed = findClosestFromPassed(citiesDistances, distance);
            // calculate distance from last passed city
            int distanceBetweenMeAndLastCity = distance - closestFromPassed;
            // calculate fine
            int fine = distanceBetweenMeAndLastCity * distanceBetweenMeAndLastCity;
            // add to pen
            pen += fine;
            // return back to city
            distance = closestFromPassed;
            //cnt
            cnt++;
            System.out.println(cnt + ": Distance after: " + distance);
            System.out.println(cnt + ": Distance between me and city: " + distanceBetweenMeAndLastCity);
            System.out.println(cnt + ": Fine this day: " + fine);
            System.out.println(cnt + ": Fine all: " + pen);
        }
    }

    private static int findIndexOfClosestFromPassed(int[] cities, int distance)
    {
        if(distance == 0)
        {
            return 0;
        }
        for(int i = 0; i < cities.length; i++)
        {
            if(distance < cities[i])
            {
                return i - 1;
            }

        }
        return cities.length - 1;
    }

    private static int returnIndex(int[] cities, int value)
    {
        if(value == 0)
        {
            return 0;
        }
        for(int i = 0; i < cities.length; i++)
        {
            if(value == cities[i])
            {
                return i;
            }
        }
        return -1;
    }

    public static int findShortestPath(int[] citiesDistances, int idx, int fine, int min)
    {
        if(idx >= citiesDistances.length - 1)
        {
            if(fine<500_000 && fine<min)
            {
                System.out.println(fine);
                min = fine;
                return fine;
            }
        }
        int distance = citiesDistances[idx];
        int lastIdx = findIndexOfClosestFromPassed(citiesDistances, distance + 400);
        int pen = Integer.MAX_VALUE;
        //int wantedIdx = idx;
        for(int i = lastIdx; i >= idx + 1; i--)
        {
            int tmp = (distance + 400 - citiesDistances[i])*(distance + 400 - citiesDistances[i]);
            findShortestPath(citiesDistances, i, fine + tmp, min);
        }
        return fine;
    }

    public static void main(String[] args) {
        //init
        //vzdialenost = 0
        //cyklus
        // 1) prirataj 400
        // 2) najdi mesto, s vzdialenostou mensou alebo rovnou prejdenej vzdialenosti
        // 3) vyrataj rozdiel od prejdenej vzdialenosti a vzdialenosti mesta
        // 4) vyrataj penalizaciu -> rozdiel**2
        // 5) prirataj k vzdialenosti 400 - rozdiel

        //.... 397..399.400.401..402.....796...801
        try
        {
            int[] citiesDistances = loadRows("data/cvicenie2data.txt");
            int distance = 0;
            findShortestPath(citiesDistances, 0, 0, Integer.MAX_VALUE);

            System.out.println("Done");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
