package com.pronque.movies;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe qui gère les films
 */
public class MovieManager {
    /**
     * Constructeur de la classe MovieManager
     */
    public MovieManager() {
    }

    /**
     * Récupère le film avec l'id passé en paramètre
     *
     * @param index index du film
     * @return le film
     */
    public Movie getMovie(int index) {
        return MoviesFragment.movies.get(index);
    }

    /**
     * Récupère les films depuis l'API
     *
     * @param context contexte de l'application
     * @param url     url de l'API
     */
    public void getMovies(Context context, String url) {
        // Complète l'url avec le numéro de la page
        url += MainActivity.pageNumber;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        // Récupère le tableau de films
                        JSONArray results = json.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            // Récupère le film
                            JSONObject movie = results.getJSONObject(i);

                            // Récupère les informations du film
                            String id = "null";
                            if (movie.has("id")) {
                                id = movie.getString("id");
                            }

                            String title = "Aucun titre";
                            if (movie.has("title")) {
                                title = movie.getString("title");
                            }

                            String release_date = "Aucune date de sortie";
                            if (movie.has("release_date")) {
                                release_date = movie.getString("release_date");
                            }

                            String overview = "Aucune description";
                            if (movie.has("overview")) {
                                overview = movie.getString("overview");
                            }

                            String poster_path = "null";
                            if (movie.has("poster_path")) {
                                poster_path = movie.getString("poster_path");
                            }

                            String language = "Aucune langue";
                            if (movie.has("original_language")) {
                                language = movie.getString("original_language");
                            }

                            String rating = "Aucune note";
                            if (movie.has("vote_average")) {
                                rating = movie.getString("vote_average");
                            }

                            // Crée un film avec les informations récupérées
                            MoviesFragment.movies.add(new Movie(id, title, release_date, overview, poster_path, language, rating));
                        }
                        MainActivity.pageNumber++;
                        MoviesFragment.refresh();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.d(TAG, "onErrorResponse: " + error));
        queue.add(stringRequest);
    }

    /**
     * Récupère les détails du film avec l'id passé en paramètre
     *
     * @param context contexte de l'application
     * @param movieID id du film
     */
    public void getMovieDetails(Context context, String movieID) {
        String url = "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=934a8072ea76f19c1778d6f7c23d35a1&language=fr-FR";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Récupère le json du film
                        JSONObject movieJSON = new JSONObject(response);

                        // Récupère les informations du film
                        String id = "null";
                        if (movieJSON.has("id")) {
                            id = movieJSON.getString("id");
                        }

                        String title = movieJSON.getString("title");

                        String release_date = "Aucune date de sortie";
                        if (movieJSON.has("release_date")) {
                            release_date = movieJSON.getString("release_date");
                        }

                        String overview = "Aucune description";
                        if (movieJSON.has("overview")) {
                            overview = movieJSON.getString("overview");
                        }

                        String poster_path = "null";
                        if (movieJSON.has("poster_path")) {
                            poster_path = movieJSON.getString("poster_path");
                        }

                        String language = "Aucune langue";
                        if (movieJSON.has("original_language")) {
                            language = movieJSON.getString("original_language");
                        }

                        String rating = "Aucune note";
                        if (movieJSON.has("vote_average")) {
                            rating = movieJSON.getString("vote_average");
                        }

                        // Crée un film avec les informations récupérées
                        MovieDetailsFragment.setMovie(new Movie(id, title, release_date, overview, poster_path, language, rating));
                        // Affiche les details du film
                        MovieDetailsFragment.displayMovieDetails();
                    } catch (JSONException e) {
                        System.out.println("Erreur lors de la récupération des données");
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }, error -> Log.d(TAG, "onErrorResponse: " + error));
        queue.add(stringRequest);
    }
}
