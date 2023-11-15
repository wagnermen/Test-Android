package pe.com.test.data.api

import pe.com.test.data.model.response.MoviePopularBase
import pe.com.test.data.model.response.MovieUpcomingBase
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("3/movie/popular")
    suspend fun popularMovies(
        @Query("page") page: String
    ): Response<MoviePopularBase>

    @GET("3/movie/upcoming")
    suspend fun upcomingMovies(
        @Query("page") page: String
    ): Response<MovieUpcomingBase>
}