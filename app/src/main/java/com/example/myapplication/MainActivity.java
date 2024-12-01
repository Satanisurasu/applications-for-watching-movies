package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        adapter = new MovieAdapter(this, movieList, movie -> {
            // Обработчик клика по фильму
            openMovieInBrowser(movie);  // Открываем фильм в браузере
        });

        recyclerView.setAdapter(adapter);

        loadMovies(); // Загрузка фильмов
    }

    // Метод для открытия ссылки на фильм
    private void openMovieInBrowser(Movie movie) {
        if (movie.getViewUrl() != null && !movie.getViewUrl().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getViewUrl()));
            startActivity(intent); // Открыть ссылку в браузере
        } else {
            Toast.makeText(MainActivity.this, "Ссылка на фильм не найдена", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод загрузки фильмов
    private void loadMovies() {
        String apiKey = "f4305861";  // Ваш API ключ
        String[] imdbIDs = {
                "tt3896198",  // Guardians of the Galaxy Vol. 2
                "tt0848228",  // The Avengers
                "tt4154756",  // Avengers: Infinity War
                "tt1838556",  // Jumanji: Welcome to the Jungle
                "tt2380307",  // Spider-Man: Homecoming
                "tt1825683",  // Wonder Woman
                "tt1631867",  // Deadpool 2
                "tt0371746",  // The Dark Knight
                "tt0468569",  // The Dark Knight
                "tt1856101"   // The Hunger Games
        };

        // Создаем очередь запросов с использованием Volley
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // Используем контекст MainActivity

        // Для каждого IMDb ID создаем запрос
        for (String imdbID : imdbIDs) {
            // Формируем URL для запроса к OMDb API
            String url = "http://www.omdbapi.com/?i=" + imdbID + "&apikey=" + apiKey;

            // Создаем объект запроса
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Получаем данные из ответа
                                String title = response.getString("Title");
                                String genre = response.getString("Genre");
                                int year = Integer.parseInt(response.getString("Year"));
                                double rating = Double.parseDouble(response.getString("imdbRating"));
                                String posterUrl = response.getString("Poster");
                                String movieUrl = "https://www.imdb.com/title/" + response.getString("imdbID");

                                // Создаем объект Movie и добавляем его в список
                                movieList.add(new Movie(title, genre, year, rating, posterUrl, movieUrl));
                                adapter.notifyDataSetChanged();  // Обновляем список
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Ошибка при парсинге данных", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show();
                }
            });

            // Добавляем запрос в очередь
            queue.add(jsonObjectRequest);
        }
    }
}
