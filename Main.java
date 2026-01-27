import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Movie> movies =new ArrayList<>();
    static ArrayList<Actor> actors =new ArrayList<>();
    static ArrayList<Director> directors =new ArrayList<>();
    public static void main(String[] args) throws Exception{
        loadMovies();
        loadActors();
        loadDirectors();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("----- Movie Data System -----");
            System.out.println("1.Get Movie Info");
            System.out.println("0.Exit");
            System.out.print("Enter choice:");
            int choice =sc.nextInt();
            sc.nextLine();
            if(choice==0) break;
            switch(choice) {
                case 1:getMovieInformation(sc);
                default:System.out.println("Invalid choice");
            }
        }
        sc.close();
    }
    //LOAD MOVIES
    static void loadMovies() throws Exception{
        BufferedReader br =new BufferedReader(
                new FileReader("/home/namish/IdeaProjects/Assaign2/src/movies_large.csv")
        );
        br.readLine();
        String line;
        while ((line =br.readLine()) !=null){
            String[] d =line.split(",");
            movies.add(new Movie(
                    Integer.parseInt(d[0]),//For id
                    d[1],//For title
                    Integer.parseInt(d[2]),//For releaseYear
                    d[3],//For genre
                    Double.parseDouble(d[4]),//For rating
                    Integer.parseInt(d[5])//For directorId
            ));
        }
        br.close();
    }

    // LOAD ACTORS
    static void loadActors() throws Exception{
        BufferedReader br = new BufferedReader(
                new FileReader("/home/namish/IdeaProjects/Assaign2/src/actors_large.csv")
        );
        br.readLine();
        String line;
        while ((line = br.readLine()) !=null) {
            String[] d = line.split(",");
            actors.add(new Actor(
                    Integer.parseInt(d[0]),//For id
                    d[1],//For name
                    d[3]//For nationality
            ));
        }
        br.close();
    }
    //LOAD DIRECTORS
    static void loadDirectors() throws Exception{
        BufferedReader br = new BufferedReader(
                new FileReader("/home/namish/IdeaProjects/Assaign2/src/directors_large.csv")
        );
        br.readLine();
        String line;

        while ((line = br.readLine()) !=null) {
            String[] d = line.split(",");
            directors.add(new Director(
                    Integer.parseInt(d[0]),//For directorId
                    d[1],                  //For name
                    d[3]                   //For nationality
            ));
        }
        br.close();
    }
    //GET MOVIE INFO
    static void getMovieInformation(Scanner sc) {
        System.out.print("Enter movie title: ");
        String inputTitle =sc.nextLine();
        Movie Movie_found =null;
        for (int i=0;i<movies.size();i++) {
            Movie m=movies.get(i);
            if (m.title.equalsIgnoreCase(inputTitle)) {
                Movie_found = m;
                break;
            }
        }
        if (Movie_found ==null) {
            System.out.println("Movie not found");
            return;
        }

        System.out.println("--- Movie Details ---");
        System.out.println("Title:"+Movie_found.title);
        System.out.println("Year:"+Movie_found.releaseYear);
        System.out.println("Genre:"+Movie_found.genre);
        System.out.println("Rating:"+Movie_found.rating);
        System.out.print("Director:");
        for (int i=0;i<directors.size(); i++) {
            Director d=directors.get(i);
            if (d.id==Movie_found.directorId) {
                System.out.println(d.name + "("+d.nationality+")");
                break;
            }
        }
        System.out.print("Actors:");
        for (int i=0;i<actors.size();i++) {
            Actor a =actors.get(i);
            System.out.print(a.name+"("+a.nationality+"),");
        }
    }
}
