package br.edu.utfpr.appcontatos.ui.contact.form.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
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
import br.edu.utfpr.appcontatos.data.ContactTypeEnum
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun FormRadioButton(
    modifier: Modifier = Modifier,
    value: ContactTypeEnum,
    groupValue: ContactTypeEnum,
    onValueChanged: (ContactTypeEnum) -> Unit,
    enabled: Boolean = true,
    label: String
) {
    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = {
                    onValueChanged(value)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = value == groupValue,
            onClick = { onValueChanged(value) },
            enabled = enabled
        )
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
private fun FormRadioButtonPreview() {
    AppContatosTheme {
        var groupValue by remember {
            mutableStateOf(ContactTypeEnum.PERSONAL)
        }
        Column {
            FormRadioButton(
                modifier = Modifier.padding(20.dp),
                value = ContactTypeEnum.PERSONAL,
                groupValue = groupValue,
                onValueChanged = { newValue ->
                    groupValue = newValue
                },
                label = "Pessoal"
            )
            FormRadioButton(
                modifier = Modifier.padding(20.dp),
                value = ContactTypeEnum.PROFESSIONAL,
                groupValue = groupValue,
                onValueChanged = { newValue ->
                    groupValue = newValue
                },
                label = "Profissional"
            )
        }
    }
}