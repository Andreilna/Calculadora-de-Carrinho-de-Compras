package com.example.calculadoradecarrinhodecompras.data

import com.example.calculadoradecarrinhodecompras.model.ItemCarrinho
import com.example.calculadoradecarrinhodecompras.model.Produto

object Catalogo {

    val produtos: List<Produto> = listOf(
        Produto(
            id = "p1",
            nome = "Notebook Dell Inspiron 15",
            preco = 3499.00,
            descricao = "Um notebook rápido para trabalho e estudo com processador Intel Core i7, 16GB RAM e SSD de 512GB.",
            descontoPercentual = 5.0
        ),
        Produto(
            id = "p2",
            nome = "Mouse sem fio",
            preco = 89.90,
            descricao = null,
            descontoPercentual = 0.0
        ),
        Produto(
            id = "p3",
            nome = "Teclado mecânico RGB com Fio",
            preco = 349.90,
            descricao = "Switch azul, ABNT2",
            descontoPercentual = 0.0
        ),
        Produto(
            id = "p4",
            nome = "Monitor Ultrawide Gamer LG 34\" LED Curvo Quad HD 160Hz 1ms Motion Blur Reduction HDR10",
            preco = 2199.00,
            descricao = "Monitor curvo ultrawide com painel IPS, taxa de atualização de 160Hz e tecnologia FreeSync Premium.",
            descontoPercentual = 10.0
        ),
        Produto(
            id = "p5",
            nome = "Headset Gamer USB 7.1 Surround Sound com Microfone Antirruído",
            preco = 299.90,
            descricao = null,
            descontoPercentual = 0.0
        ),
        Produto(
            id = "p6",
            nome = "Cadeira Ergonômica Presidente com Apoio de Cabeça e Braços 3D Adjust",
            preco = 1299.00,
            descricao = "Cadeira de escritório ergonômica com suporte lombar ajustável e reclinação até 150 graus.",
            descontoPercentual = 15.0
        ),
        Produto(
            id = "p7",
            nome = "Webcam Full HD 1080p com Foco Automático",
            preco = 199.00,
            descricao = null,
            descontoPercentual = 0.0
        )
    )

    fun getCarrinhoInicial(): List<ItemCarrinho> {
        return listOf(
            ItemCarrinho(produto = produtos[0], quantidade = 2),
            ItemCarrinho(produto = produtos[1], quantidade = 1),
            ItemCarrinho(produto = produtos[2], quantidade = 1)
        )
    }
}
