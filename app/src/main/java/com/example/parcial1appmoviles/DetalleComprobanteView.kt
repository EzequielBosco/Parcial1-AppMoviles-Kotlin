package com.example.parcial1appmoviles

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parcial1appmoviles.ui.theme.Parcial1AppMovilesTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleComprobanteView(navController: NavController,
                           comprobante: Comprobante?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalle de comprobante",
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .padding(top = 10.dp),)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (comprobante != null) {
                Text("Retiro exitoso de $${comprobante.monto} (Comprobante #${comprobante.id})")
            } else {
                Text("Comprobante no encontrado")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetalleComprobantePreview() {
    Parcial1AppMovilesTheme {
        val comprobante = Comprobante(
            id = 1,
            monto = 100,
            fecha = LocalDateTime.now()
        )
        DetalleComprobanteView(
            rememberNavController(),
            comprobante
        )
    }
}