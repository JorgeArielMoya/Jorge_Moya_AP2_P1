package edu.ucne.jorge_moya_ap2_p1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amonestaciones")
data class AmonestacionEntity(
    @PrimaryKey(autoGenerate = true)
    val amonestacionId :Int,
    val nombres : String,
    val razon : String,
    val monto : Double
)