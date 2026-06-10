package edu.ucne.jorge_moya_ap2_p1.domain.usecase

import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion
import edu.ucne.jorge_moya_ap2_p1.domain.repository.AmonestacionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAmonestacionUseCase @Inject constructor(
    private val repository : AmonestacionRepository
) {
    operator fun invoke() : Flow<List<Amonestacion>> = repository.observeAll()
}