package com.example.lab7.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.components.CenterProgress
import com.example.lab7.model.PersonEntity
import com.example.lab7.viewmodel.PersonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
    personId: Int,
    navController: NavController,
    viewModel: PersonViewModel
) {
    var person by remember { mutableStateOf<PersonEntity?>(null) }
    val isLoading by viewModel.isDetailLoading.collectAsState()
    val error by viewModel.detailError.collectAsState()

    LaunchedEffect(personId) {
        person = viewModel.getPerson(personId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(person?.name ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    person?.let {
                        IconButton(onClick = { navController.navigate("todos/${it.id}") }) {
                            Text("TODO")
                        }
                        IconButton(onClick = { navController.navigate("edit/${it.id}") }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    CenterProgress()
                }

                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ошибка: $error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                person != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Name: ${person!!.name}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Age: ${person!!.age}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Profession: ${person!!.profession}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}