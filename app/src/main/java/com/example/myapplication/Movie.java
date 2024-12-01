package com.example.myapplication;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String genre;
    private int year;
    private double rating;
    private String posterUrl;
    private String viewUrl; // Новое поле для ссылки на просмотр

    // Конструктор
    public Movie(String title, String genre, int year, double rating, String posterUrl, String viewUrl) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.viewUrl = viewUrl; // Инициализация нового поля
    }

    // Геттеры
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getViewUrl() {  // Новый геттер
        return viewUrl;
    }
}
