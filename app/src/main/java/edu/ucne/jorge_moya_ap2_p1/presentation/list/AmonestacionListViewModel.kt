package edu.ucne.jorge_moya_ap2_p1.presentation.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.ObserveAmonestacionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AmonestacionListViewModel @Inject constructor(
    private val observeAmonestacionUseCase: ObserveAmonestacionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AmonestacionListUiState(isLoading = true))
    val state: StateFlow<AmonestacionListUiState> = _state.asStateFlow()

    init {
        loadEmpleado()
    }

    fun onEvent(event: AmonestacionListUiEvent) {
        return when (event) {
            AmonestacionListUiEvent.ClearMessage -> _state.update { it.copy(message = null) }
            AmonestacionListUiEvent.CreateNew -> _state.update { it.copy(onNavigateToCreate = true) }
            is AmonestacionListUiEvent.Edit -> _state.update { it.copy(onNavigateToEdit = event.id) }
            AmonestacionListUiEvent.Load -> loadEmpleado()
            AmonestacionListUiEvent.Refresh -> loadEmpleado()
            is AmonestacionListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
            is AmonestacionListUiEvent.SearchNombreChange -> {
                _state.update { it.copy(searchNombre = event.value) }
                filtrar()
            }
        }
    }

    private fun filtrar() {
        val nombre = _state.value.searchNombre.trim().lowercase()
        val filtrados = _state.value.amonestaciones.filter { amo ->
            (nombre.isEmpty() || amo.nombres.contains(nombre, true))

        }
        _state.update { it.copy(amonestacionesFiltradas = filtrados) }
    }

    fun loadEmpleado (){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeAmonestacionUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, amonestaciones = list) }
                filtrar()
            }
        }
    }
}