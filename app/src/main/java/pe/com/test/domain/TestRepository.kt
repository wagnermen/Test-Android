package pe.com.test.domain

import pe.com.test.data.model.response.MoviePopular
import pe.com.test.core.Resource
import pe.com.test.data.model.response.MovieUpcoming

interface TestRepository {

    suspend fun getPopularMovies(page: String): Resource<List<MoviePopular>>
    suspend fun getUpcomingMovies(page: String): Resource<List<MovieUpcoming>>

}