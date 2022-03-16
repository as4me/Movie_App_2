package com.as4me.movieapp.services


import com.as4me.movieapp.models.Film
import com.as4me.movieapp.models.FilmModel
import com.as4me.movieapp.models.Poster

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {

    @GET("en/API/Top250Movies/k_6uyj5b9n")
    fun getMovies() : Call<FilmModel>

    @GET("en/API/ComingSoon/k_6uyj5b9n")
    fun coomingSoon() : Call<FilmModel>


    @GET("en/API/Posters/k_6uyj5b9n/{id}")
    fun getPoster(@Path("id") id: String) : Call<Poster>

    @GET("ru/API/Title/k_6uyj5b9n/{id}")
    fun getFilm(@Path("id") id: String) : Call<Film>


    companion object {

        var BASE_URL = "https://imdb-api.com/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}