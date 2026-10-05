# Calculadora de Carrinho de Compras - Android Jetpack Compose

**Nome Completo:** [Seu Nome Completo Aqui]  
**Repositório:** [Link do Repositório no GitHub]  
**Vídeo de Apresentação:** [Link do Vídeo no YouTube (Não Listado)]  

---

## 📱 Sobre o Projeto

O **Calculadora de Carrinho de Compras** é um aplicativo Android desenvolvido em **Kotlin** e **Jetpack Compose** (Material Design 3). O projeto implementa a gestão de um catálogo de produtos, cálculo de subtotal, descontos percentuais e valor total final, além do processamento funcional de coleções para geração de relatórios no **Logcat**.

---

## 🎯 Cenário de Validação e Resultados Esperados

O aplicativo inicia por padrão com os dados do cenário de validação:

1. **Notebook Dell Inspiron** | R$ 3.499,00 | Desconto: 5% | Qtd: 2
2. **Mouse sem fio** | R$ 89,90 | Desconto: 0% | Qtd: 1
3. **Teclado mecânico RGB** | R$ 349,90 | Desconto: 0% | Qtd: 1

### 📊 Resumo Calculado na Tela:
- **Subtotal bruto:** R$ 7.437,80
- **Descontos aplicados:** R$ 349,90
- **Valor Total Final:** R$ 7.087,90

---

## 🛠️ Requisitos Técnicos e Arquitetura

### 1. Modelagem de Dados
- **`Pagavel` (Interface):** Contrato para cálculo de valor total (`fun calcularTotal(): Double`). Implemented por `ItemCarrinho` e `Carrinho`.
- **`Produto` (Data Class):** Atributos: `id`, `nome`, `preco`, `descricao` (`String?` - com valor nulo seguro), e `descontoPercentual` (padrão 0.0).
- **`ItemCarrinho` (Data Class):** Relaciona um `Produto` a uma `quantidade`.
- **`Carrinho` (Data Class):** Agrupa os itens do carrinho e realiza os cálculos agregados de subtotal, descontos e total final.

### 2. Catálogo e Regras de Negócio
- Catálogo fixo com 7 produtos abrangendo:
  - Produtos com desconto (ex: 5%, 10%, 15%).
  - Produtos sem descrição (`null`).
  - Produto com nome longo (para validação do layout visual com reticências).
- Lógica de cálculo puramente declarativa no domínio (funções puras).

### 3. Processamento Funcional de Coleções (Logcat)
Relatório emitido via `RelatorioService` utilizando métodos funcionais do Kotlin:
```kotlin
val relatorioLinhas = itens
    .filter { item -> item.produto.descontoPercentual > 0 }
    .sortedByDescending { item -> item.calcularTotal() }
    .map { item ->
        "PRODUTO: ${item.produto.nome} | VALOR FINAL: ${formatarMoeda(item.calcularTotal())}"
    }
```
- Filtra apenas produtos com desconto.
- Ordena do maior valor total para o menor.
- Mapeia para strings formatadas em Moeda Brasileira (R$).
- Acumula total economizado via `fold`.

### 4. Interface Gráfica (UI) e Parametrização
- **`ItemCarrinhoRow`:** Componente reutilizável com passagem de parâmetros explícitos (sem dados estáticos hardcoded).
- **Tratamento seguro de nulos:** Descrições nulas exibem automaticamente o valor padrão `"Sem descrição"`.
- **Tipografia Material Design 3:** Utilização exclusiva de `MaterialTheme.typography` (sem valores manuais de `fontSize`).
- **Limites Visuais:** Nomes limitados a 1 linha e descrições a no máximo 2 linhas (`TextOverflow.Ellipsis`).

---

## 📸 Capturas de Tela

### 1. Tela do Emulador
*(Insira aqui a imagem da tela principal do aplicativo rodando no emulador)*

### 2. Relatório no Logcat
*(Insira aqui a imagem do terminal Logcat exibindo a tag `RelatorioCarrinho`)*

---

## 🧪 Testes Unitários

O projeto inclui testes automatizados com JUnit em `CarrinhoCalculadoraTest.kt`, cobrindo:
- Validação dos totais calculados no cenário de teste.
- Cumprimento da interface `Pagavel`.
- Requisitos do catálogo e tratamento seguro de nulos.
- Operações funcionais em coleções.

Para executar os testes:
```bash
./gradlew testDebugUnitTest
```
