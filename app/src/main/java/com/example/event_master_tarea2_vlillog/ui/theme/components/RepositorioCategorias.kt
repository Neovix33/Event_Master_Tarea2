package com.example.event_master_tarea2_vlillog.ui.theme.components

import androidx.compose.runtime.mutableStateListOf

object CategoryRepository {

    val categories = mutableStateListOf(
        Category("Música"),
        Category("Deportes"),
        Category("Tecnología"),
        Category("Arte")
    )

}