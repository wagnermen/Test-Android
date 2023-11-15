package pe.com.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pe.com.test.data.api.ApiInterface
import pe.com.test.data.repository.TestRepositoryImpl
import pe.com.test.domain.TestRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://api.themoviedb.org"
const val apiKey = "d9ae4921794c06bd0fdbd1463d274804"
const val language = "en-US"

@Module
@InstallIn(SingletonComponent::class)
object ApiManager {

    @Singleton
    @Provides
    fun provideTestApi(): ApiInterface {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey, language))
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideTestRepository(apiInterface: ApiInterface): TestRepository {
        return TestRepositoryImpl(apiInterface)
    }

}
