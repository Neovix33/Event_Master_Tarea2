package com.example.event_master_tarea2_vlillog.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.event_master_tarea2_vlillog.ui.theme.components.Category
import com.example.event_master_tarea2_vlillog.ui.theme.components.CategoryRepository
import com.example.event_master_tarea2_vlillog.ui.theme.components.Event

class EventViewModel : ViewModel() {

    val categories = CategoryRepository.categories

    init {
        if (categories.isNotEmpty()) {
            val musica = categories.find { it.name == "Música" }
            if (musica?.events?.isEmpty() == true) {
                musica.events.addAll(listOf(
                    Event("Lollapalooza", "Festival de música internacional", "15-03-2024"),
                    Event("Concierto Rock", "Bandas locales en vivo", "20-05-2024")
                ))
            }

            val deportes = categories.find { it.name == "Deportes" }
            if (deportes?.events?.isEmpty() == true) {
                deportes.events.addAll(listOf(
                    Event("Maratón Santiago", "Carrera 42K por la ciudad", "07-04-2024"),
                    Event("Final Fútbol", "Torneo nacional de apertura", "12-06-2024")
                ))
            }
        }
    }

    fun addCategory(name: String) {
        if (name.isNotEmpty()) {
            categories.add(Category(name))
        }
    }

    fun getCategory(name: String): Category? {
        return categories.find { it.name == name }
    }

    fun addEventToCategory(categoryName: String, title: String, description: String, date: String) {
        val category = getCategory(categoryName)
        if (title.isNotEmpty()) {
            category?.events?.add(Event(title, description, date))
        }
    }
}
