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
            System.out.println("2.Get Top 10 Movies");
            System.out.println("3.Get Movies by Genere");
            System.out.println("4.Get Movies by Directors");
            System.out.println("5.Get Movies by Realease Year");
            System.out.println("6.Get Movies by Year Range");
            System.out.println("7.Add New Movie");
            System.out.println("8.Update Movie Rating");
            System.out.println("9.Delet Movie");
            System.out.println("0.Exit");
            System.out.print("Enter choice:");
            int choice =sc.nextInt();
            sc.nextLine();
            if(choice==0) break;
            switch(choice) {
                case 1:getMovieInformation(sc);
                break;
                case 2:getTop10Movies();
                break;
                case 3:getMoviesByGenre(sc);
                break;
                case 4:getMoviesByDirector(sc);
                break;
                case 5:getMoviesByYear(sc);
                break;
                case 6:getMoviesByYearRange(sc);
                break;
                case 7:addNewMovie(sc);
                break;
                case 8:updateMovieRating(sc);
                break;
                case 9:deleteMovie(sc);
                break;
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
    //Get Top 10 rated movies
    public static void getTop10Movies() {
    // Make a copy of movies 
    ArrayList<Movie> temp = new ArrayList<>();
    for (int i=0;i<movies.size();i++) {
        temp.add(movies.get(i));
    }
    for (int i=0;i<temp.size()-1;i++) {
        for (int j=0;j<temp.size()-i-1;j++) {
            if (temp.get(j).rating<temp.get(j+1).rating) {
                Movie t =temp.get(j);
                temp.set(j,temp.get(j+1));
                temp.set(j+1,t);
            }
        }
    }
    System.out.println("Top 10 Rated Movies");
    for (int i=0;i<10 && i<temp.size();i++) {
        System.out.println((i+1)+"."+temp.get(i).title +"- Rating:" +temp.get(i).rating);
    }
}
//Get Movie by Genere
public static void getMoviesByGenre(Scanner sc){
    System.out.print("Enter genre: ");
    String genre=sc.nextLine();
    boolean found=false;
    for(int i=0;i<movies.size();i++){
        if(movies.get(i).genre.equalsIgnoreCase(genre)){
            System.out.println(movies.get(i).title);
            found=true;
        }
    }
    if(!found){
        System.out.println("No movies found for genre: "+genre);
    }
}
//Get Movies By Directors
public static void getMoviesByDirector(Scanner sc){
    System.out.print("Enter director name: ");
    String name=sc.nextLine().trim();
    int directorId=-1;
    for(int i=0;i<directors.size();i++){
        if(directors.get(i).name.trim().equalsIgnoreCase(name)){
            directorId=directors.get(i).id;
            break;
        }
    }
    if(directorId==-1){
        System.out.println("Director not found");
        return;
    }
    boolean found=false;
    for(int i=0;i<movies.size();i++){
        if(movies.get(i).directorId==directorId){
            System.out.println(movies.get(i).title);
            found=true;
        }
    }
}
//Get Movies By Release Year
public static void getMoviesByYear(Scanner sc){
    System.out.print("Enter release year: ");
    int year=sc.nextInt();
    boolean found=false;
    for(int i=0;i<movies.size();i++){
        if(movies.get(i).releaseYear==year){
            System.out.println(movies.get(i).title);
            found=true;
        }
    }
    if(!found)
        System.out.println("No movies found for year: "+year);
}   
//Get Movies by year Range
public static void getMoviesByYearRange(Scanner sc){
        System.out.print("Enter start year: ");
        int start=sc.nextInt();
        System.out.print("Enter end year: ");
        int end=sc.nextInt();
        boolean found=false;
        for(int i=0;i<movies.size();i++){
            int y=movies.get(i).releaseYear;
            if(y>=start && y<=end){
                System.out.println(movies.get(i).title);
                found=true;
            }
        }
        if(!found)
            System.out.println("No movies found between "+start+" and "+end);
    }
    //Add New Movie
    public static void addNewMovie(Scanner sc){
        System.out.print("Enter movie id: ");
        int id=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter title: ");
        String title=sc.nextLine();
        System.out.print("Enter release year: ");
        int year=sc.nextInt();
        sc.nextLine();
        System.out.print("Enter genre: ");
        String genre=sc.nextLine();
        System.out.print("Enter rating: ");
        double rating=sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter director id: ");
        int directorId=sc.nextInt();
        sc.nextLine();
        movies.add(new Movie(id,title,year,genre,rating,directorId));
        System.out.println("Movie added!");
    }

    //Update movie rating
    public static void updateMovieRating(Scanner sc){
    System.out.print("Enter movie id: "); 
    int id=sc.nextInt(); 
    sc.nextLine();
    System.out.print("Enter new rating: "); 
    double rating=sc.nextDouble(); 
    sc.nextLine();
    boolean found=false;
    for(int i=0;i<movies.size();i++){
        if(movies.get(i).id==id){movies.get(i).rating=rating; 
        found=true; 
        System.out.println("Rating updated!"); 
        break;
        }
    }
    if(!found){
    System.out.println("Movie not found");
}}
//Delete Movie
public static void deleteMovie(Scanner sc){
    System.out.print("Enter movie id: ");
    int id = sc.nextInt(); sc.nextLine();
    boolean found = false;
    for(int i=0;i<movies.size(); i++){
        if(movies.get(i).id == id){
            movies.remove(i);
            found = true;
            System.out.println("Movie deleted!");
            break;
        }
    }
    if(!found)
        System.out.println("Movie not found");
}
}
