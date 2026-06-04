package edu.ucne.jorge_moya_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ListScreen : Screen()
    @Serializable
    data class FormScreen(val id : Int)
}