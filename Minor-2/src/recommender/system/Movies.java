package recommender.system;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Movies {
    
	// Map with the movie id as key and its name as value
	private Map<Integer, String> movies;

    //Constructor
    public Movies() {
        movies = new HashMap<>();
    }

    //returns the number of movies
    public int size() {
        return movies.size();
    }

    //returns the name of the movie with the id given
    public String getName(int id) {
        return movies.get(id);
    }

    //returns the whole map with movies
    public Map<Integer, String> getMovies() {
        return  movies;
    }
    
    //Fill map function with the films in the file
    public void readFile(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            while (line != null) {
                String[] splitLine = line.split("\\|");
                movies.put(Integer.parseInt(splitLine[0]), splitLine[1]);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
