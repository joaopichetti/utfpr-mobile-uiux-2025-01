package br.edu.utfpr.appcontatos.ui.contact.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.appcontatos.data.ContactDatasource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ContactDetailsViewModel : ViewModel() {
    var uiState: ContactDetailsUiState by mutableStateOf(ContactDetailsUiState())
        private set
    private val datasource: ContactDatasource = ContactDatasource.instance
    private val contactId: Int = 1

    init {
        loadContact()
    }

    fun loadContact() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )
        viewModelScope.launch {
            delay(2000)
            val hasError = Random.nextBoolean()
            val contact = datasource.findById(contactId)
            uiState = if (hasError || contact == null) {
                uiState.copy(
                    isLoading = false,
                    hasErrorLoading = true
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    contact = contact
                )
            }
        }
    }
}