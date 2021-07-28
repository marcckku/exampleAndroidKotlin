package com.mvvm.serviceImpl

import com.mvvm.interfaces.ApiFilmService


class ApiFilmServiceImpl (val service : ApiFilmService) {

    private val TAG: String = ApiFilmServiceImpl::class.java.simpleName

    suspend fun loadFilmsSyncV2() = service.getFilmsSyncV2()

    suspend fun getDettaglioFilm(movie_id: String)= service.getDettaglioFilm(movie_id)

    suspend fun getActorsList(movie_id: String)= service.getActorsList(movie_id)
}