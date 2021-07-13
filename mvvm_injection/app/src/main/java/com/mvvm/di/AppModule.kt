package com.mvvm.di

import android.content.Context
import com.mvvm.interfaces.ApiFilmService
import com.mvvm.repository.FilmRepository
import com.mvvm.serviceImpl.ApiFilmServiceImpl
import com.mvvm.utils.FilmApplicationGlobal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


/**
 *  PAGINA UFFICIALE PER SETUP +  IMPLEMENTATION CLASSES
 *  DAGER : https://dagger.dev/hilt/gradle-setup
 *  ANDROID : https://developer.android.com/training/dependency-injection/hilt-android#component-lifetimes
 *
 * - INIEZZIONE A LIVELLO DI VIEWMODELS E PER TUTTE LE CLASSI DELLO STESSO TIPO-
 *  Tutti i modelli Hilt View sono forniti dal ViewModelComponent che
 *  segue lo stesso ciclo di vita di un ViewModel, ovvero sopravvive
 *  alle modifiche alla configurazione.
 *
 *  - @Provides = usato quando la classe non è di tua propieta o utilizza una libreria esterna non tua.
 *   Esempio :  Retrofit, OkHttpClient o bases de datos de Room, ApplicationContext, o classi
 *   che dipendono da altre come nel mio caso FilmRepository),
 */

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideApplication(@ApplicationContext app: Context): FilmApplicationGlobal {
        return app as FilmApplicationGlobal
    }

    @Provides
    @ViewModelScoped
    fun provideRandomString(): String {
        return "TESTING INJECT RANDOM STRING = 5yt53wgeta644tagfegtrsjkyujkr6jytae"
    }

    @Provides
    @ViewModelScoped
    fun provideFilmRepository(@ApplicationContext applicationContext : Context): FilmRepository {
        return FilmRepository(provideApiFilmServiceLazy(), provideApplication(applicationContext))
    }

   //Standard init retrofit
   @Provides
   fun provideApiFilmService(): ApiFilmService{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
       return Retrofit.Builder().baseUrl(FilmApplicationGlobal.DEFAULT_SERVER_DOMAIN_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okClient)
            .build().create(ApiFilmService::class.java)
    }

    //Lazy init retrofit
    @Provides
    fun provideApiFilmServiceLazy(): Lazy<ApiFilmService>{
        val client : Lazy<ApiFilmService> = FilmApplicationGlobal.initRetrofitLazy
        return client
    }

    @ViewModelScoped
    @Provides
    fun provideApiFilmServiceImpl() : ApiFilmServiceImpl {
        val apiFilmService: ApiFilmService = provideApiFilmServiceLazy().value
        return ApiFilmServiceImpl(apiFilmService)
    }
}