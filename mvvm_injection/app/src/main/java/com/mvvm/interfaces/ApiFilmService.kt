package com.mvvm.interfaces

import com.mvvm.model.ResultActorList
import com.mvvm.model.ResultDetailsFilm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

open interface ApiFilmService {


    @GET("3/movie/popular?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US&page=1")
    fun getFilmsAsyncV1(): Call<com.mvvm.model.ResultListFilm>

    @GET("3/movie/popular?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US&page=1")
    suspend fun getFilmsSyncV2(): com.mvvm.model.ResultListFilm


    @GET("3/movie/{movie_id}?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US")
    suspend fun getDettaglioFilm(@Path("movie_id") movie_id: String): ResultDetailsFilm


    @GET("3/movie/{movie_id}/credits?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US")
    suspend fun getActorsList(@Path("movie_id") movie_id: String): ResultActorList
}