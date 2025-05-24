package br.edu.utfpr.appcontatos.ui.contact.form.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun FormCheckbox(
    modifier: Modifier = Modifier,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit,
    enabled: Boolean = true,
    label: String
) {
    Row(
       modifier = modifier
           .clickable(
               enabled = enabled,
               onClick = {
                   onValueChanged(!value)
               }
           ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = value,
            onCheckedChange = onValueChanged,
            enabled = enabled
        )
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
fun FormCheckboxPreview() {
    AppContatosTheme {
        var checked by remember { mutableStateOf(false) }
        FormCheckbox(
            modifier = Modifier.padding(20.dp),
            value = checked,
            onValueChanged = { newValue ->
                checked = newValue
            },
            label = "Favorito"
        )
    }
}