package com.example.weatherinfo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToWeather: (String) -> Unit) {
    var cityList by remember { mutableStateOf(listOf("Budapest")) } // Persistent list of cities
    var selectedCity by remember { mutableStateOf(cityList.first()) }
    var showDialog by remember { mutableStateOf(false) }
    var newCity by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Dropdown expanded state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("City Selector & List") },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add City")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedCity,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select City") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    cityList.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(text = city) },
                            onClick = {
                                selectedCity = city
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onNavigateToWeather(selectedCity) }) {
                Text("Show Weather")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(cityList) { city ->
                    CityRow(
                        city = city,
                        onDelete = { cityList = cityList.filter { it != city } },
                        onNavigateToWeather = onNavigateToWeather
                    )
                }
            }
        }

        if (showDialog) {
            AddCityDialog(
                newCity = newCity,
                onCityChange = { newCity = it },
                onConfirm = {
                    if (newCity.isNotBlank() && !cityList.contains(newCity)) {
                        cityList = cityList + newCity
                        newCity = ""
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun CityRow(city: String, onDelete: () -> Unit, onNavigateToWeather: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToWeather(city) }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        IconButton(onClick = { onDelete() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete City",
                tint = MaterialTheme.colorScheme.error
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Navigate to Weather",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AddCityDialog(
    newCity: String,
    onCityChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add City") },
        text = {
            Column {
                OutlinedTextField(
                    value = newCity,
                    onValueChange = onCityChange,
                    label = { Text("City Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onConfirm() }),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Enter the name of a new city to add it to the list.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        confirmButton = {
            Button(onClick = onConfirm, enabled = newCity.isNotBlank()) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
