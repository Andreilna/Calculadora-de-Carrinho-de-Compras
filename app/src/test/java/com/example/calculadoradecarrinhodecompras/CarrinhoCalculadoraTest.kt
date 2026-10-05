package com.example.calculadoradecarrinhodecompras

import com.example.calculadoradecarrinhodecompras.data.Catalogo
import com.example.calculadoradecarrinhodecompras.model.Carrinho
import com.example.calculadoradecarrinhodecompras.model.ItemCarrinho
import com.example.calculadoradecarrinhodecompras.model.Pagavel
import com.example.calculadoradecarrinhodecompras.model.Produto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CarrinhoCalculadoraTest {

    @Test
    fun testCenarioDeValidacaoValoresEsperados() {
        val itens = Catalogo.getCarrinhoInicial()
        val carrinho = Carrinho(itens)

        val subtotalBrutoEsperado = 7437.80
        val descontoEsperado = 349.90
        val totalFinalEsperado = 7087.90

        assertEquals(subtotalBrutoEsperado, carrinho.calcularSubtotalBruto(), 0.001)
        assertEquals(descontoEsperado, carrinho.calcularTotalDesconto(), 0.001)
        assertEquals(totalFinalEsperado, carrinho.calcularTotal(), 0.001)
    }

    @Test
    fun testContratoPagavel() {
        val produto = Produto("p1", "Item Teste", 100.0, null, 10.0)
        val item = ItemCarrinho(produto, 2)
        val carrinho = Carrinho(listOf(item))

        val pagavelItem: Pagavel = item
        val pagavelCarrinho: Pagavel = carrinho

        assertEquals(180.0, pagavelItem.calcularTotal(), 0.001)
        assertEquals(180.0, pagavelCarrinho.calcularTotal(), 0.001)
    }

    @Test
    fun testRequisitosCatalogo() {
        val produtos = Catalogo.produtos

        assertTrue(produtos.size >= 6)

        val produtosComDesconto = produtos.filter { it.descontoPercentual > 0 }
        assertTrue(produtosComDesconto.size >= 2)

        val produtosSemDescricao = produtos.filter { it.descricao == null }
        assertTrue(produtosSemDescricao.isNotEmpty())

        val produtosNomeLongo = produtos.filter { it.nome.length > 30 }
        assertTrue(produtosNomeLongo.isNotEmpty())
    }

    @Test
    fun testOperacoesFuncionaisColecoesRelatorio() {
        val itens = Catalogo.getCarrinhoInicial()

        val resultado = itens
            .filter { it.produto.descontoPercentual > 0 }
            .sortedByDescending { it.calcularTotal() }
            .map { it.produto.nome to it.calcularTotal() }

        assertEquals(1, resultado.size)
        assertTrue(resultado[0].first.startsWith("Notebook Dell Inspiron"))
        assertEquals(6648.10, resultado[0].second, 0.001)
    }

    @Test
    fun testManipulacaoSeguraDescricaoNula() {
        val produtoSemDescricao = Produto("p3", "Teclado", 100.0, null, 0.0)
        assertNull(produtoSemDescricao.descricao)

        val descricaoTratada = produtoSemDescricao.descricao ?: "Sem descrição"
        assertEquals("Sem descrição", descricaoTratada)
    }
}
