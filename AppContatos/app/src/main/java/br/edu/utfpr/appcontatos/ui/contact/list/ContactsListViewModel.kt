package br.edu.utfpr.appcontatos.ui.contact.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.appcontatos.data.Contact
import br.edu.utfpr.appcontatos.data.ContactDatasource
import br.edu.utfpr.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactsListViewModel : ViewModel() {
    var uiState: ContactsListUiState by mutableStateOf(ContactsListUiState())
        private set
    private val datasource: ContactDatasource = ContactDatasource.instance

    init {
        loadContacts()
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
        val newMap: MutableMap<String, List<Contact>> = mutableMapOf()
        uiState.contacts.keys.forEach { key ->
            val contactsOfKey: List<Contact> = uiState.contacts[key]!!
            val newContacts = contactsOfKey.map { contact ->
                if (contact.id == pressedContact.id) {
                    contact.copy(isFavorite = !contact.isFavorite)
                } else {
                    contact
                }
            }
            newMap[key] = newContacts
        }
        uiState = uiState.copy(
            contacts = newMap.toMap()
        )
    }
}