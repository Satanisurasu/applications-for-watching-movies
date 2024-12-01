package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView titleText, genreText, yearText, ratingText, viewUrlText;
    private ImageView posterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleText = findViewById(R.id.title);
        genreText = findViewById(R.id.genre);
        yearText = findViewById(R.id.year);
        ratingText = findViewById(R.id.rating);
        posterImage = findViewById(R.id.poster);
        viewUrlText = findViewById(R.id.view_url);  // Получаем ссылку

        // Получаем переданный объект Movie
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            // Заполняем поля данными из объекта movie
            titleText.setText(movie.getTitle());
            genreText.setText(movie.getGenre());
            yearText.setText(String.valueOf(movie.getYear()));
            ratingText.setText(String.valueOf(movie.getRating()));

            // Загружаем изображение постера с помощью Glide
            Glide.with(this).load(movie.getPosterUrl()).into(posterImage);

            // Если ссылка на фильм существует, отображаем её
            if (movie.getViewUrl() != null && !movie.getViewUrl().isEmpty()) {
                viewUrlText.setText("Watch Movie");
                viewUrlText.setOnClickListener(v -> {
                    // Открыть ссылку в браузере
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getViewUrl()));
                    startActivity(intent);
                });
            } else {
                viewUrlText.setVisibility(TextView.GONE); // Если ссылки нет, скрываем TextView
            }
        }
    }
}
