package com.as4me.movieapp

import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.as4me.movieapp.Adapters.CustomRecyclerAdapter
import com.as4me.movieapp.models.Film
import com.as4me.movieapp.models.FilmModel
import com.as4me.movieapp.models.Poster
import com.as4me.movieapp.services.ApiInterface
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        val sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val defaultValue = ""
        val imageURL = sharedPref.getString("imageURL",defaultValue)
        val IMDBID = sharedPref.getString("IMDBID",defaultValue)
        val title = sharedPref.getString("title",defaultValue)
        val description = sharedPref.getString("description",defaultValue)

        val imageView = findViewById<ImageView>(R.id.imageViewFilmActivityHover)
        val titleView = findViewById<TextView>(R.id.textViewtitle)
        val descView = findViewById<TextView>(R.id.textViewDesc)

        val apiInterface = ApiInterface.create()

        apiInterface.getPoster(IMDBID.toString()).enqueue( object : Callback<Poster> {
            override fun onResponse(call: Call<Poster>?, response: Response<Poster>?) {
                if(response?.isSuccessful == true)
                    Log.d("Retrofit",response.body()?.posters.toString())
                      Picasso.get().load(response?.body()?.posters?.get(0)?.link.toString()).centerCrop().fit().into(imageView);
            }

            override fun onFailure(call: Call<Poster>?, t: Throwable?) {

            }
        })


        apiInterface.getFilm(IMDBID.toString()).enqueue( object : Callback<Film> {
            override fun onResponse(call: Call<Film>?, response: Response<Film>?) {
                if(response?.isSuccessful == true)
                {
                    Log.d("Test",response.body().toString())
                    descView.text = response.body()?.plotLocal.toString()
                }
            }

            override fun onFailure(call: Call<Film>?, t: Throwable?) {

            }
        })

        titleView.text = title.toString()



        Log.d("TEST",imageURL.toString())
    }
}