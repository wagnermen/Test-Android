package pe.com.test.iu.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pe.com.test.data.model.response.MoviePopular
import pe.com.test.data.model.response.MovieUpcoming
import pe.com.test.core.Resource
import pe.com.test.domain.TestRepository
import pe.com.test.iu.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val testRepository: TestRepository) : BaseViewModel() {

    private val _data = MutableLiveData<List<MoviePopular?>>()
    val data: LiveData<List<MoviePopular?>> = _data

    private val _movieUpcoming = MutableLiveData<List<MovieUpcoming?>>()
    val movieUpcoming: LiveData<List<MovieUpcoming?>> = _movieUpcoming

    val error = MutableLiveData<String>()

    fun data() {
        viewModelScope.launch {
            isViewLoading.value = true
            async {
                when (val response = testRepository.getPopularMovies("1")) {
                    is Resource.Success -> {
                        _data.value = response.data
                    }

                    is Resource.Error -> {
                        error.value = response.message
                    }
                }
            }.await()

            async {
                when (val response = testRepository.getUpcomingMovies("1")) {
                    is Resource.Success -> {
                        _movieUpcoming.value = response.data
                    }
                    is Resource.Error -> {
                        error.value = response.message
                    }
                }
                isViewLoading.value = false
            }.await()
        }
    }

}