package br.edu.utfpr.appcontatos.ui.contact.details.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        title = title?.let {
            { Text(it) }
        },
        text = { Text(message) },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ConfirmationDialogPreview() {
    AppContatosTheme {
        ConfirmationDialog(
            message = "Essa operação não poderá ser desfeita",
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfirmationDialogWithTitlePreview() {
    AppContatosTheme {
        ConfirmationDialog(
            title = "Atenção",
            message = "Essa operação não poderá ser desfeita",
            onDismiss = {},
            onConfirm = {}
        )
    }
}