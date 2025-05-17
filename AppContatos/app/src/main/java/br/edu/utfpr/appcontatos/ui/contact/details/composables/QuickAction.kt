package br.edu.utfpr.appcontatos.ui.contact.details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun QuickAction(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    text: String,
    enabled: Boolean = true,
    onPressed: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconButton(
            enabled = enabled,
            onClick = onPressed
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = text
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuickActionPreview() {
    AppContatosTheme {
        QuickAction(
            imageVector = Icons.Filled.Phone,
            text = "Ligar",
            onPressed = {}
        )
    }
}