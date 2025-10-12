package com.nespichanl.is8n1sp.ui.home

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.nespichanl.is8n1sp.R
import com.nespichanl.is8n1sp.transfer.ProductEntryActivity
import com.nespichanl.is8n1sp.ui.tableview.S07TableActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenMembers: () -> Unit = {},
    onOpenAbout: () -> Unit = {},
    onOpenGrades: () -> Unit = {},
    onOpenDataPass: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    val context = LocalContext.current

    val onOpenServicios: () -> Unit = {
        try {
            val intent = Intent(context, ProductEntryActivity::class.java).apply {
                if (context !is Activity) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "No se pudo abrir S05: ${e.javaClass.simpleName}",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    val onOpenS07: () -> Unit = {
        val intent = Intent(context, S07TableActivity::class.java).apply {
            if (context !is Activity) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        DrawerOption("Integrantes") { onOpenMembers() },
        DrawerOption("Acerca de ...")  { onOpenAbout() },
        DrawerOption("Salir")      { onExit() }
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Menú - Grupo 2",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                DrawerHeader()
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                Spacer(Modifier.height(8.dp))
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            item.onClick()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home - Grupo 2", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.teal_700)
                    )
                )
            }
        ) { innerPadding ->
            HomeContent(
                modifier = Modifier.padding(innerPadding),
                onOpenGrades = onOpenGrades,
                onOpenDataPass = onOpenDataPass,
                onOpenServicios = onOpenServicios,
                onOpenS07 = onOpenS07
            )
        }
    }
}

@Composable
private fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.g2_appmovil),
            contentDescription = "Logo Grupo 2",
            modifier = Modifier.size(72.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Grupo 2",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Menú principal",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(12.dp))
    }
}

private data class DrawerOption(val title: String, val onClick: () -> Unit)

/** Contenido del Home: banner + lista de opciones en Cards */
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onOpenGrades: () -> Unit,
    onOpenDataPass: () -> Unit,
    onOpenServicios: () -> Unit,
    onOpenS07: () -> Unit,
) {
    val opciones = listOf(
        OpcionHome(
            titulo = "S02 → Registro de Notas",
            descripcion = "Formulario: Apellido, Curso, Nota1, Nota2, Nota3 → Promedio y Condición",
            onClick = onOpenGrades
        ),
        OpcionHome(
            titulo = "S03 → Paso de Datos entre Actividades",
            descripcion = "Formulario: Id, Nombre, Precio → Mostrar Data Ingresada",
            onClick = onOpenDataPass
        ),
        OpcionHome(
            titulo = "S05 → Manejo de controles básicos y layouts",
            descripcion = "Aplicación de Servicios",
            onClick = onOpenServicios
        ),
        OpcionHome(
            titulo = "S07 → Navegación TableView",
            descripcion = "Datos en forma de tabla utilizando RecyclerView y un diseño personalizado",
            onClick = onOpenS07
        ),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Banner()

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(opciones) { op ->
                ElevatedCard(
                    onClick = op.onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        ),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(op.titulo, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(op.descripcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

private data class OpcionHome(
    val titulo: String,
    val descripcion: String,
    val onClick: () -> Unit
)

/** Banner simple en Compose */
@Composable
private fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFF00BFA5)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Actividades del Curso\nPonte a Prueba",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}
