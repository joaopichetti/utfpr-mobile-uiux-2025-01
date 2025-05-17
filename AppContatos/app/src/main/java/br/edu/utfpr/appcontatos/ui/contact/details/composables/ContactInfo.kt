package br.edu.utfpr.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun ContactInfo(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    value: String,
    enabled: Boolean = true,
    onPressed: () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onPressed
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = imageVector,
                contentDescription = value
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactInfoPreview() {
    AppContatosTheme {
        ContactInfo(
            imageVector = Icons.Filled.Phone,
            value = "(46) 99191-9911",
            onPressed = {}
        )
    }
}