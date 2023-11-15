package pe.com.test.iu.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.com.test.core.Resource
import pe.com.test.data.model.response.MoviePopular
import pe.com.test.domain.TestRepository

@ExperimentalCoroutinesApi
class MyViewModelTest {

    @RelaxedMockK
    private lateinit var testRepository: TestRepository

    private lateinit var myViewModel: MyViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        myViewModel = MyViewModel(testRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when the repo brings the movies and sets to livedata`() = runTest{
        val movie = listOf(MoviePopular(1.0,2,true, "",2, true, "", "", "", emptyList(), "", 2.0, "", ""))
        coEvery { testRepository.getPopularMovies("1") } returns Resource.Success(movie)

        myViewModel.data()

        assert(myViewModel.data.value == movie)
    }

}