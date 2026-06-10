package edu.ucne.jorge_moya_ap2_p1.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.jorge_moya_ap2_p1.data.local.dao.AmonestacionDao
import edu.ucne.jorge_moya_ap2_p1.data.local.entity.AmonestacionEntity

@Database(
    entities = [AmonestacionEntity::class],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase() {
    abstract fun amonestacionDao () : AmonestacionDao
}