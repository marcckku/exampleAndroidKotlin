package com.mvvm.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.mvvm.model.Film
import com.mvvm.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 *  *
 * https://localazy.com/blog/floating-windows-on-android-8-the-final-app?utm_campaign=tech_android&utm_medium=social&utm_source=vh-reddit-Kotlin&utm_content=c-109&utm_variant=v-293&utm_ref=u-1&utm_distribution=contelazy
 *  C'è anche enableMultiInstanceInvalidation() per Room abilitato.
 *  Risolve il problema con le modifiche simultanee allo stesso database.
 *
 *  val db = Room.databaseBuilder(
 *  if (context.applicationContext == null) context else context.applicationContext,
 *  AppDatabase::class.java,
 *  "db-notes"
 *  ).enableMultiInstanceInvalidation().build()
 *
 * */

class DbManager(private val context: Context) {

    private val TAG: String = DbManager::class.java.simpleName

    var db: AppDatabase? = null

    /**
     * Se non desideri fornire migrazioni e desideri specificamente che il tuo database
     * venga cancellato quando aggiorni la versione, chiama fallbackToDestructiveMigration
     * nel generatore di database.
     * Legato alla versione @Database(entities = [ Film::class, User::class,], version = 2)
     *
     * */
    init {
        db = Room.databaseBuilder(
            if (context.applicationContext == null) context else context.applicationContext,
            AppDatabase::class.java,
            "${AppDatabase.DB_NAME}"
        ).fallbackToDestructiveMigration().enableMultiInstanceInvalidation().build()
        Log.e(TAG, "init db")
    }

    suspend fun insertFilm(film: Film) {
        if (film == null) {
            throw Exception("insertFilm - FILM NULL")
        }
        db!!.filmDaoCRUD().insertFilm(film)
    }

    suspend fun deleteFilms() {
        db!!.filmDaoCRUD().deleteFilms()
        Log.e(TAG, "deleteFilms - success!!")
    }

    //////////////////////////////////////////////////
    suspend fun getAllFilms(): List<Film>? {
        return db!!.filmDaoCRUD().getAllFilms()
    }

    suspend fun insertUser(user: User) {
        if (user == null) {
            throw Exception("insertUser - USER NULL")
        }
        db!!.userDaoCRUD().insertUser(user)
    }

    suspend fun getAllUsers(): List<User>? {
        return db!!.userDaoCRUD().getAllUsers()
    }

    suspend fun updateUser(user: User): User {
        if (user == null) {
            throw Exception("updateUser - USER NULL")
        }
        val r = db!!.userDaoCRUD().updateSingleUser(user)
        Log.e(TAG, "updateUser  ==== ${r}")
        return db!!.userDaoCRUD().getUserById(user.id)
    }


//    suspend fun updateUserFlow(user: User): Flow<User> {
//        if (user == null) {
//            throw Exception("updateUser - USER NULL")
//        }
//        val r = db!!.userDaoCRUD()!!.updateSingleUser(user)
//        Log.e(TAG, "updateUser  ==== ${r}")
//        return  db!!.userDaoCRUD()!!.loadUserById(user.id)
//    }

    suspend fun deleteUsers() {
        db!!.userDaoCRUD().deleteUsers()
        Log.e(TAG, "deleteUsers - success!!")
    }

    suspend fun getUserById(id: String): User? {
        if (id.isNullOrEmpty()) {
            throw Exception("id - isNullOrEmpty")
        }
        Log.e(TAG, "getUserById - success!!")
        return db!!.userDaoCRUD().getUserById(id)
    }


    fun deleteAllRowsAllTables() {
        val dispatchers = Dispatchers.IO
        CoroutineScope(dispatchers + SupervisorJob()).launch {
            db!!.clearAllTables()
            Log.e(TAG, "deleteAllRowsAllTables - success!!")
        }
    }

}