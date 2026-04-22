package com.example.event_master_tarea2_vlillog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.event_master_tarea2_vlillog.ui.theme.theme.Event_Master_Tarea2_VLilloGTheme
import com.example.event_master_tarea2_vlillog.ui.theme.navigation.Navigation
import com.example.event_master_tarea2_vlillog.ui.theme.navigation.NewCategoria

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Event_Master_Tarea2_VLilloGTheme {
                Navigation()
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

    ) {

        Button(onClick = {navController.navigate(NewCategoria)},

            ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar_categoria",modifier = Modifier.size(50.dp))
        }
    }
}

