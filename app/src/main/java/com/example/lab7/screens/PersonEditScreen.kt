package com.example.lab7.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lab7.model.PersonEntity
import com.example.lab7.viewmodel.PersonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonEditScreen(
    personId: Int,
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }

    // Загрузка данных при редактировании существующей персоны
    LaunchedEffect(personId) {
        if (personId > 0) {
            viewModel.getPerson(personId)?.let { person ->
                name = person.name
                age = person.age.toString()
                profession = person.profession
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (personId == 0) "New Person" else "Edit Person") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val person = PersonEntity(
                                id = if (personId > 0) personId else 0,
                                name = name,
                                age = age.toIntOrNull() ?: 0,
                                profession = profession
                            )
                            if (personId == 0) {
                                viewModel.insertPerson(person)
                            } else {
                                viewModel.updatePerson(person)
                            }
                            navController.popBackStack()
                        },
                        enabled = name.isNotBlank() && age.toIntOrNull() != null
                    ) {
                        Icon(Icons.Default.Check, "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { age = it.takeWhile { char -> char.isDigit() } },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = profession,
                onValueChange = { profession = it },
                label = { Text("Profession") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}