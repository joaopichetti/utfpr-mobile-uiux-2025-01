package br.edu.utfpr.trabalhofinal.ui.lancamento.lista

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.trabalhofinal.R
import br.edu.utfpr.trabalhofinal.data.Lancamento
import br.edu.utfpr.trabalhofinal.data.TipoLancamentoEnum
import br.edu.utfpr.trabalhofinal.ui.theme.TrabalhoFinalTheme
import br.edu.utfpr.trabalhofinal.ui.utils.composables.Carregando
import br.edu.utfpr.trabalhofinal.ui.utils.composables.ErroAoCarregar
import br.edu.utfpr.trabalhofinal.utils.calcularProjecao
import br.edu.utfpr.trabalhofinal.utils.calcularSaldo
import br.edu.utfpr.trabalhofinal.utils.formatar
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun ListaLancamentosScreen(
    modifier: Modifier = Modifier,
    onAdicionarPressed: () -> Unit,
    onLancamentoPressed: (Lancamento) -> Unit,
    viewModel: ListaLancamentosViewModel = viewModel()
) {
    val contentModifier: Modifier = modifier.fillMaxSize()
    if (viewModel.state.carregando) {
        Carregando(modifier = contentModifier)
    } else if (viewModel.state.erroAoCarregar) {
        ErroAoCarregar(
            modifier = contentModifier,
            onTryAgainPressed = viewModel::carregarLancamentos,
        )
    } else {
        Scaffold(
            modifier = contentModifier,
            topBar = { AppBar(onAtualizarPressed = viewModel::carregarLancamentos) },
            bottomBar = { BottomBar(lancamentos = viewModel.state.lancamentos) },
            floatingActionButton = {
                FloatingActionButton(onClick = onAdicionarPressed) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.adicionar)
                    )
                }
            }
        ) { paddingValues ->
            val modifierWithPadding = Modifier.padding(paddingValues)
            if (viewModel.state.lancamentos.isEmpty()) {
                ListaVazia(modifier = modifierWithPadding.fillMaxSize())
            } else {
                List(
                    modifier = modifierWithPadding,
                    lancamentos = viewModel.state.lancamentos,
                    onLancamentoPressed = onLancamentoPressed
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    onAtualizarPressed: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.lancamentos)) },
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = onAtualizarPressed) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.atualizar)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    TrabalhoFinalTheme {
        AppBar(
            onAtualizarPressed = {}
        )
    }
}

@Composable
private fun ListaVazia(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = stringResource(R.string.lista_vazia_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = stringResource(R.string.lista_vazia_subtitle),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListaVaziaPreview() {
    TrabalhoFinalTheme {
        ListaVazia()
    }
}

@Composable
private fun List(
    modifier: Modifier = Modifier,
    lancamentos: List<Lancamento>,
    onLancamentoPressed: (Lancamento) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(lancamentos) { lancamento ->
            val pago = if (lancamento.paga) "pago" else "pendente"
            val descricao = "${lancamento.data.formatar()} - ${lancamento.descricao} - ${lancamento.valor} - $pago"

            ListItem(
                modifier = Modifier.clickable { onLancamentoPressed(lancamento) },
                headlineContent = { Text(descricao) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListPreview() {
    TrabalhoFinalTheme {
        List(
            lancamentos = gerarLancamentos(),
            onLancamentoPressed = {}
        )
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    lancamentos: List<Lancamento>
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Totalizador(
            modifier = Modifier.padding(top = 20.dp),
            titulo = stringResource(R.string.saldo),
            valor = lancamentos.calcularSaldo(),
            textColor = MaterialTheme.colorScheme.secondary
        )
        Totalizador(
            modifier = Modifier.padding(bottom = 20.dp),
            titulo = stringResource(R.string.previsao),
            valor = lancamentos.calcularProjecao(),
            textColor = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun Totalizador(
    modifier: Modifier = Modifier,
    titulo: String,
    valor: BigDecimal,
    textColor: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            text = titulo,
            color = textColor
        )
        Spacer(Modifier.size(10.dp))
        Text(
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.End,
            text = valor.formatar(),
            color = textColor
        )
        Spacer(Modifier.size(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview() {
    TrabalhoFinalTheme {
        BottomBar(
            lancamentos = gerarLancamentos()
        )
    }
}

private fun gerarLancamentos(): List<Lancamento> = listOf(
    Lancamento(
        descricao = "Salário",
        valor = BigDecimal("5000.0"),
        tipo = TipoLancamentoEnum.RECEITA,
        data = LocalDate.of(2024, 9, 5),
        paga = true
    ),
    Lancamento(
        descricao = "Aluguel",
        valor = BigDecimal("1500.0"),
        tipo = TipoLancamentoEnum.DESPESA,
        data = LocalDate.of(2024, 9, 10),
        paga = true
    ),
    Lancamento(
        descricao = "Condomínio",
        valor = BigDecimal("200.0"),
        tipo = TipoLancamentoEnum.DESPESA,
        data = LocalDate.of(2024, 9, 15),
        paga = false
    )
)