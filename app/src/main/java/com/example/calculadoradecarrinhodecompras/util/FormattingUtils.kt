package com.example.calculadoradecarrinhodecompras.util

import java.text.NumberFormat
import java.util.Locale

object FormattingUtils {

    fun formatarMoeda(valor: Double): String {
        val localePtBr = Locale.forLanguageTag("pt-BR")
        val numberFormat = NumberFormat.getCurrencyInstance(localePtBr)
        return numberFormat.format(valor)
    }
}
