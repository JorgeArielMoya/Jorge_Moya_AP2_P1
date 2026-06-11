package edu.ucne.jorge_moya_ap2_p1.presentation.edit


sealed interface AmonestacionFormUiEvent {
    object Load : AmonestacionFormUiEvent
    object Save : AmonestacionFormUiEvent
    object Delete : AmonestacionFormUiEvent
    data class NombreChanged (val value : String) : AmonestacionFormUiEvent
    data class RazonChanged (val value : String) : AmonestacionFormUiEvent
    data class MontoChanged (val value : String) : AmonestacionFormUiEvent
}