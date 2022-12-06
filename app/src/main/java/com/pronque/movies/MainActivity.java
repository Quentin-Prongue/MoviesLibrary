package com.pronque.movies;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Classe de l'activité principale
 */
public class MainActivity extends AppCompatActivity {
    // Déclaration du FragmentManager
    public static FragmentManager fragmentManager;
    // Création de la liste de films
    public static ArrayList<Movie> moviesList = new ArrayList<>();
    // Si on est sur la page principale
    public static boolean isMain = true;
    // L'index du film sélectionné
    public static int index = 0;
    // Le numéro de la page
    public static int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupération du fragment manager
        fragmentManager = getFragmentManager();
    }
}