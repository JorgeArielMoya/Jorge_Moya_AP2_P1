package edu.ucne.jorge_moya_ap2_p1.domain.repository

import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion
import kotlinx.coroutines.flow.Flow

interface AmonestacionRepository {
    suspend fun upsert (amonestacion: Amonestacion) : Int

    fun observeAll () : Flow<List<Amonestacion>>

    suspend fun getById (id : Int) : Amonestacion?

    suspend fun deleteById (id : Int)
}