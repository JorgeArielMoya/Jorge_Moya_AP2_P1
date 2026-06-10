package edu.ucne.jorge_moya_ap2_p1.domain.usecase

import edu.ucne.jorge_moya_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Inject

class DeleteAmonestacionUseCase @Inject constructor(
    private val repository : AmonestacionRepository
) {
    suspend operator fun invoke(id : Int) = repository.deleteById(id)
}