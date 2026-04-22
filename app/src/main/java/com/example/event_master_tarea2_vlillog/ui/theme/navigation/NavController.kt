package com.example.event_master_tarea2_vlillog.ui.theme.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.event_master_tarea2_vlillog.BottomBar
import com.example.event_master_tarea2_vlillog.R
import com.example.event_master_tarea2_vlillog.ui.theme.components.CategoryItem
import com.example.event_master_tarea2_vlillog.ui.theme.screen.NewCategoriaScreen
import com.example.event_master_tarea2_vlillog.ui.theme.viewmodel.EventViewModel
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class SecondPage(val name: String)

@Serializable
object NewCategoria

@Serializable
data class EventDetail(val categoryName: String, val eventTitle: String)

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val eventViewModel: EventViewModel = viewModel()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(navController = navController, viewModel = eventViewModel)
        }
        composable<SecondPage> { backstackEntry ->
            val args = backstackEntry.toRoute<SecondPage>()
            SecondPageScreen(navController = navController, categoryName = args.name, viewModel = eventViewModel)
        }
        composable<NewCategoria> {
            NewCategoriaScreen(navController = navController, viewModel = eventViewModel)
        }
        composable<EventDetail> { backstackEntry ->
            val args = backstackEntry.toRoute<EventDetail>()
            EventDetailScreen(navController = navController, categoryName = args.categoryName, eventTitle = args.eventTitle, viewModel = eventViewModel)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, viewModel: EventViewModel) {
    val categories = viewModel.categories

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                stringResource(id = R.string.titulo_inicio),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                stringResource(id = R.string.categorias),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(12.dp)
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    CategoryItem(category) {
                        navController.navigate(SecondPage(category.name))
                    }
                }
            }
        }
    }
}

@Composable
fun SecondPageScreen(navController: NavHostController, categoryName: String, viewModel: EventViewModel) {
    val category = viewModel.getCategory(categoryName)
    var showDialog by remember { mutableStateOf(false) }
    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    
    var titleError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Text(
                text = "Eventos de $categoryName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Evento")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            if (category != null) {
                LazyColumn {
                    items(category.events) { event ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate(EventDetail(categoryName, event.title))
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = event.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text(text = event.date, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showDialog = false
                    titleError = false
                    dateError = false
                },
                title = { Text(stringResource(id= R.string.Nuevo_evento)) },
                text = {
                    Column {
                        TextField(
                            value = eventTitle,
                            onValueChange = { 
                                eventTitle = it 
                                if (it.isNotEmpty()) titleError = false
                            },
                            label = { Text(stringResource(id = R.string.Titulo_evento)) },
                            isError = titleError,
                            supportingText = {
                                if (titleError) Text(stringResource(id = R.string.Titulo_obligatorio))
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = eventDescription,
                            onValueChange = { eventDescription = it },
                            label = { Text(stringResource(id = R.string.Descripcion)) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = eventDate,
                            onValueChange = { 
                                eventDate = it 
                                if (it.isNotEmpty()) dateError = false
                            },
                            label = { Text(stringResource(id = R.string.Fecha_evento)) },
                            isError = dateError,
                            supportingText = {
                                if (dateError) Text(stringResource(id = R.string.Fecha_obligatoria))
                            }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val isTitleEmpty = eventTitle.trim().isEmpty()
                        val isDateEmpty = eventDate.trim().isEmpty()
                        
                        titleError = isTitleEmpty
                        dateError = isDateEmpty
                        
                        if (!isTitleEmpty && !isDateEmpty) {
                            viewModel.addEventToCategory(categoryName, eventTitle.trim(), eventDescription.trim(), eventDate.trim())
                            eventTitle = ""
                            eventDescription = ""
                            eventDate = ""
                            showDialog = false
                        }
                    }) {
                        Text("Agregar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { 
                        showDialog = false
                        titleError = false
                        dateError = false
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavHostController, categoryName: String, eventTitle: String, viewModel: EventViewModel) {
    val category = viewModel.getCategory(categoryName)
    val event = category?.events?.find { it.title == eventTitle }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.Detalle_evento)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (event != null) {
                Text(
                    text = event.title,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = event.date,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = stringResource(id = R.string.Descripcion_2),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = event.description.ifEmpty { stringResource(id = R.string.SinDescricpion) },
                            fontSize = 16.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Categoría: $categoryName",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            } else {
                Text("Evento no encontrado")
            }
        }
    }
}
