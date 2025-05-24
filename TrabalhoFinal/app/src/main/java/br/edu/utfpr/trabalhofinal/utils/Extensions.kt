package br.edu.utfpr.trabalhofinal.utils

import br.edu.utfpr.trabalhofinal.data.Lancamento
import br.edu.utfpr.trabalhofinal.data.TipoLancamentoEnum
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun List<Lancamento>.calcularSaldo(): BigDecimal = map {
    if (it.paga) {
        if (it.tipo == TipoLancamentoEnum.DESPESA) {
            it.valor.negate()
        } else {
            it.valor
        }
    } else {
        BigDecimal.ZERO
    }
}.sumOf { it }

fun List<Lancamento>.calcularProjecao(): BigDecimal = map {
    if (it.tipo == TipoLancamentoEnum.DESPESA) it.valor.negate() else it.valor
}.sumOf { it }

fun BigDecimal.formatar(): String {
    val formatter = DecimalFormat("R$#,##0.00")
    return formatter.format(this)
}

fun LocalDate.formatar(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return format(formatter)
}