package ru.stolexiy.openweather.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.stolexiy.openweather.BuildConfig
import ru.stolexiy.openweather.data.remote.dao.CurrentWeatherRemoteDao
import ru.stolexiy.openweather.data.remote.dao.ForecastWeatherRemoteDao
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDaoModule {
    companion object {
        private val logInterceptor: Interceptor by lazy {
            Interceptor { chain ->
                Timber.d("make request to ${chain.request().url}")
                chain.proceed(chain.request())
            }
        }

        private val authInterceptor: Interceptor by lazy {
            Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter(BuildConfig.API_AUTH_PARAM, BuildConfig.API_KEY)
                    .build()
                chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                    .run(chain::proceed)
            }
        }

        private val httpClient: OkHttpClient by lazy {
            OkHttpClient()
                .newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(logInterceptor)
                .build()
        }

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun currentWeatherDao(): CurrentWeatherRemoteDao {
            return retrofit.create(CurrentWeatherRemoteDao::class.java)
        }

        @Provides
        @Singleton
        fun forecastWeatherDao(): ForecastWeatherRemoteDao {
            return retrofit.create(ForecastWeatherRemoteDao::class.java)
        }
    }
}
