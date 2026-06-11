package edu.ucne.jorge_moya_ap2_p1.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmonestacionFormScreen(
    viewModel: AmonestacionFormViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) onBack()
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nueva amonestacion" else "Editar amonestacion") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { viewModel.onEvent(AmonestacionFormUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.nombreError != null,
                supportingText = state.nombreError?.let { { Text(it) } },
                singleLine = true
            )

            OutlinedTextField(
                value = state.razon,
                onValueChange = { viewModel.onEvent(AmonestacionFormUiEvent.RazonChanged(it)) },
                label = { Text("Razon") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.razonError != null,
                supportingText = state.razonError?.let { { Text(it) } },
                singleLine = true
            )

            OutlinedTextField(
                value = state.monto,
                onValueChange = { viewModel.onEvent(AmonestacionFormUiEvent.MontoChanged(it)) },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.montoError != null,
                supportingText = state.montoError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.onEvent(AmonestacionFormUiEvent.Delete) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isNew,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }

                Button(
                    onClick = { viewModel.onEvent(AmonestacionFormUiEvent.Save) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSaving
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}