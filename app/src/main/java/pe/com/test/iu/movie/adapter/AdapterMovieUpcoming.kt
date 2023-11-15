package pe.com.test.iu.movie.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pe.com.test.R
import pe.com.test.data.model.response.MovieUpcoming
import java.util.concurrent.Executors

class AdapterMovieUpcoming() :
    RecyclerView.Adapter<AdapterMovieUpcoming.MovieUpcomingViewHolder>() {

    var data: List<MovieUpcoming?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieUpcomingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_upcoming_item, parent, false)
        return MovieUpcomingViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieUpcomingViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item!!)
        val bundle = bundleOf("title" to item.title, "posterPath" to item.posterPath, "overview" to item.overview)
        holder.itemView.setOnClickListener{ view ->
            view.findNavController().navigate(R.id.action_FirstFragment_to_DetailFragment, bundle)
        }
    }

    class MovieUpcomingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movieUpcoming: MovieUpcoming) {
            val image_view = itemView.findViewById<ImageView>(R.id.posterImageView)
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap? = null
            executor.execute {
                val imageURL = "https://image.tmdb.org/t/p/w185/${movieUpcoming.posterPath}"
                try {
                    val `in` = java.net.URL(imageURL).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        image_view.setImageBitmap(image)
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

}