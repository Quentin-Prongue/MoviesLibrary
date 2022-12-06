package com.pronque.movies;

import static com.pronque.movies.MoviesFragment.fragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    // Le film actuel
    private static Movie currentMovie;

    // Récupère les composants de la vue
    private static TextView title;
    private static TextView description;
    private static TextView year;
    private static ImageView image;
    private ImageButton button_back;

    /**
     * Constructeur du fragment
     */
    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Défini le film à traiter
     *
     * @param movie film à traiter
     */
    public static void setMovie(Movie movie) {
        currentMovie = movie;
    }

    /**
     * Affiche les détails du film
     */
    public static void displayMovieDetails() {
        title.setText(currentMovie.getTitle());
        description.setText(currentMovie.getOverview());
        year.setText("Date de sortie : " + currentMovie.getDate() + " | Langue : " + currentMovie.getLanguage());
        String imageUri = currentMovie.getImage();

        // Affiche l'image du film
        Picasso.get().load(imageUri).into(image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies_details, container, false);

        // Instancie un nouveau MovieManager
        MovieManager movieManager = new MovieManager();

        // Récupère le bouton de retour
        button_back = view.findViewById(R.id.button_back);

        // Au clic sur le bouton de retour, on revient au fragment précédent
        button_back.setOnClickListener(v -> {
            // Instancie le fragment MoviesFragment
            MoviesFragment moviesFragment = new MoviesFragment();

            // Remplace le fragment actuel par le fragment MoviesFragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, moviesFragment).addToBackStack(null).commit();
        });
        // Récupère les details du film
        movieManager.getMovieDetails(getContext(), currentMovie.getId());

        // Récupère le titre, la description, l'année et l'image du film
        title = view.findViewById(R.id.movie_name);
        description = view.findViewById(R.id.movie_overview);
        year = view.findViewById(R.id.movie_year);
        image = view.findViewById(R.id.film_image);

        // Affiche les détails du film
        displayMovieDetails();

        return view;
    }
}