package edu.ucne.jorge_moya_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jorge_moya_ap2_p1.data.local.entity.AmonestacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AmonestacionDao {
    @Upsert
    suspend fun upsert (entity: AmonestacionEntity)

    @Delete
    suspend fun delete (entity : AmonestacionEntity)

    @Query("SELECT * FROM AMONESTACIONES ORDER BY amonestacionId DESC")
    fun observeAll () : Flow<List<AmonestacionEntity>>

    @Query("SELECT * FROM AMONESTACIONES WHERE amonestacionId = :id")
    suspend fun getById (id : Int) : AmonestacionEntity?

    @Query("DELETE FROM AMONESTACIONES WHERE amonestacionId = :id")
    suspend fun deleteById (id : Int)
}