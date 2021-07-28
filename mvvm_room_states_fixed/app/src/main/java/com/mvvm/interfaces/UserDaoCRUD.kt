package com.mvvm.interfaces
//
import androidx.room.*
import com.mvvm.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the users table.
 */
@Dao
interface UserDaoCRUD {

    //////////////////////////////////////////////
    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg users: User)

    @Update
    suspend fun updateUsers(vararg users: User)

    // @Query("UPDATE user SET email = :email, pwd = :pwd, description= :description, role = :role WHERE id = :id")
    // suspend fun _updateSingleUser(id: String, email: String, pwd : String, description : String, role : String ): Int

    @Update(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSingleUser(user: User): Int

    @Delete
    suspend fun deleteUsers(vararg users: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user")
    fun getAllUsersFlow(): Flow<Array<User>>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): User

//    @Query("SELECT * from user where id= :id LIMIT 1")
//    suspend fun loadUserById(id: String): Flow<User>
//

//    /**
//     * Get a user by id.
//     * @return the user from the table with a specific id.
//     */
//    @Query("SELECT * FROM User WHERE id = :id")
//    fun getUserById(id: String): Flowable<User>
//
//    /**
//     * Delete all users.
//     */
//    @Query("DELETE FROM User")
//    fun deleteAllUsers():Completable

    //////////////////////////////////////////////


}