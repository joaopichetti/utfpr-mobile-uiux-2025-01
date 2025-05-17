package br.edu.utfpr.appcontatos.ui.contact.list

import br.edu.utfpr.appcontatos.data.Contact

data class ContactsListUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val contacts: Map<String, List<Contact>> = emptyMap()
)
