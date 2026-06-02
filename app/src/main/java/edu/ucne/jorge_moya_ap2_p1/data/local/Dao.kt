package edu.ucne.jorge_moya_ap2_p1.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface Dao {
    @Upsert
    suspend fun upsert (entity: BorrameEntity)
}