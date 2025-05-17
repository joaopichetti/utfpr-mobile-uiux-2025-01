package br.edu.utfpr.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.appcontatos.data.Contact
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun QuickActionsRow(
    modifier: Modifier = Modifier,
    contact: Contact,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickAction(
            imageVector = Icons.Filled.Phone,
            text = "Ligar",
            onPressed = {},
            enabled = enabled && contact.phoneNumber.isNotBlank()
        )
        QuickAction(
            imageVector = Icons.Filled.Sms,
            text = "Mensagem",
            onPressed = {},
            enabled = enabled && contact.phoneNumber.isNotBlank()
        )
        QuickAction(
            imageVector = Icons.Filled.Videocam,
            text = "Vídeo",
            onPressed = {},
            enabled = enabled && contact.phoneNumber.isNotBlank()
        )
        QuickAction(
            imageVector = Icons.Filled.Email,
            text = "E-mail",
            onPressed = {},
            enabled = enabled && contact.email.isNotBlank()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuickActionsRowPreview() {
    AppContatosTheme {
        QuickActionsRow(
            contact = Contact(
                phoneNumber = "(99) 98888-8888"
            )
        )
    }
}