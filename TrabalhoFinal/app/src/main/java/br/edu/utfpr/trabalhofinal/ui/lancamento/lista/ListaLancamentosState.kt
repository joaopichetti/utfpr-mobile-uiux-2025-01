package br.edu.utfpr.trabalhofinal.ui.lancamento.lista

import br.edu.utfpr.trabalhofinal.data.Lancamento

data class ListaLancamentosState(
    val carregando: Boolean = false,
    val erroAoCarregar: Boolean = false,
    val lancamentos: List<Lancamento> = listOf()
)