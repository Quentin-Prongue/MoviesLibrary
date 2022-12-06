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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    // RecyclerView pour afficher la liste des films
    private RecyclerView rv_movies;

    public static FragmentManager fragmentManager;
    public static MoviesAdapter moviesAdapter;
    public MovieManager movieManager;

    // Liste des films
    public static ArrayList<Movie> movies = new ArrayList<>();
    // Url de l'API
    String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=934a8072ea76f19c1778d6f7c23d35a1&language=fr-FR&page=";

    /**
     * Constructeur par défaut
     */
    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Rafraichit la liste des films
     */
    public static void refresh() {
        moviesAdapter.notifyDataSetChanged();
    }

    /**
     * Ouvre le fragment de détail d'un film
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        MainActivity.isMain = true;

        rv_movies = rootView.findViewById(R.id.rv_movies);

        fragmentManager = getActivity().getSupportFragmentManager();
        movieManager = new MovieManager();

        movieManager.getMovies(getContext(), url);

        moviesAdapter = new MoviesAdapter(movies);

        rv_movies.setAdapter(moviesAdapter);

        rv_movies.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int nbrOfItem = Objects.requireNonNull(rv_movies.getAdapter()).getItemCount();
                nbrOfItem = nbrOfItem - 80;

                // on récupère la index du dernier élément visible
                MainActivity.index = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastVisibleItemPosition();

                // si le nombre d'éléments dans la liste est a la moitier on charge les éléments suivants
                if (MainActivity.index > nbrOfItem) {
                    // scroll en bas
                    movieManager.getMovies(getContext(), url);
                }
            }
        });
        // scroll a l'index sauvegardé
        rv_movies.scrollToPosition(MainActivity.index);

        return rootView;
    }
}