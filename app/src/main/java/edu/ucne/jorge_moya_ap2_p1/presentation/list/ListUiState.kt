package edu.ucne.jorge_moya_ap2_p1.presentation.list

import edu.ucne.jorge_moya_ap2_p1.data.local.BorrameEntity

data class ListUiState(
    val isLoading: Boolean = false,
    val borrame: List<BorrameEntity> = emptyList()
)