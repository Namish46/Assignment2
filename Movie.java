public class Movie {
    int id;
    String title;
    int releaseYear;
    String genre;
    double rating;
    int directorId;
    public Movie(int id, String title, int releaseYear,
                 String genre, double rating, int directorId) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
        this.directorId = directorId;
    }
}
