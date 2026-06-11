package edu.ucne.jorge_moya_ap2_p1.presentation.list

import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion

data class AmonestacionListUiState (
    val isLoading : Boolean = false,
    val searchNombre: String = "",
    val searchRazon : String = "",
    val amonestaciones : List<Amonestacion> = emptyList(),
    val amonestacionesFiltradas: List<Amonestacion> = emptyList(),
    val message : String? = null,
    val error : String? = null,
    val onNavigateToCreate : Boolean = false,
    val onNavigateToEdit : Int? = null
)