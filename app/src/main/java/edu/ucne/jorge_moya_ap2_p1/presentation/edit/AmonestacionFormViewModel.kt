package edu.ucne.jorge_moya_ap2_p1.presentation.edit

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.DeleteAmonestacionUseCase
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.GetAmonestacionUseCase
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.UpsertAmonestacionUseCase
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.validateMonto
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.validateNombre
import edu.ucne.jorge_moya_ap2_p1.domain.usecase.validateRazon
import edu.ucne.jorge_moya_ap2_p1.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AmonestacionFormViewModel @Inject constructor (
    private val getAmonestacionUseCase: GetAmonestacionUseCase,
    private val upsertAmonestacionUseCase: UpsertAmonestacionUseCase,
    private val deleteAmonestacionUseCase: DeleteAmonestacionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val routeArgs = savedStateHandle.toRoute<Screen.FormScreen>()
    private val amonestacionId = routeArgs.id
    private val _state = MutableStateFlow(AmonestacionFormUiState())
    val state: StateFlow<AmonestacionFormUiState> = _state.asStateFlow()

    init {
        loadAmonestacion(amonestacionId)
    }

    fun onEvent(event: AmonestacionFormUiEvent){
        return when(event) {
            AmonestacionFormUiEvent.Delete -> onDelete()
            AmonestacionFormUiEvent.Load -> loadAmonestacion(amonestacionId)
            is AmonestacionFormUiEvent.NombreChanged -> _state.update { it.copy(nombre = event.value, nombreError = null) }
            AmonestacionFormUiEvent.Save -> onSave()
            is AmonestacionFormUiEvent.RazonChanged -> _state.update { it.copy(razon = event.value, montoError = null) }
            is AmonestacionFormUiEvent.MontoChanged -> _state.update { it.copy(monto = event.value, montoError = null) }
        }
    }

    fun loadAmonestacion (id : Int){
        if (id == null || id == 0){
            _state.update { it.copy(isNew = true, amonestacionId = null ) }
        }

        viewModelScope.launch {
            val amonestacion = getAmonestacionUseCase(id)
            if (amonestacion != null){
                _state.update { it.copy(
                    isNew = false,
                    amonestacionId = amonestacion.amonestacionId,
                    nombre = amonestacion.nombres,
                    razon = amonestacion.razon,
                    monto = amonestacion.monto.toString()
                ) }
            }else {
                _state.update { it.copy(isNew = true, amonestacionId = null) }
            }
        }
    }

    fun onSave (){
        val nombre = validateNombre(state.value.nombre)
        val razon = validateRazon(state.value.razon)
        val monto = validateMonto(state.value.monto)

        if (!nombre.isValid || !monto.isValid || !razon.isValid){
            _state.update {
                it.copy(
                    razonError = razon.error,
                    nombreError = nombre.error,
                    montoError = monto.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val amonestacion = Amonestacion(
                amonestacionId = state.value.amonestacionId?: 0,
                nombres = state.value.nombre,
                razon = state.value.razon,
                monto = state.value.monto.toDouble(),
            )

            val result = upsertAmonestacionUseCase(amonestacion)

            result.onSuccess { id ->
                _state.update {
                    it.copy(
                        amonestacionId = id,
                        isNew = false,
                        isSaving = false,
                        saved = true
                    ) }
            }.onFailure { _state.update {
                it.copy(isSaving = false)
            } }
        }
    }

    fun onDelete(){
        val id = state.value.amonestacionId?: 0
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteAmonestacionUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}