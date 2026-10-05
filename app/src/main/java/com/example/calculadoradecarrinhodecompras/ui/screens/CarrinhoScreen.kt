package com.example.calculadoradecarrinhodecompras.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculadoradecarrinhodecompras.data.Catalogo
import com.example.calculadoradecarrinhodecompras.data.RelatorioService
import com.example.calculadoradecarrinhodecompras.model.Carrinho
import com.example.calculadoradecarrinhodecompras.model.ItemCarrinho
import com.example.calculadoradecarrinhodecompras.ui.components.CatalogoDialog
import com.example.calculadoradecarrinhodecompras.ui.components.ItemCarrinhoRow
import com.example.calculadoradecarrinhodecompras.ui.components.ResumoCarrinhoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen() {
    var itensCarrinho by remember { mutableStateOf(Catalogo.getCarrinhoInicial()) }

    var exibirDialogCatalogo by remember { mutableStateOf(value = false) }

    val carrinho = Carrinho(itens = itensCarrinho)

    LaunchedEffect(itensCarrinho) {
        RelatorioService.processarERelatarDescontos(itensCarrinho)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meu Carrinho",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Ícone do Carrinho",
                        modifier = Modifier.padding(start = 12.dp, end = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            ResumoCarrinhoCard(
                subtotalBruto = carrinho.calcularSubtotalBruto(),
                totalDescontos = carrinho.calcularTotalDesconto(),
                valorTotalFinal = carrinho.calcularTotal(),
                onAdicionarProdutoClique = { exibirDialogCatalogo = true },
                onRestaurarCenarioClique = { itensCarrinho = Catalogo.getCarrinhoInicial() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (itensCarrinho.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "O seu carrinho está vazio.\nAdicione produtos pelo botão '+ Catálogo'.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = itensCarrinho,
                        key = { item -> item.produto.id }
                    ) { item ->
                        ItemCarrinhoRow(
                            nome = item.produto.nome,
                            precoUnitario = item.produto.preco,
                            descontoPercentual = item.produto.descontoPercentual,
                            descricao = item.produto.descricao,
                            quantidade = item.quantidade,
                            totalItem = item.calcularTotal(),
                            onIncrementar = {
                                itensCarrinho = itensCarrinho.map {
                                    if (it.produto.id == item.produto.id) {
                                        it.copy(quantidade = it.quantidade + 1)
                                    } else it
                                }
                            },
                            onDecrementar = {
                                itensCarrinho = itensCarrinho.map {
                                    if ((it.produto.id == item.produto.id) && (it.quantidade > 1)) {
                                        it.copy(quantidade = it.quantidade - 1)
                                    } else it
                                }
                            },
                            onRemover = {
                                itensCarrinho = itensCarrinho.filter { it.produto.id != item.produto.id }
                            }
                        )
                    }
                }
            }
        }
    }

    if (exibirDialogCatalogo) {
        CatalogoDialog(
            produtos = Catalogo.produtos,
            onAdicionarProduto = { produtoSelecionado ->
                val itemExistente = itensCarrinho.find { it.produto.id == produtoSelecionado.id }
                itensCarrinho = if (itemExistente != null) {
                    itensCarrinho.map {
                        if (it.produto.id == produtoSelecionado.id) {
                            it.copy(quantidade = it.quantidade + 1)
                        } else it
                    }
                } else {
                    itensCarrinho + ItemCarrinho(
                        produto = produtoSelecionado,
                        quantidade = 1
                    )
                }
            },
            onDismiss = { exibirDialogCatalogo = false }
        )
    }
}
