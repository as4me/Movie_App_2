package com.as4me.movieapp.models

data class Poster(
    val backdrops: List<Backdrop>,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val posters: List<PosterX>,
    val title: String,
    val type: String,
    val year: String
)