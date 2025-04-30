package com.example.parcial1appmoviles

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parcial1appmoviles.ui.theme.Parcial1AppMovilesTheme
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController,
             montoTotalBilletera: Int,
             comprobantes: List<Comprobante>,
             onRetirarDinero: (Int) -> Int,
             modifier: Modifier = Modifier
) {
    var montoARetirarText by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("BILLETERA VIRTUAL")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(top = 10.dp),
                text = "Monto disponible: $montoTotalBilletera",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height((20.dp)))

            TextField(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                value = montoARetirarText,
                onValueChange = { newText ->
                    if (newText.all { it.isDigit() }) {
                        montoARetirarText = newText
                    }
                },
                label = {
                    Text("Ingresar monto a retirar")
                }
            )
            Spacer(modifier = Modifier.height((16.dp)))

            Button(
                onClick = {
                    val monto = montoARetirarText.toIntOrNull() ?: 0
                    if (monto > 0 && monto <= montoTotalBilletera) {
                        val comprobanteId = onRetirarDinero(monto)
                        if (comprobanteId != -1)
                        {
                            montoARetirarText = ""
                            navController.navigate("comprobante/${comprobanteId}")
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Retirar dinero")
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (comprobantes.isNotEmpty()) {
                Text(
                    "Retiros anteriores:",
                    style = MaterialTheme.typography.titleMedium
                )

                LazyColumn (
                    modifier = Modifier.padding(padding)
                        .fillMaxWidth()
                        .weight(2f),
                    contentPadding = PaddingValues(15.dp)
                ) {
                    val comprobantesOrdenados = comprobantes.sortedByDescending { it.fecha }

                    items(comprobantesOrdenados) { comprobante ->
                        val fechaFormateada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        navController.navigate("comprobante/${comprobante.id}")
                                    }
                                )
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                Text(
                                    text = "Comprobante #${comprobante.id}: $${comprobante.monto}",
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                                Text("Fecha: ${comprobante.fecha.format(fechaFormateada)}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Spacer(modifier = Modifier.height((15.dp)))
                    }
                }
            } else {
                Text(
                    "No se encontraron retiros anteriores...",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Parcial1AppMovilesTheme {
        val montoTotalBilletera = 500
        val list = listOf<Comprobante>()
        HomeView(
            rememberNavController(),
            montoTotalBilletera = montoTotalBilletera,
            list,
            onRetirarDinero = { montoARetirar -> montoARetirar },
        )
    }
}