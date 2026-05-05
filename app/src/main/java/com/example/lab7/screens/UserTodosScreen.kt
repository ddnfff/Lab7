package com.example.lab7.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab7.components.CenterProgress
import com.example.lab7.model.TodoDto
import com.example.lab7.viewmodel.PersonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTodosScreen(
    userId: Int,
    navController: NavController,
    viewModel: PersonViewModel
) {
    val todos by viewModel.userTodos.collectAsState()
    val isLoading by viewModel.isTodosLoading.collectAsState()
    val error by viewModel.todosError.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadTodosByUserId(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Todos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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

                todos.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("У пользователя нет задач")
                    }
                }

                else -> {
                    LazyColumn {
                        items(todos) { todo ->
                            TodoItem(todo = todo)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(todo: TodoDto) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (todo.completed) "Статус: выполнено" else "Статус: не выполнено",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}