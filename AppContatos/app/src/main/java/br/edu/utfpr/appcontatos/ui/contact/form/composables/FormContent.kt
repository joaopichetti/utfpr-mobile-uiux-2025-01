package br.edu.utfpr.appcontatos.ui.contact.form.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.appcontatos.data.ContactTypeEnum
import br.edu.utfpr.appcontatos.ui.contact.composables.ContactAvatar
import br.edu.utfpr.appcontatos.ui.contact.form.FormState
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme
import br.edu.utfpr.appcontatos.ui.utils.visualtransformation.PhoneVisualTransformation
import java.time.LocalDate

@Composable
fun FormContent(
    modifier: Modifier = Modifier,
    isSaving: Boolean = false,
    formState: FormState,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onIsFavoriteChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onTypeChanged: (String) -> Unit,
    onSalaryChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(
            modifier = Modifier.padding(16.dp),
            firstName = formState.firstName.value,
            lastName = formState.lastName.value,
            size = 150.dp,
            textStyle = MaterialTheme.typography.displayLarge
        )
        val textFieldPadding = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
        FormFieldRow(
            imageVector = Icons.Default.Person
        ) {
            FormTextField(
                modifier = textFieldPadding,
                value = formState.firstName.value,
                errorMessage = formState.firstName.errorMessage,
                onValueChanged = onFirstNameChanged,
                label = "Nome",
                enabled = !isSaving,
                keyboardCapitalization = KeyboardCapitalization.Words
            )
        }
        FormFieldRow {
            FormTextField(
                modifier = textFieldPadding,
                value = formState.lastName.value,
                errorMessage = formState.lastName.errorMessage,
                onValueChanged = onLastNameChanged,
                label = "Sobrenome",
                enabled = !isSaving,
                keyboardCapitalization = KeyboardCapitalization.Words
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.Phone
        ) {
            FormTextField(
                modifier = textFieldPadding,
                value = formState.phone.value,
                errorMessage = formState.phone.errorMessage,
                onValueChanged = onPhoneChanged,
                label = "Telefone",
                keyboardType = KeyboardType.Phone,
                enabled = !isSaving,
                visualTransformation = PhoneVisualTransformation()
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.Mail
        ) {
            FormTextField(
                modifier = textFieldPadding,
                value = formState.email.value,
                errorMessage = formState.email.errorMessage,
                onValueChanged = onEmailChanged,
                label = "E-mail",
                keyboardType = KeyboardType.Email,
                enabled = !isSaving
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.Cake
        ) {
            FormDatePicker(
                modifier = textFieldPadding,
                value = if (formState.birthDate.value.isBlank()) {
                    LocalDate.now()
                } else {
                    LocalDate.parse(formState.birthDate.value)
                },
                errorMessage = formState.birthDate.errorMessage,
                label = "Aniversário",
                onValueChanged = { newValue ->
                    onBirthDateChanged(newValue.toString())
                },
                enabled = !isSaving
            )
        }
        FormFieldRow(
            imageVector = Icons.Default.MonetizationOn
        ) {
            FormTextField(
                modifier = textFieldPadding,
                value = formState.salary.value,
                errorMessage = formState.salary.errorMessage,
                label = "Salário",
                onValueChanged = onSalaryChanged,
                enabled = !isSaving,
                keyboardType = KeyboardType.Number
            )
        }
        val optionsModifier = Modifier.padding(vertical = 8.dp)
        FormFieldRow {
            FormCheckbox(
                modifier = optionsModifier,
                label = "Favoritar",
                value = formState.isFavorite.value.toBoolean(),
                onValueChanged = { newValue ->
                    onIsFavoriteChanged(newValue.toString())
                },
                enabled = !isSaving
            )
        }
        FormFieldRow {
            val groupValue = if (formState.type.value.isBlank()) {
                ContactTypeEnum.PERSONAL
            } else {
                ContactTypeEnum.valueOf(formState.type.value)
            }
            FormRadioButton(
                modifier = optionsModifier,
                label = "Pessoal",
                value = ContactTypeEnum.PERSONAL,
                groupValue = groupValue,
                onValueChanged = { newValue ->
                    onTypeChanged(newValue.toString())
                },
                enabled = !isSaving
            )
            FormRadioButton(
                modifier = optionsModifier,
                label = "Profissional",
                value = ContactTypeEnum.PROFESSIONAL,
                groupValue = groupValue,
                onValueChanged = { newValue ->
                    onTypeChanged(newValue.toString())
                },
                enabled = !isSaving
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormContentPreview() {
    AppContatosTheme {
        FormContent(
            formState = FormState(),
            onFirstNameChanged = {},
            onLastNameChanged = {},
            onPhoneChanged = {},
            onEmailChanged = {},
            onIsFavoriteChanged = {},
            onBirthDateChanged = {},
            onTypeChanged = {},
            onSalaryChanged = {}
        )
    }
}