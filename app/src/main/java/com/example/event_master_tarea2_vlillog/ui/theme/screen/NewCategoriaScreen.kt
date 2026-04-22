package com.example.event_master_tarea2_vlillog.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.event_master_tarea2_vlillog.R
import com.example.event_master_tarea2_vlillog.ui.theme.viewmodel.EventViewModel

@Composable
fun NewCategoriaScreen(navController: NavHostController, viewModel: EventViewModel) {

    var categoryName by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.Nueva_categoria),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(
            value = categoryName,
            onValueChange = { 
                categoryName = it
                if (it.isNotEmpty()) showError = false
            },
            label = { Text(stringResource(id = R.string.Nombre_categoria)) },
            isError = showError,
            supportingText = {
                if (showError) {
                    Text(
                        text = stringResource(id = R.string.Error_vacio),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {
                if(categoryName.trim().isNotEmpty()) {
                    viewModel.addCategory(categoryName.trim())
                    navController.popBackStack()
                } else {
                    showError = true
                }
            }
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Guardar")
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(id = R.string.Guardar))
        }
    }
}
