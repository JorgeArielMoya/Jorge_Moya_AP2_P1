package edu.ucne.jorge_moya_ap2_p1.data.repository

import edu.ucne.jorge_moya_ap2_p1.data.local.dao.AmonestacionDao
import edu.ucne.jorge_moya_ap2_p1.data.mapper.toDomain
import edu.ucne.jorge_moya_ap2_p1.data.mapper.toEntity
import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion
import edu.ucne.jorge_moya_ap2_p1.domain.repository.AmonestacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AmonestacionRepositoryImpl @Inject constructor(
    private val localDataSource : AmonestacionDao
) : AmonestacionRepository {
    override suspend fun upsert(amonestacion: Amonestacion) : Int {
        localDataSource.upsert(amonestacion.toEntity())
        return amonestacion.amonestacionId
    }

    override fun observeAll(): Flow<List<Amonestacion>> {
        return localDataSource.observeAll().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getById(id: Int): Amonestacion? {
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun deleteById(id: Int) {
        localDataSource.deleteById(id)
    }
}