package com.pronque.movies;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Classe de l'adaptateur de la liste des films
 * Un objet Adapter agit comme un pont entre un AdapterView et les données sous-jacentes de cette vue.
 * L'adaptateur permet d'accéder aux éléments de données.
 * L'adaptateur est également chargé de créer une vue pour chaque élément de l'ensemble de données. (Doc Android)
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private ArrayList<Movie> movies;

    /**
     * Constructeur de la classe MoviesAdapter
     *
     * @param films la liste des films
     */
    public MoviesAdapter(ArrayList<Movie> films) {
        movies = films;
    }

    /**
     * Méthode qui permet de créer un ViewHolder
     *
     * @param parent   parent de la vue
     * @param viewType type de la vue
     * @return le ViewHolder
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Récupère l'affichage des films
        View filmView = inflater.inflate(R.layout.movie_item, parent, false);

        // Crée un ViewHolder pour le film
        MovieViewHolder viewHolder = new MovieViewHolder(filmView);

        // Pour chaque élément de la liste on récupère le film et on l'affiche
        viewHolder.itemView.setOnClickListener(v -> {
            // Instancie un nouveau MovieManager
            MovieManager movieManager = new MovieManager();

            // Récupère l'index du film
            int index = viewHolder.getAdapterPosition();

            // Récupère le film à l'index
            Movie movie = movieManager.getMovie(index);

            // Ajoute le film à la liste des films
            MainActivity.moviesList.add(movie);

            // Ouvre le fragment de détail du film
            MoviesFragment.openMovieDetailsFragment(movie);
        });

        return viewHolder;
    }

    /**
     * Permet d'afficher l'image et le titre du film
     *
     * @param holder le holder
     * @param index  l'index du film
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int index) {
        // Récupère le film à l'index
        Movie movie = movies.get(index);

        // Récupère les textViews du layout
        ImageView image_view = holder.image_view;
        TextView title_view = holder.title_view;
        TextView rating_view = holder.rating_view;

        // Affiche l'image et le titre du film
        Picasso.get().load(movie.getImage()).into(image_view);
        title_view.setText(movie.getTitle());

        if (!movie.getRating().equals("Aucune note")) {
            // Change la couleur en fonction de la note
            float rating = Float.parseFloat(movie.getRating());
            // Si la note est supérieure à 7 on affiche en vert
            if (rating >= 7) {
                rating_view.setTextColor(Color.GREEN);
                // Sinon si la note est supérieure à 5 on affiche en jaune
            } else if (rating >= 5) {
                rating_view.setTextColor(Color.YELLOW);
                // Sinon on affiche en rouge
            } else {
                rating_view.setTextColor(Color.RED);
            }
        }
        // Affiche la note du film
        rating_view.setText(movie.getRating());
    }

    /**
     * Retourne le nombre d'éléments
     *
     * @return le nombre d'éléments
     */
    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * Classe interne pour le ViewHolder du RecyclerView
     */
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        // Les éléments de la vue
        public ImageView image_view;
        public TextView title_view;
        public TextView rating_view;

        /**
         * Constructeur du ViewHolder
         *
         * @param itemView la vue
         */
        public MovieViewHolder(View itemView) {
            super(itemView);

            // Récupère les éléments de la vue
            image_view = itemView.findViewById(R.id.movie_image);
            title_view = itemView.findViewById(R.id.movie_name);
            rating_view = itemView.findViewById(R.id.movie_rating);
        }
    }
}
