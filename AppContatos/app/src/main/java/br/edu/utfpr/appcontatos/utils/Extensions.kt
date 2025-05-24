package br.edu.utfpr.appcontatos.utils

import android.icu.text.DecimalFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// "55988884444".toFormattedPhone() -> "(55) 98888-4444"
fun String.toFormattedPhone(): String {
    val formattedText = this.mapIndexed { index, char ->
        when {
            index == 0 -> "($char"
            index == 2 -> ") $char"
            (index == 6 && length < 11) ||
                    (index == 7 && length == 11) -> "-$char"
            else -> char
        }
    }
    return formattedText.joinToString("")
}

// BigDecimal("1550.55").format() -> "R$1.550,55"
fun BigDecimal.format(): String {
    val formatter = DecimalFormat("R$#,##0.00")
    return formatter.format(this)
}

// LocalDate.of(2025, 5, 24).format() -> "24/05/2025"
fun LocalDate.format(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return this.format(formatter)
}