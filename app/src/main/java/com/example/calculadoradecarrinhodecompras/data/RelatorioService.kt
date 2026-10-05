package com.example.calculadoradecarrinhodecompras.data

import android.util.Log
import com.example.calculadoradecarrinhodecompras.model.ItemCarrinho
import com.example.calculadoradecarrinhodecompras.util.FormattingUtils.formatarMoeda

object RelatorioService {
    private const val TAG = "RelatorioCarrinho"

    fun processarERelatarDescontos(itens: List<ItemCarrinho>) {
        Log.i(TAG, "==========================================================")
        Log.i(TAG, "      RELATÓRIO DE CARRINHO - PRODUTOS COM DESCONTO       ")
        Log.i(TAG, "==========================================================")

        val relatorioLinhas = itens
            .filter { item -> item.produto.descontoPercentual > 0 }
            .sortedByDescending { item -> item.calcularTotal() }
            .map { item ->
                val valorFinalFormatado = formatarMoeda(item.calcularTotal())
                val valorSemDesconto = formatarMoeda(item.calcularSubtotalBruto())
                "PRODUTO: ${item.produto.nome} | QTD: ${item.quantidade} | DESCONTO: ${item.produto.descontoPercentual}% | VALOR BRUTO: $valorSemDesconto | VALOR FINAL FORMATADO: $valorFinalFormatado"
            }

        if (relatorioLinhas.isEmpty()) {
            Log.i(TAG, "Nenhum produto com desconto aplicado no carrinho.")
        } else {
            relatorioLinhas.forEach { linha ->
                Log.i(TAG, linha)
            }
        }

        val totalDescontosEconomizados = itens
            .filter { item -> item.produto.descontoPercentual > 0 }
            .fold(0.0) { acumulador, item -> acumulador + item.calcularTotalDesconto() }

        Log.i(TAG, "----------------------------------------------------------")
        Log.i(TAG, "TOTAL ECONOMIZADO EM DESCONTOS: ${formatarMoeda(totalDescontosEconomizados)}")
        Log.i(TAG, "==========================================================")
    }
}
