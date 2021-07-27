package com.mvvm.interfaces

import androidx.room.*
import com.mvvm.model.Film


/**
 * https://medium.com/androiddevelopers/room-rxjava-acb0cd4f3757
 *
 * Room offre la possibilità di osservare i dati nel database ed eseguire query asincrone con
 * l'aiuto di oggetti RxJava Maybe , Single e Flowable .
 *
 * */
@Dao
interface FilmDaoCRUD {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(vararg film: Film)

    @Update
    suspend fun updateFilm(vararg film: Film)

    @Delete
    suspend fun deleteFilms(vararg film: Film)

    @Query("SELECT * FROM film")
    suspend fun getAllFilms(): List<Film>?

    @Query("SELECT * FROM Film WHERE id = :idFilm")
    suspend fun getFilmById(idFilm: String): Film?


//     @Query("SELECT * FROM film")
//     fun getAllFilmsFlowable(): Flowable<List<Film>>

//    @Query("SELECT * FROM Film WHERE id = :idFilm")
//    fun getFilmById(idFilm: String): Flowable<Film>

}