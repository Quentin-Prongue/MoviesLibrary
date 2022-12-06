package com.pronque.movies;

/**
 * Classe qui représente un film avec ses attributs
 */
public class Movie {
    private String id;
    private String title;
    private String overview;
    private String image;
    private String date;
    private String language;
    private String rating;

    /**
     * Constructeur de la classe Movie
     *
     * @param id       id du film
     * @param title    titre du film
     * @param date     date de sortie du film
     * @param overview description du film
     * @param image    image du film
     * @param language langue du film
     */
    public Movie(String id, String title, String date, String overview, String image, String language, String rating) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.image = image;
        this.date = date;
        this.language = language;
        this.rating = rating;
    }

    /**
     * @return id du film
     */
    public String getId() {
        return id;
    }

    /**
     * @return titre du film
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return description du film
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @return image du film
     */
    public String getImage() {
        return "https://image.tmdb.org/t/p/w500" + image;
    }

    /**
     * @return date de sortie du film
     */
    public String getDate() {
        return date;
    }

    /**
     * @return langue du film
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return note du film
     */
    public String getRating() {
        return rating;
    }
}
