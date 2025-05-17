package br.edu.utfpr.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.appcontatos.data.Contact
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun CardContactInfo(
    modifier: Modifier = Modifier,
    contact: Contact,
    enabled: Boolean = true,
    onContactInfoPressed: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Informações de contato",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        ContactInfo(
            imageVector = Icons.Outlined.Phone,
            value = contact.phoneNumber.ifBlank {
                "Adicionar número de telefone"
            },
            enabled = enabled && contact.phoneNumber.isBlank(),
            onPressed = onContactInfoPressed
        )
        ContactInfo(
            imageVector = Icons.Outlined.Email,
            value = contact.email.ifBlank {
                "Adicionar e-mail"
            },
            enabled = enabled && contact.email.isBlank(),
            onPressed = onContactInfoPressed
        )
        Spacer(Modifier.size(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun CardContactInfoPreview() {
    AppContatosTheme {
        CardContactInfo(
            contact = Contact(
                phoneNumber = "(99) 98888-8888"
            ),
            onContactInfoPressed = {}
        )
    }
}