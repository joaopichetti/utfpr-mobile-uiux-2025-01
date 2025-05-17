package br.edu.utfpr.appcontatos.ui.contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.appcontatos.data.Contact
import br.edu.utfpr.appcontatos.data.groupByInitial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ContactsListViewModel : ViewModel() {
    var uiState: ContactsListUiState by mutableStateOf(ContactsListUiState())
        private set

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
                contacts = generateContacts().groupByInitial(),
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

fun generateContacts(): List<Contact> {
    val firstNames = listOf(
        "João", "José", "Everton", "Marcos", "André", "Anderson", "Antônio",
        "Laura", "Ana", "Maria", "Joaquina", "Suelen", "Samuel"
    )
    val lastNames = listOf(
        "Do Carmo", "Oliveira", "Dos Santos", "Da Silva", "Brasil", "Pichetti",
        "Cordeiro", "Silveira", "Andrades", "Cardoso", "Souza"
    )
    val contacts: MutableList<Contact> = mutableListOf()
    for (i in 0..19) {
        var generatedNewContact = false
        while (!generatedNewContact) {
            val firstNameIndex = Random.nextInt(firstNames.size)
            val lastNameIndex = Random.nextInt(lastNames.size)
            val newContact = Contact(
                id = i + 1,
                firstName = firstNames[firstNameIndex],
                lastName = lastNames[lastNameIndex]
            )
            if (!contacts.any { it.fullName == newContact.fullName }) {
                contacts.add(newContact)
                generatedNewContact = true
            }
        }
    }
    return contacts
}