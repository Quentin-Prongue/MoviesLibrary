package com.pronque.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    public static FragmentManager fragmentManager;
    public static MoviesAdapter moviesAdapter;
    // Liste des films
    public static ArrayList<Movie> movies = new ArrayList<>();
    public MovieManager movieManager;
    // Url de l'API
    String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=934a8072ea76f19c1778d6f7c23d35a1&language=fr-FR&page=";
    // RecyclerView pour afficher la liste des films
    private RecyclerView rv_movies;

    /**
     * Rafraichit la liste des films
     */
    public static void refresh() {
        moviesAdapter.notifyDataSetChanged();
    }

    /**
     * Ouvre le fragment de détail d'un film
     *
     * @param movie film à afficher
     */
    public static void openMovieDetailsFragment(Movie movie) {
        // Teste le fragment actuel
        if (MainActivity.isMain) {
            MainActivity.index = movies.indexOf(movie);
        }

        // Le fragment actuel est le fragment de détail
        MainActivity.isMain = false;

        // Crée le fragment de détail
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        // Ajoute le film au fragment
        MovieDetailsFragment.setMovie(movie);

        // Remplace le fragment actuel par le fragment de détail
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, movieDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        MainActivity.isMain = true;

        // Récupère la RecyclerView
        rv_movies = rootView.findViewById(R.id.rv_movies);

        fragmentManager = requireActivity().getSupportFragmentManager();
        movieManager = new MovieManager();

        // Récupère la liste des films
        movieManager.getMovies(getContext(), url);

        // Crée un adapter pour la RecyclerView
        moviesAdapter = new MoviesAdapter(movies);

        // Lie l'adapter à la RecyclerView
        rv_movies.setAdapter(moviesAdapter);

        // Crée un layout manager pour la RecyclerView
        rv_movies.setLayoutManager(new LinearLayoutManager(getContext()));

        // A chaque scroll on récupère les films suivants
        rv_movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Le nombre de films affichés
                int itemsNumber = Objects.requireNonNull(rv_movies.getAdapter()).getItemCount();
                itemsNumber -= 60;

                // Récupère le dernier film affiché
                MainActivity.index = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastVisibleItemPosition();

                // Si l'index est supérieur au nombre de films affichés on récupère les films suivants
                if (MainActivity.index > itemsNumber) {
                    movieManager.getMovies(getContext(), url);
                }
            }
        });
        // Scroll à l'index sauvegardé
        rv_movies.scrollToPosition(MainActivity.index);

        return rootView;
    }
}