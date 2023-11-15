package pe.com.test.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.com.test.data.model.response.MoviePopular
import pe.com.test.core.Resource
import pe.com.test.data.api.ApiInterface
import pe.com.test.data.model.response.MovieUpcoming
import pe.com.test.domain.TestRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    TestRepository {

    override suspend fun getPopularMovies(
        page: String
    ): Resource<List<MoviePopular>> {
        return withContext(Dispatchers.IO){
            try {
                val response = apiInterface.popularMovies(page)
                if (response.isSuccessful){
                    Resource.Success(response.body()!!.results)
                }else{
                    Resource.Error("Error en la solicitud: "+response.code().toString())
                }
            }catch (e: Exception){
                Resource.Error(e.message)
            }catch (e: HttpException){
                Resource.Error(e.message)
            }
        }
    }

    override suspend fun getUpcomingMovies(
        page: String
    ): Resource<List<MovieUpcoming>> {
        return withContext(Dispatchers.IO){
            try {
                val response = apiInterface.upcomingMovies(page)
                if (response.isSuccessful){
                    Resource.Success(response.body()!!.results)
                } else{
                    Resource.Error("Error en la solicitud: "+response.code().toString())
                }
            }catch (e: Exception){
                Resource.Error(e.message)
            }catch (e: IOException){
                Resource.Error(e.message)
            }
        }
    }
}