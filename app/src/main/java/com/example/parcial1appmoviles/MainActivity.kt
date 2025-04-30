package com.example.parcial1appmoviles

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial1appmoviles.ui.theme.Parcial1AppMovilesTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial1AppMovilesTheme {
                val navController = rememberNavController()
                var montoTotalBilletera by rememberSaveable { mutableStateOf(500) }
                var listaComprobantes by rememberSaveable { mutableStateOf(listOf<Comprobante>()) }

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeView(
                            navController,
                            montoTotalBilletera,
                            listaComprobantes,
                            onRetirarDinero = { montoRetiro ->
                                if (montoRetiro <= montoTotalBilletera && montoRetiro > 0) {
                                    val nuevoComprobante = Comprobante(
                                        id = listaComprobantes.size + 1,
                                        monto = montoRetiro,
                                        fecha = LocalDateTime.now()
                                    )
                                    listaComprobantes = listaComprobantes + nuevoComprobante
                                    montoTotalBilletera -= montoRetiro
                                    nuevoComprobante.id
                                } else {
                                    -1
                                }
                            }
                        )
                    }
                    composable("comprobante/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        id?.let {
                            val navController = rememberNavController()
                            val comprobante = listaComprobantes.find { it.id == id }
                            comprobante?.let {
                                DetalleComprobanteView(navController, comprobante)
                            }
                        }
                    }
                }
            }
        }
    }
}