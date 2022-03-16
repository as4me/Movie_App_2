package com.as4me.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.as4me.movieapp.Adapters.CustomRecyclerAdapter
import com.as4me.movieapp.Adapters.MySliderAdapter
import com.as4me.movieapp.R
import com.as4me.movieapp.databinding.FragmentHomeBinding
import com.as4me.movieapp.models.FilmModel
import com.as4me.movieapp.services.ApiInterface
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = root.findViewById(R.id.rec_1_250)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        val recyclerView2 : RecyclerView = root.findViewById(R.id.rec_2_new)
        recyclerView2.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://static.wikia.nocookie.net/marvel/images/a/ad/Avengers_Endgame_Banner.jpg/revision/latest/scale-to-width-down/700?cb=20190415135926&path-prefix=ru")
        imageList.add("https://upload.wikimedia.org/wikipedia/ru/thumb/a/a5/Loki_card.png/548px-Loki_card.png")

        val imageSlider = root.findViewById<SliderView>(R.id.imageSlider)

        setImageInSlider(imageList,imageSlider)



        val apiInterface = ApiInterface.create()
        Log.d("Retrofit","test")

        apiInterface.getMovies().enqueue( object : Callback<FilmModel>{
            override fun onResponse(call: Call<FilmModel>?, response: Response<FilmModel>?) {
                if(response?.body() != null)
                    Log.d("Retrofit","Success")
                    val movieResponse = response?.body()
                    val resultList = movieResponse?.items
                    recyclerView.adapter = CustomRecyclerAdapter(resultList,context!!.applicationContext)

                      Log.d("Retrofit",resultList?.get(0)?.title.toString())

            }

            override fun onFailure(call: Call<FilmModel>?, t: Throwable?) {

            }
        })

        apiInterface.coomingSoon().enqueue( object : Callback<FilmModel>{
            override fun onResponse(call: Call<FilmModel>?, response: Response<FilmModel>?) {
                if(response?.body() != null)
                    Log.d("Retrofit","Success")
                val movieResponse = response?.body()
                val resultList = movieResponse?.items
                recyclerView2.adapter = CustomRecyclerAdapter(resultList,context!!.applicationContext)

                Log.d("Retrofit",resultList?.get(0)?.title.toString())

            }

            override fun onFailure(call: Call<FilmModel>?, t: Throwable?) {

            }
        })



        return root
    }

//    private fun fillList() : List<String> {
//        val data = mutableListOf<String>()
//        (0..30).forEach{ i -> data.add("Film $i")}
//        return data
//    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView){
        val adapter = MySliderAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
        imageSlider.setScrollTimeInSec(8); //set scroll delay in seconds :

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}