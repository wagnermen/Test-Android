package pe.com.test.data.repository

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import pe.com.test.core.Resource
import pe.com.test.data.api.ApiInterface
import pe.com.test.data.model.response.MoviePopularBase
import pe.com.test.data.model.response.MovieUpcomingBase
import retrofit2.Response
import java.io.IOException

class TestRepositoryImplTest {

    @RelaxedMockK
    private lateinit var apiInterface: ApiInterface

    private lateinit var testRepositoryImpl: TestRepositoryImpl

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        testRepositoryImpl = TestRepositoryImpl(apiInterface)
    }

    @Test
    fun `when the popular movies api is satisfactory`() = runBlocking {
        //Given
        val listModel = MoviePopularBase(1, 2, 3, emptyList())
        coEvery { apiInterface.popularMovies("1") } returns Response.success(listModel)

        //When
        val res = testRepositoryImpl

        //Then
        assertNotNull(res)
    }

    @Test
    fun `when the upcoming movies api is satisfactory`() = runBlocking {
        //Given
        val listModel = MovieUpcomingBase(1, 2, 3, emptyList())
        coEvery { apiInterface.upcomingMovies("1") } returns Response.success(listModel)

        //When
        val resp = testRepositoryImpl

        //Then
        assertNotNull(resp)
    }

    @Test
    fun `test getPopularMovies success`() {
        val mockResponse = MoviePopularBase(1, 2, 3, emptyList())

        coEvery { apiInterface.popularMovies("1") } returns Response.success(mockResponse)
        val result = runBlocking { testRepositoryImpl.getPopularMovies("1") }

        assertTrue(result is Resource.Success)
    }

    @Test
    fun `test getPopularMovies error`() {
        val mockException = IOException("Network error")

        coEvery { apiInterface.popularMovies("1") } throws mockException
        val result = runBlocking { testRepositoryImpl.getPopularMovies("1") }

        assertTrue(result is Resource.Error)
    }

}