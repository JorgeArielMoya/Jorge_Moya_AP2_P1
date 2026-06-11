package edu.ucne.jorge_moya_ap2_p1.presentation.edit

data class AmonestacionFormUiState (
    val amonestacionId : Int? = null,
    val nombre : String= "",
    val razon : String= "",
    val monto : String = "",
    val nombreError : String? = null,
    val razonError : String? = null,
    val montoError : String? = null,
    val isSaving : Boolean =false,
    val isDeleting : Boolean = false,
    val saved : Boolean = false,
    val deleted : Boolean = false,
    val isNew : Boolean = true,
)

