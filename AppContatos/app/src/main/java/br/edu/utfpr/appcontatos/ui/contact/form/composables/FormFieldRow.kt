package br.edu.utfpr.appcontatos.ui.contact.form.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness1
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun FormFieldRow(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = imageVector ?: Icons.Default.Brightness1,
            contentDescription = "",
            tint = if (imageVector == null) {
                MaterialTheme.colorScheme.background
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
        Spacer(Modifier.size(16.dp))
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun FormFieldRowPreview() {
    AppContatosTheme {
        Column {
            FormFieldRow(
                imageVector = Icons.Default.Person
            ) {
                FormTextField(
                    value = "João",
                    label = "Nome",
                    onValueChanged = {}
                )
            }
            FormFieldRow {
                FormCheckbox(
                    value = true,
                    label = "Favorito",
                    onValueChanged = {}
                )
            }
        }
    }
}