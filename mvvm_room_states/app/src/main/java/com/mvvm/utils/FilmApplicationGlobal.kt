package com.mvvm.utils

import android.app.Application
import com.mvvm.interfaces.ApiFilmService
import com.mvvm.model.User
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/** in classe application
 * https://developer.android.com/training/dependency-injection/hilt-android#kotlin
 *
 * @HiltAndroidApp consente la generazione di codice Hilt, inclusa una classe base
 * per la tua applicazione che funge da contenitore di dipendenze a livello di applicazione.
 *
 * Questo componente generato da Hilt è collegato al ciclo di vita dell'oggetto Application
 * e fornisce dipendenze ad esso. Inoltre, è il componente principale dell'app, il che significa
 * che altri componenti possono accedere alle dipendenze fornite
 *
 * Hilt puoi fornire dipendenze per altre classi Android che hanno l'annotazione @AndroidEntryPoint
 * su (activity o  fragment, service, content provider, broadcast receiver, view).
 * Ad esempio, se scrivi @AndroidEntryPoint in una activity, dovresti allora anche annotare tutte le
 * attiviy in cui usi quel componente.
 *
 * https://developer.android.com/codelabs/android-hilt#0
 *
 **/

@HiltAndroidApp
class FilmApplicationGlobal : Application() {

    @JvmField
    var retrofitApiFilmService: ApiFilmService? = null
    var retrofit: Retrofit? = null

    companion object {
        private val TAG: String = FilmApplicationGlobal::class.java.simpleName
        val DEFAULT_SERVER_DOMAIN_URL: String = "https://api.themoviedb.org/"
        val GET_PICTURE_URL: String = "https://image.tmdb.org/t/p/w500"

        fun fixTooLongTitle(str: String?): String? {
            if (str == null) {
                return str
            }
            var isTooLong = str.length > 15
            if (isTooLong && str.contains(':')) {
                println("isTooLong $str")
                println("isTooLong $isTooLong")
                println("&& str.contains(':')  ${str.contains(':')}")
                var list = str.split(":")
                return "${list[0]}\n${list[1]}"
            }
            return str
        }

        /**
         * https://proandroiddev.com/suspend-what-youre-doing-retrofit-has-now-coroutines-support-c65bd09ba067
         * */
        val initRetrofitLazy = lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
            Retrofit.Builder().baseUrl(DEFAULT_SERVER_DOMAIN_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okClient)
                .build().create(ApiFilmService::class.java)
        }

        var usersFake = (arrayOf(
            User(
                id = "1",
                email = "marcckku@hotmail.com",
                description = "Android Developer",
                pwd = "123456",
                role = "admin"
            ),
            User(
                id = "2",
                email = "example2@hotmail.com",
                description = "Android Developer Example2",
                pwd = "example2",
                role = "user"
            ),
            User(
                id = "3",
                email = "example3@hotmail.com",
                description = "Android Developer Example3",
                pwd = "example3",
                role = "user"
            )
        )).toList()

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------
    private fun getRetrofit(URL: String?): Retrofit? {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okClient)
            .build()
        return retrofit
    }

    fun initServiceRetrofit(url: String?) {
        retrofitApiFilmService = if (url == null) {
            getRetrofit(DEFAULT_SERVER_DOMAIN_URL)!!.create(ApiFilmService::class.java)
        } else {
            getRetrofit(url)!!.create(ApiFilmService::class.java)
        }
    }
}