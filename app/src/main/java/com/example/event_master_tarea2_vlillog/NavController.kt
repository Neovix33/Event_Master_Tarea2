package com.example.event_master_tarea2_vlillog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class SecondPage(val name: String)

@Serializable
object NewCategoria
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(navController = navController)
        }
        composable<SecondPage> { backstackEntry -> val args = backstackEntry.toRoute<SecondPage>()
            SecondPageScreen(navController = navController, name = args.name)
        }
        composable<NewCategoria>{
            NewCategoriaScreen(navController = navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {

    val categories = listOf(
        Category("Música"),
        Category("Deportes"),
        Category("Tecnología"),
        Category("Arte"),
        Category("Gaming"),
        Category("Gaming"),
        Category("Gaming"),
        Category("Conferencias")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) }

    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Text(
                stringResource(id = R.string.titulo_inicio),
                fontSize = 30.sp
            )

            Text(stringResource(id = R.string.categorias))

            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier.padding(8.dp)
            ) {

                items(categories.size) { index ->

                    val category = categories[index]

                    CategoryItem(category) {
                        navController.navigate(
                            SecondPage(category.name)
                        )
                    }

                }

            }

        }

    }
}
@Composable
fun SecondPageScreen(navController: NavHostController, name: String) {
    Text(
        stringResource(id = R.string.bienvenido_a_second_page),
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    )
}

@Composable
fun NewCategoriaScreen(navController: NavHostController){

    Column {

        Text(
            text = stringResource(id = R.string.Newcategoria),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

    }
}

