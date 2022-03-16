package com.as4me.movieapp.Adapters

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.as4me.movieapp.FilmActivity
import com.as4me.movieapp.MainActivity
import com.as4me.movieapp.R
import com.as4me.movieapp.Sign_up_screen
import com.as4me.movieapp.models.Item
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class CustomRecyclerAdapter(private val film: List<Item>?,val context: Context) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filmimage: ImageView = itemView.findViewById(R.id.imageViewcard)
        var title: TextView = itemView.findViewById(R.id.textView2)
        val holder_cart: ConstraintLayout = itemView.findViewById(R.id.holder_cart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // меняем картинку в зависти от фильма
        // меняем текст в зависимости от фильма
        val preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        var editor = preferences.edit()


        holder.title.text = film?.get(position)?.title
        Picasso.get().load(film?.get(position)?.image).resize(140, 170).centerCrop().into(holder.filmimage);
       // holder.filmimage.load(film?.get(position)?.image)
        Log.d("Retrofit",film?.get(position)?.image.toString())

        holder.holder_cart.setOnClickListener{
            editor.putString("title",film?.get(position)?.title)

            editor.putString("imageURL",film?.get(position)?.image)
            editor.putString("IMDBID",film?.get(position)?.id)
            editor.commit()

            val intent = Intent(context, FilmActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = film?.size!!

}