package br.edu.utfpr.appcontatos.ui.contact.form

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.appcontatos.ui.contact.composables.DefaultErrorContent
import br.edu.utfpr.appcontatos.ui.contact.composables.DefaultLoadingContent
import br.edu.utfpr.appcontatos.ui.contact.form.composables.AppBar
import br.edu.utfpr.appcontatos.ui.contact.form.composables.FormContent

@Composable
fun ContactFormScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactFormViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackPressed: () -> Unit,
    onContactSaved: () -> Unit
) {
    LaunchedEffect(viewModel.uiState.contactSaved) {
        if (viewModel.uiState.contactSaved) {
            onContactSaved()
        }
    }

    LaunchedEffect(snackbarHostState, viewModel.uiState.hasErrorSaving) {
        if (viewModel.uiState.hasErrorSaving) {
            snackbarHostState.showSnackbar(
                "Não foi possível salvar o contato. Aguarde um momento e tente novamente"
            )
        }
    }

    val contentModifier: Modifier = modifier.fillMaxSize()
    if (viewModel.uiState.isLoading) {
        DefaultLoadingContent(modifier = contentModifier)
    } else if (viewModel.uiState.hasErrorLoading) {
        DefaultErrorContent(
            modifier = contentModifier,
            onTryAgainPressed = viewModel::loadContact
        )
    } else {
        Scaffold(
            modifier = contentModifier,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                AppBar(
                    isNewContact = viewModel.uiState.isNewContact,
                    onBackPressed = onBackPressed,
                    isSaving = viewModel.uiState.isSaving,
                    onSavePressed = viewModel::save
                )
            }
        ) { paddingValues ->
            FormContent(
                modifier = Modifier.padding(paddingValues),
                isSaving = viewModel.uiState.isSaving,
                formState = viewModel.uiState.formState,
                onFirstNameChanged = viewModel::onFirstNameChanged,
                onLastNameChanged = viewModel::onLastNameChanged,
                onPhoneChanged = viewModel::onPhoneChanged,
                onEmailChanged = viewModel::onEmailChanged,
                onTypeChanged = viewModel::onTypeChanged,
                onIsFavoriteChanged = viewModel::onIsFavoriteChanged,
                onBirthDateChanged = viewModel::onBirthDateChanged,
                onSalaryChanged = viewModel::onSalaryChanged
            )
        }
    }
}