package br.edu.utfpr.appcontatos.ui.contact.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.appcontatos.ui.contact.composables.DefaultErrorContent
import br.edu.utfpr.appcontatos.ui.contact.composables.DefaultLoadingContent
import br.edu.utfpr.appcontatos.ui.contact.details.composables.AppBar
import br.edu.utfpr.appcontatos.ui.contact.details.composables.ContactDetails

@Composable
fun ContactDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = viewModel()
) {
    val contentModifier: Modifier = modifier.fillMaxSize()
    if (viewModel.uiState.isLoading) {
        DefaultLoadingContent(
            modifier = contentModifier
        )
    } else if (viewModel.uiState.hasErrorLoading) {
        DefaultErrorContent(
            modifier = contentModifier,
            onTryAgainPressed = viewModel::loadContact
        )
    } else {
        Scaffold(
            modifier = contentModifier,
            topBar = {
                AppBar(
                    contact = viewModel.uiState.contact,
                    onBackPressed = {},
                    onEditPressed = {},
                    onDeletePressed = {},
                    onFavoritePressed = {}
                )
            }
        ) { innerPadding ->
            ContactDetails(
                modifier = Modifier.padding(innerPadding),
                contact = viewModel.uiState.contact,
                onContactInfoPressed = {}
            )
        }
    }
}