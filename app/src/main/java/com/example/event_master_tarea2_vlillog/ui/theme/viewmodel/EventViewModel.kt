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
                    Event("Lollapalooza", "Festival de música internacional con múltiples escenarios.", "15-03-2024"),
                    Event("Concierto Rock", "Bandas locales de rock alternativo en el centro de la ciudad.", "20-05-2024")
                ))
            }

            val deportes = categories.find { it.name == "Deportes" }
            if (deportes?.events?.isEmpty() == true) {
                deportes.events.addAll(listOf(
                    Event("Maratón Santiago", "La carrera más grande de Chile, recorriendo las principales calles.", "07-04-2024"),
                    Event("Final Fútbol", "Gran final del campeonato nacional entre los mejores equipos.", "12-06-2024")
                ))
            }

            val tecnologia = categories.find { it.name == "Tecnología" }
            if (tecnologia?.events?.isEmpty() == true) {
                tecnologia.events.addAll(listOf(
                    Event("Cumbre Tech", "Conferencia sobre las últimas tendencias en IA y desarrollo móvil.", "10-08-2024"),
                    Event("Hackathon 2024", "Competencia de programación de 48 horas para resolver desafíos sociales.", "05-11-2024")
                ))
            }

            val arte = categories.find { it.name == "Arte" }
            if (arte?.events?.isEmpty() == true) {
                arte.events.addAll(listOf(
                    Event("Expo Pintura", "Muestra de talentos locales con técnicas de óleo y acuarela.", "22-04-2024"),
                    Event("Galería Moderna", "Inauguración de la nueva colección de escultura abstracta.", "30-07-2024")
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
