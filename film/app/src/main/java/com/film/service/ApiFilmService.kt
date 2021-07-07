package com.film.service

import com.film.model.ResultActorList
import com.film.model.ResultDetailsFilm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

open interface ApiFilmService {


    @GET("3/movie/popular?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US&page=1")
    fun getFilms() : Call<com.film.model.ResultListFilm>


    @GET("3/movie/{movie_id}?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US")
    fun getDettaglioFilm(@Path("movie_id") movie_id : String) : Call<ResultDetailsFilm>


    @GET("/3/movie/{movie_id}/credits?api_key=0bfaf3c0640773566cb7692affcae335&language=en-US")
    fun getActorsList(@Path("movie_id") movie_id : String) : Call<ResultActorList>
}