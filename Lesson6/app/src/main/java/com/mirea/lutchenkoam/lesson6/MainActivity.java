package com.mirea.lutchenkoam.lesson6;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "com.mirea.lastname.Lesson6";
    private static final String GROUP_NUMBER_KEY = "group_number";
    private static final String LIST_NUMBER_KEY = "list_number";
    private static final String FAVORITE_MOVIE_KEY = "favorite_movie";

    private EditText groupNumberEditText;
    private EditText listNumberEditText;
    private EditText favoriteMovieEditText;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupNumberEditText = findViewById(R.id.groupNumberEditText);
        listNumberEditText = findViewById(R.id.listNumberEditText);
        favoriteMovieEditText = findViewById(R.id.favoriteMovieEditText);
        Button saveButton = findViewById(R.id.saveButton);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load saved values from SharedPreferences
        groupNumberEditText.setText(sharedPreferences.getString(GROUP_NUMBER_KEY, ""));
        listNumberEditText.setText(sharedPreferences.getString(LIST_NUMBER_KEY, ""));
        favoriteMovieEditText.setText(sharedPreferences.getString(FAVORITE_MOVIE_KEY, ""));

        saveButton.setOnClickListener(v -> {
            // Save values to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(GROUP_NUMBER_KEY, groupNumberEditText.getText().toString());
            editor.putString(LIST_NUMBER_KEY, listNumberEditText.getText().toString());
            editor.putString(FAVORITE_MOVIE_KEY, favoriteMovieEditText.getText().toString());
            editor.apply();
        });
    }
}