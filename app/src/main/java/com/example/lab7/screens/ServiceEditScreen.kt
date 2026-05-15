package com.example.lab7.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.model.ServiceRecordEntity
import com.example.lab7.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceEditScreen(
    recordId: Int,
    navController: NavController,
    viewModel: ServiceViewModel
) {
    var serviceDate by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(recordId) {
        if (recordId > 0) {
            val record = viewModel.allRecords.value.find { it.id == recordId }
            record?.let {
                serviceDate = it.serviceDate
                mileage = it.mileage.toString()
                serviceType = it.serviceType
                cost = it.cost.toString()
                location = it.location
                notes = it.notes
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (recordId == 0) "Новая запись" else "Редактирование записи") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val record = ServiceRecordEntity(
                                id = if (recordId > 0) recordId else 0,
                                serviceDate = serviceDate,
                                mileage = mileage.toIntOrNull() ?: 0,
                                serviceType = serviceType,
                                cost = cost.toDoubleOrNull() ?: 0.0,
                                location = location,
                                notes = notes
                            )

                            if (recordId == 0) {
                                viewModel.addRecord(record)
                            } else {
                                viewModel.updateRecord(record)
                            }

                            navController.popBackStack()
                        },
                        enabled = serviceDate.isNotBlank() &&
                                mileage.toIntOrNull() != null &&
                                serviceType.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Сохранить")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = serviceDate,
                onValueChange = { serviceDate = it },
                label = { Text("Дата обслуживания") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = mileage,
                onValueChange = { mileage = it.filter(Char::isDigit) },
                label = { Text("Пробег") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = serviceType,
                onValueChange = { serviceType = it },
                label = { Text("Тип работ") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it.replace(',', '.') },
                label = { Text("Стоимость") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Сервис / место") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Заметки") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
        }
    }
}