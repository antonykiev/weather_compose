package com.test.weatherspbmsc.di

import android.app.Application
import androidx.room.Room
import com.test.weatherspbmsc.data.rest.RemoteDataSource
import com.test.weatherspbmsc.data.rest.RestApi
import com.test.weatherspbmsc.data.storage.WeatherDao
import com.test.weatherspbmsc.data.storage.WeatherDataBase
import com.test.weatherspbmsc.data.storage.WeatherStorageDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Provides Singleton]
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
        }
    }

    @[Provides Singleton]
    fun provideNoteDataBase(app: Application): WeatherDataBase {
        return Room.databaseBuilder(
            app,
            WeatherDataBase::class.java,
            WeatherDataBase.DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun providesWeatherStorageDataSource(db: WeatherDataBase): WeatherDao {
        return WeatherStorageDataSource(db.weatherDao)
    }

    @[Provides Singleton]
    fun provideRemoteRepo(httpClient: HttpClient) : RestApi {
        return RemoteDataSource(httpClient)
    }
}