import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.Arrays;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();


        // put all actors into a string
        String temp = "";
        for (int i = 0;i < movies.size(); i++){
            temp += movies.get(i).getCast() + "|";
        }
        String[] actors = temp.split("\\|");
        ArrayList<String> actorsMatched = new ArrayList<String>();
        for (int i = 0;i < actors.length; i++){
            if (actors[i].toLowerCase().contains(searchTerm) && !actorsMatched.contains(actors[i])){
                actorsMatched.add(actors[i]);
            }
        }
        Collections.sort(actorsMatched);
        for (int i = 0;i < actorsMatched.size();i++){
            System.out.println(i+1 + ". " + actorsMatched.get(i));
        }
        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Movie> results = new ArrayList<Movie>();
        int choiceNum = 0;
        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            if (movies.get(i).getCast().contains(actorsMatched.get(choice-1))){
                results.add(movies.get(i));
                String title = movies.get(i).getTitle();

                // this will print index 0 as choice 1 in the results list; better for user!
                choiceNum++;

                System.out.println("" + choiceNum + ". " + title);
            }
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        // put all genres into a string
        String temp = "";
        for (int i = 0;i < movies.size(); i++){
            temp += movies.get(i).getGenres() + "|";
        }
        String[] temp2 = temp.split("\\|");
        ArrayList<String> genres = new ArrayList<String>(Arrays.asList(temp2));
        for (int i = 0; i < genres.size(); i++) {
            for (int k = i + 1; k < genres.size(); k++) {
                if (genres.get(i).equals(genres.get(k))) {
                    genres.remove(k);
                    k--;
                }
            }
        }

        Collections.sort(genres);
        for (int i = 0;i < genres.size();i++){
            System.out.println(i+1 + ". " + genres.get(i));
        }
        System.out.print("Choose a genre: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Movie> results = new ArrayList<Movie>();
        int choiceNum = 0;
        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            if (movies.get(i).getGenres().contains(genres.get(choice-1))){
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i = 0;i < results.size();i++){
            String title = results.get(i).getTitle();
            // this will print index 0 as choice 1 in the results list; better for user!
            choiceNum++;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> top50 = new ArrayList<Movie>();
        double max = 0;
        int highestIdx = 0;
        for (int i = 0; i < 51; i ++){
            for (int j = 0; j < movies.size();j++){
                if (movies.get(j).getUserRating() > max){
                    max = movies.get(j).getUserRating();
                    highestIdx = j;
                }
            }
            top50.add(movies.get(highestIdx));
            movies.remove(highestIdx);
            max = 0;
        }
        int choiceNum = 0;
        for (int i = 0;i < top50.size();i++){
            String title = top50.get(i).getTitle();
            // this will print index 0 as choice 1 in the results list; better for user!
            choiceNum++;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> top50 = new ArrayList<Movie>();
        int max = 0;
        int highestIdx = 0;
        for (int i = 0; i < 51; i ++){
            for (int j = 0; j < movies.size();j++){
                if (movies.get(j).getRevenue() > max){
                    max = movies.get(j).getRevenue();
                    highestIdx = j;
                }
            }
            top50.add(movies.get(highestIdx));
            movies.remove(highestIdx);
            max = 0;
        }
        int choiceNum = 0;
        for (int i = 0;i < top50.size();i++){
            String title = top50.get(i).getTitle();
            // this will print index 0 as choice 1 in the results list; better for user!
            choiceNum++;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}