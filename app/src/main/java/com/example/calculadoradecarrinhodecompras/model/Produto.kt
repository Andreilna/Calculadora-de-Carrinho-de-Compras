package com.example.calculadoradecarrinhodecompras.model

data class Produto(
    val id: String,
    val nome: String,
    val preco: Double,
    val descricao: String? = null,
    val descontoPercentual: Double = 0.0
) {
    fun precoComDesconto(): Double {
        val fatorDesconto = 1.0 - (descontoPercentual / 100.0)
        return preco * fatorDesconto
    }

    fun valorDescontoUnitario(): Double {
        return preco * (descontoPercentual / 100.0)
    }
}
