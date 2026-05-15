package com.example.lab7.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.model.WishEntity
import com.example.lab7.viewmodel.WishViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishEditScreen(
    wishId: Int,
    navController: NavController,
    viewModel: WishViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var isPurchased by remember { mutableStateOf(false) }

    LaunchedEffect(wishId) {
        if (wishId > 0) {
            val wish = viewModel.allWishes.value.find { it.id == wishId }
            wish?.let {
                title = it.title
                description = it.description
                price = it.price.toString()
                url = it.url
                priority = it.priority
                isPurchased = it.isPurchased
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (wishId == 0) "Новое желание" else "Редактирование желания") },
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
                            val wish = WishEntity(
                                id = if (wishId > 0) wishId else 0,
                                title = title,
                                description = description,
                                price = price.toDoubleOrNull() ?: 0.0,
                                url = url,
                                priority = priority,
                                isPurchased = isPurchased
                            )

                            if (wishId == 0) {
                                viewModel.addWish(wish)
                            } else {
                                viewModel.updateWish(wish)
                            }

                            navController.popBackStack()
                        },
                        enabled = title.isNotBlank()
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
                value = title,
                onValueChange = { title = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it.replace(',', '.') },
                label = { Text("Цена") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Ссылка") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = priority,
                onValueChange = { priority = it },
                label = { Text("Приоритет") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isPurchased,
                    onCheckedChange = { isPurchased = it }
                )
                Text("Уже куплено")
            }
        }
    }
}