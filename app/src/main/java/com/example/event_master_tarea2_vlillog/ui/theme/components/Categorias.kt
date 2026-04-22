package com.example.event_master_tarea2_vlillog.ui.theme.components

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf

data class Event(
    val title: String,
    val description: String,
    val date: String
)

data class Category(
    val name: String,
    val events: SnapshotStateList<Event> = mutableStateListOf()
)
