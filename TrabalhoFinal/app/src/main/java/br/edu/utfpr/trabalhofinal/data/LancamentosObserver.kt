package br.edu.utfpr.trabalhofinal.data

interface LancamentosObserver {
    fun onUpdate(lancamentosAtualizados: List<Lancamento>)
}