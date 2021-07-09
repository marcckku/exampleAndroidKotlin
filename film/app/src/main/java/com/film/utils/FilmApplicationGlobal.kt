package com.film.utils

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import com.film.model.ResultActorList
import com.film.model.ResultDetailsFilm
import com.film.service.ApiFilmService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit


class FilmApplicationGlobal : Application(){



    @JvmField
    var retrofitApiFilmService: ApiFilmService? = null

    @JvmField
    var resultDetailsFilm : ResultDetailsFilm?=null

    @JvmField
    var resultActorList : ResultActorList?=null


    var retrofit: Retrofit? = null

    init {
        initServiceRetrofit(null)
    }

    companion object{
        private val TAG: String = FilmApplicationGlobal::class.java.simpleName


        val DEFAULT_SERVER_DOMAIN_URL: String = "https://api.themoviedb.org/"
        val GET_PICTURE_URL: String = "https://image.tmdb.org/t/p/w500"

        fun getDrawableFromUrl(url: String?): Drawable? {
            var draw : Drawable?= null
            try {
                val inputStream: InputStream = URL(url).content as InputStream
                draw =  Drawable.createFromStream(inputStream, "src")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return draw
        }

        fun loadUrlPicture(url: String?): Bitmap?{
            var bitmap : Bitmap?=null
            try {
//                val i: ImageView = findViewById(R.id.image) as ImageView
                bitmap = BitmapFactory.decodeStream(URL(url).content as InputStream)
//                i.setImageBitmap(bitmap)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bitmap
        }

        fun getBitmapFromUrl(url : String) : Bitmap?{
            var bitmap : Bitmap?=null
            try
            {
                val url = URL(url)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.readTimeout = 60000
                conn.connectTimeout = 65000
                conn.requestMethod = "GET"
                conn.doInput = true
                conn.connect()
                val response: Int = conn.responseCode
                Log.e(TAG, "The response is: $response" );
                val inputStream = conn.inputStream
                val bufferedInputStream = BufferedInputStream(inputStream)
                bitmap = BitmapFactory.decodeStream(bufferedInputStream)
            }  catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        fun fixTooLongTitle(str: String?): String?{
            if(str == null){
                return str
            }

            var isTooLong = str.length > 15
            if( isTooLong &&  str.contains(':')){
                println("isTooLong $str")
                println("isTooLong $isTooLong")
                println("&& str.contains(':')  ${ str.contains(':')}")
                var list  = str.split(":")
                return "${list[0]}\n${list[1]}"
            }
            return str
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

}