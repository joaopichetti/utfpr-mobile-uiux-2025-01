package br.edu.utfpr.appcontatos.ui.contact.details

import br.edu.utfpr.appcontatos.data.Contact

data class ContactDetailsUiState(
    val isLoading: Boolean = false,
    val hasErrorLoading: Boolean = false,
    val contact: Contact = Contact()
)
