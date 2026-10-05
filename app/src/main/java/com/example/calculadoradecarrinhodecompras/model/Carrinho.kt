package com.example.calculadoradecarrinhodecompras.model

data class Carrinho(
    val itens: List<ItemCarrinho> = emptyList()
) : Pagavel {

    override fun calcularTotal(): Double {
        return itens.sumOf { it.calcularTotal() }
    }

    fun calcularSubtotalBruto(): Double {
        return itens.sumOf { it.calcularSubtotalBruto() }
    }

    fun calcularTotalDesconto(): Double {
        return itens.sumOf { it.calcularTotalDesconto() }
    }
}
