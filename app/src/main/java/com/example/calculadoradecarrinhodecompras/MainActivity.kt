package com.example.calculadoradecarrinhodecompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadoradecarrinhodecompras.ui.screens.CarrinhoScreen
import com.example.calculadoradecarrinhodecompras.ui.theme.CalculadoraDeCarrinhoDeComprasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraDeCarrinhoDeComprasTheme {
                CarrinhoScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarrinhoScreenPreview() {
    CalculadoraDeCarrinhoDeComprasTheme {
        CarrinhoScreen()
    }
}
