package br.edu.utfpr.appcontatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.utfpr.appcontatos.ui.contact.list.ContactsListScreen
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContatosTheme {
                ContactsListScreen()
            }
        }
    }
}
