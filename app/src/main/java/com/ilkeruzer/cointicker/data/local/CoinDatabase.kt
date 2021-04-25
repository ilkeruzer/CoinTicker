package com.ilkeruzer.cointicker.data.local

import android.content.Context
import android.os.Parcelable
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Database(
    entities = [CoinDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class CoinDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context): CoinDatabase {
            return Room.databaseBuilder(
                context,
                CoinDatabase::class.java,
                "cryptocurrency.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun coinDao(): CoinDao

}

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stations: List<CoinDbModel>)

    @Query("DELETE FROM coins")
    suspend fun clearStation()

    @Query("SELECT * FROM coins")
    fun getAllCoins(): PagingSource<Int, CoinDbModel>

    @Query("SELECT * FROM coins WHERE name LIKE :param OR symbol LIKE :param")
    fun getCoinsBySearch(param: String): PagingSource<Int, CoinDbModel>


}

@Entity(tableName = "coins")
@Parcelize
data class CoinDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val code: String = "",
    val symbol: String = "",
    val name: String = ""
) : Parcelable