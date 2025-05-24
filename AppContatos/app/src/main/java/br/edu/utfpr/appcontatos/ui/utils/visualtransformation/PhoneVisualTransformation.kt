package br.edu.utfpr.appcontatos.ui.utils.visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import br.edu.utfpr.appcontatos.utils.toFormattedPhone

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedPhone = text.text.toFormattedPhone()

        return TransformedText(
            AnnotatedString(formattedPhone),
            PhoneOffsetMapping
        )
    }

    object PhoneOffsetMapping : OffsetMapping {
        //(00) 00000-0000
        //(00) 0000-0000
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 6 -> offset + 4
                offset > 2 -> offset + 3
                offset > 0 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 6 -> offset - 4
                offset > 2 -> offset - 3
                offset > 0 -> offset - 1
                else -> offset
            }
        }
    }
}