package com.mvvm.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvvm.converter.Converters
import com.mvvm.interfaces.FilmDaoCRUD
import com.mvvm.interfaces.UserDaoCRUD
import com.mvvm.model.Film
import com.mvvm.model.User


/***
 *
 * Dopo aver creato i file AppDatabase, otterrai un'istanza del database creato con il seguente
 * codice:
 *   val db = Room.databaseBuilder(
 *       applicationContext,
 *       AppDatabase::class.java, "database-name"
 *   ).build()
 *
 */


@Database(entities = [Film::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val DB_NAME = AppDatabase.javaClass.simpleName

    }

    abstract fun filmDaoCRUD(): FilmDaoCRUD
    abstract fun userDaoCRUD(): UserDaoCRUD
}