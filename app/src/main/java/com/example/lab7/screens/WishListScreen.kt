package com.example.lab7.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.components.CenterProgress
import com.example.lab7.model.WishEntity
import com.example.lab7.viewmodel.WishViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListScreen(
    navController: NavController,
    viewModel: WishViewModel
) {
    val wishes by viewModel.allWishes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Вишлист") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("edit/0") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить желание")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading && wishes.isEmpty() -> {
                    CenterProgress()
                }

                error != null && wishes.isEmpty() -> {
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

                wishes.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Список желаний пока пуст")
                    }
                }

                else -> {
                    LazyColumn {
                        items(
                            items = wishes,
                            key = { it.id }
                        ) { wish ->
                            WishListItem(
                                wish = wish,
                                onClick = { navController.navigate("detail/${wish.id}") },
                                onDelete = { viewModel.deleteWish(wish) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WishListItem(
    wish: WishEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = wish.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Цена: ${wish.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Приоритет: ${wish.priority}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (wish.isPurchased) "Статус: куплено" else "Статус: не куплено",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}