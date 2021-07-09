package com.mvvm.utils

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mvvm.model.Film
import com.mvvm.model.ResultActorList
import com.mvvm.model.ResultDetailsFilm
import com.mvvm.model.ResultListFilm
import com.mvvm.service.ApiFilmService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit


class FilmApplicationGlobal : Application() {


    @JvmField
    var retrofitApiFilmService: ApiFilmService? = null


    var retrofit: Retrofit? = null




    init {
        initServiceRetrofit(null)
    }

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

        private suspend fun loadFilms(
            filmApplicationGlobal: FilmApplicationGlobal,
            liveData: MutableLiveData<List<Film>>
        ) {
            // Do an asynchronous operation to fetch films.
            if (filmApplicationGlobal.retrofitApiFilmService != null) {
                val call: Call<ResultListFilm> =
                    filmApplicationGlobal.retrofitApiFilmService!!.getFilmsAsyncV1()
                call.enqueue(object : Callback<ResultListFilm> {
                    override fun onResponse(
                        call: Call<ResultListFilm>,
                        response: Response<ResultListFilm>
                    ) {
                        if (response.isSuccessful) {
                            println("Success!!")
                            Log.e(TAG, "SUCCESS!!")
                            var results = response.body() as ResultListFilm
                            liveData.value = (results.films as List<Film>)
                        } else {
                            println("Errore getFilms() call retrofit")
                            Log.e(TAG, "NUMBER ERROR")
                        }
                    }

                    override fun onFailure(call: Call<ResultListFilm>, t: Throwable) {
                        Log.e(TAG, "onFailure")
                        t.printStackTrace()
                    }
                })
            }
        }

        val initRetrofitLazy =   lazy {
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



    /**
     * https://proandroiddev.com/suspend-what-youre-doing-retrofit-has-now-coroutines-support-c65bd09ba067
     * */
    fun initServiceRetrofit3(){
        val webservice by lazy {
            retrofitApiFilmService = Retrofit.Builder()
                .baseUrl(DEFAULT_SERVER_DOMAIN_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(ApiFilmService::class.java)
        }
    }

}