package pe.com.test.iu.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pe.com.test.iu.movie.adapter.AdapterMovieUpcoming
import pe.com.test.iu.movie.adapter.MoviePopularAdapter
import pe.com.test.databinding.FragmentFirstBinding
import pe.com.test.iu.movie.MyViewModel

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val viewModel: MyViewModel by viewModels()
    private var adapterUpcoming = AdapterMovieUpcoming()
    private lateinit var moviePopularAdapter: MoviePopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        //view_model = MyViewModel(requireActivity().application)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        if (viewModel.data.value == null) {
            viewModel.data()
        }
    }

    private fun observer() {
        viewModel.isViewLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.data.observe(viewLifecycleOwner) { moviePopular ->
            moviePopularAdapter = MoviePopularAdapter(moviePopular)
            binding.moviePopularRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.moviePopularRecyclerView.adapter = moviePopularAdapter
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.baseView, error, Snackbar.LENGTH_LONG).show()
        }

        viewModel.movieUpcoming.observe(viewLifecycleOwner) { movieUpcoming ->
            adapterUpcoming.data = movieUpcoming
            binding.movieUpcomingRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.movieUpcomingRecyclerView.adapter = adapterUpcoming
        }
    }

}