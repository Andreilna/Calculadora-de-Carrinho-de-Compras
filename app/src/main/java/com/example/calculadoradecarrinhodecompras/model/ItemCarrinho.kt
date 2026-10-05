package com.example.calculadoradecarrinhodecompras.model

data class ItemCarrinho(
    val produto: Produto,
    val quantidade: Int
) : Pagavel {

    override fun calcularTotal(): Double {
        return produto.precoComDesconto() * quantidade
    }

    fun calcularSubtotalBruto(): Double {
        return produto.preco * quantidade
    }

    fun calcularTotalDesconto(): Double {
        return produto.valorDescontoUnitario() * quantidade
    }
}
