package edu.ucne.jorge_moya_ap2_p1.domain.usecase

import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion
import edu.ucne.jorge_moya_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Inject

class UpsertAmonestacionUseCase @Inject constructor(
    private val repository : AmonestacionRepository
) {
    suspend operator fun invoke(amonestacion: Amonestacion) : Result<Int>{
        val nombre = validateNombre(amonestacion.nombres)
        if (!nombre.isValid){
            return Result.failure(IllegalArgumentException(nombre.error))
        }

        val razon = validateRazon(amonestacion.razon)
        if (!razon.isValid){
            return Result.failure(IllegalArgumentException(razon.error))
        }

        val monto = validateMonto(amonestacion.monto.toString())
        if (!monto.isValid){
            return Result.failure(IllegalArgumentException(monto.error))
        }

        return runCatching { repository.upsert(amonestacion) }
    }
}