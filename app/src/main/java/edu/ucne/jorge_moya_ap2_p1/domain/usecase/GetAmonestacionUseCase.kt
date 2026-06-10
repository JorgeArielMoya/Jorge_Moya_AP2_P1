package edu.ucne.jorge_moya_ap2_p1.domain.usecase

import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion
import edu.ucne.jorge_moya_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Inject

class GetAmonestacionUseCase @Inject constructor(
    private val repository : AmonestacionRepository
) {
    suspend operator fun invoke(id : Int) : Amonestacion? = repository.getById(id)
}