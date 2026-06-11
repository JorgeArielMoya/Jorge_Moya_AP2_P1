package edu.ucne.jorge_moya_ap2_p1.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion

@Composable
fun AmonestacionListScreen(
    viewModel: AmonestacionListViewModel = hiltViewModel(),
    onNew: () -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.onNavigateToCreate) {
        if (state.onNavigateToCreate) {
            onNew()
        }
    }

    LaunchedEffect(state.onNavigateToEdit) {
        state.onNavigateToEdit?.let { id ->
            onNavigateToEdit(id)
        }
    }

    AmonestacionListBody(state, viewModel::onEvent, onNew)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmonestacionListBody(
    state: AmonestacionListUiState,
    onEvent: (AmonestacionListUiEvent) -> Unit,
    onAddTask: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val totalMontos = remember(state.amonestaciones) {
        state.amonestaciones.sumOf { it.monto }
    }
    val totalAmonestaciones = state.amonestaciones.size

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onEvent(AmonestacionListUiEvent.ClearMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTask,
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar amonestacion"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            } else {
                if (state.amonestaciones.isEmpty()) {
                    Text(
                        text = "No hay amonestaciones",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag("empty_message"),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = state.searchNombre,
                                onValueChange = { onEvent(AmonestacionListUiEvent.SearchNombreChange(it)) },
                                label = { Text("Buscar nombre") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = state.searchRazon,
                                onValueChange = { onEvent(AmonestacionListUiEvent.SearchRazonChange(it)) },
                                label = { Text("Buscar Razon") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = state.amonestacionesFiltradas,
                                key = { it.amonestacionId }
                            ) { amonestacion ->
                                AmonestacionItem(
                                    amonestacion = amonestacion,
                                    onClick = { onEvent(AmonestacionListUiEvent.Edit(amonestacion.amonestacionId)) }
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Cantidad",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "$totalAmonestaciones",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "Total Monto",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "$${String.format("%.2f", totalMontos)}",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmonestacionItem(
    amonestacion: Amonestacion,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("amonestacion_item_${amonestacion.amonestacionId}"),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Id: ${amonestacion.amonestacionId}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Nombres: ${amonestacion.nombres}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Razon: ${amonestacion.razon}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Amonestacion: ${amonestacion.monto}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}