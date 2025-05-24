package br.edu.utfpr.trabalhofinal.ui.lancamento.lista

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.edu.utfpr.trabalhofinal.data.Lancamento
import br.edu.utfpr.trabalhofinal.data.LancamentoDatasource
import br.edu.utfpr.trabalhofinal.data.LancamentosObserver

class ListaLancamentosViewModel : ViewModel(), LancamentosObserver {
    var state: ListaLancamentosState by mutableStateOf(ListaLancamentosState())
        private set

    init {
        LancamentoDatasource.instance.registrarObserver(this)
        carregarLancamentos()
    }

    override fun onCleared() {
        LancamentoDatasource.instance.removerObserver(this)
        super.onCleared()
    }

    fun carregarLancamentos() {
        state = state.copy(
            carregando = true,
            erroAoCarregar = false
        )
        val lancamentos = LancamentoDatasource.instance.listar()
        state = state.copy(
            carregando = false,
            lancamentos = lancamentos
        )
    }

    override fun onUpdate(lancamentosAtualizados: List<Lancamento>) {
        state = state.copy(lancamentos = lancamentosAtualizados)
    }
}