package br.edu.utfpr.appcontatos.ui.contact.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.appcontatos.data.Contact
import br.edu.utfpr.appcontatos.data.ContactDatasource
import br.edu.utfpr.appcontatos.data.ContactsObserver
import br.edu.utfpr.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsListViewModel : ViewModel(), ContactsObserver {
    var uiState: ContactsListUiState by mutableStateOf(ContactsListUiState())
        private set
    private val datasource: ContactDatasource = ContactDatasource.instance

    init {
        datasource.registerObserver(this)
        loadContacts()
    }

    override fun onCleared() {
        datasource.unregisterObserver(this)
        super.onCleared()
    }

    fun loadContacts() {
        uiState = uiState.copy(
            isLoading = true,
            hasError = false
        )
        viewModelScope.launch {
            delay(2000)
            uiState = uiState.copy(
                contacts = datasource.findAll().groupByInitial(),
                isLoading = false
            )
        }
    }

    fun toggleFavorite(pressedContact: Contact) {
        val updatedContact = pressedContact.copy(isFavorite = !pressedContact.isFavorite)
        datasource.save(updatedContact)
    }

    override fun onUpdate(updatedContacts: List<Contact>) {
        uiState = uiState.copy(contacts = updatedContacts.groupByInitial())
    }
}