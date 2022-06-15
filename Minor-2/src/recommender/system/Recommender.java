package recommender.system;

import java.io.FileWriter;
import java.util.*;
import java.io.IOException;

//main function
public class Recommender {
    public static void main(String[] args) throws IOException {
        final int NUM_RATINGS = 20;
        final int NUM_NEIGHBOURHOODS = 10;
        final int NUM_RECOMMENDATIONS = 20;
        final int MIN_VALUE_RECOMMENDATION = 4;
        final boolean RANDOM_RATINGS = false;

        Movies movies = new Movies();
        movies.readFile("data/u.item");
        Users users = new Users();
        users.readFile("data/u.data");
        FileWriter myWriter = new FileWriter("data/output.csv");

        HashMap<Integer, Integer> ratings = new HashMap<>();

        Random random = new Random();
        Scanner in = new Scanner(System.in);

        System.out.println("**********************************************");
        System.out.println("Obtaining user data: ");
        System.out.println("**********************************************");
        for (int i = 0; i < NUM_RATINGS; i++) {
            int idMovie = random.nextInt(movies.size());
            while (ratings.containsKey(idMovie)) {
                idMovie = random.nextInt(movies.size());
            }
            int rating;
            do {
                System.out.println("Movie: " + movies.getName(idMovie));
                System.out.println("Enter your rating (1-5):");
                if (RANDOM_RATINGS) {
                    rating = random.nextInt(5) + 1;
                    System.out.println(rating);
                } else {
                    rating = Integer.parseInt(in.nextLine());
                }
            } while (rating < 0 || rating > 5);
            ratings.put(idMovie, rating);
        }

        Map<Integer, Double> neighbourhoods = users.getNeighbourhoods(ratings, NUM_NEIGHBOURHOODS);
        Map<Integer, Double> recommendations = users.getRecommendations(ratings, neighbourhoods, movies.getMovies());

        ValueComparator valueComparator = new ValueComparator(recommendations);
        Map<Integer, Double> sortedRecommendations = new TreeMap<>(valueComparator);
        sortedRecommendations.putAll(recommendations);

        System.out.println("\t\t\t\t\t**************************************************");
        System.out.println("\n\t\t\t\t\t\tWELCOME TO RECOMMENDATION SYSTEM.\n");
        System.out.println("\t\t\t\t\t**************************************************");
        Iterator entries = sortedRecommendations.entrySet().iterator();
        int i = 0;
        
        try {	 
        while (entries.hasNext() && i < NUM_RECOMMENDATIONS) {
            Map.Entry entry = (Map.Entry) entries.next();
            if ((double) entry.getValue() >= MIN_VALUE_RECOMMENDATION) {
                System.out.println("Movie: " + movies.getName((int) entry.getKey()) + ", Rating: " + entry.getValue());
                // Writes this content into the specified file
                myWriter.write(movies.getName((int) entry.getKey()) + "," + entry.getValue() + "\n"); 
                i++;
            }
        }  
        
        // Closing is necessary to retrieve the resources allocated
        myWriter.close();
        System.out.println("Recommendation Successful!\nYour recommendations are in 'output.csv'\n");
        } catch (IOException e) {
        	System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}

