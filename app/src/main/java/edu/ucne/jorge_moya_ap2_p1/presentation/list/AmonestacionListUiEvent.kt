package edu.ucne.jorge_moya_ap2_p1.presentation.list


sealed class AmonestacionListUiEvent {
    object Load : AmonestacionListUiEvent()
    object Refresh : AmonestacionListUiEvent()
    object ClearMessage : AmonestacionListUiEvent()
    object CreateNew : AmonestacionListUiEvent()
    data class Edit(val id: Int) : AmonestacionListUiEvent()
    data class ShowMessage(val message: String) : AmonestacionListUiEvent()
    data class SearchNombreChange(val value: String) : AmonestacionListUiEvent()
}