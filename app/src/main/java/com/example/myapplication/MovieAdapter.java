package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private OnItemClickListener listener;

    // Интерфейс для обработки кликов на элемент списка
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    // Конструктор
    public MovieAdapter(Context context, List<Movie> movieList, OnItemClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(String.valueOf(movie.getYear()));
        holder.rating.setText(String.valueOf(movie.getRating()));

        // Загрузка изображения постера с помощью Glide
        Glide.with(context)
                .load(movie.getPosterUrl())
                .into(holder.poster);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title, genre, year, rating;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            genre = itemView.findViewById(R.id.movie_genre);
            year = itemView.findViewById(R.id.movie_year);
            rating = itemView.findViewById(R.id.movie_rating);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }
}
