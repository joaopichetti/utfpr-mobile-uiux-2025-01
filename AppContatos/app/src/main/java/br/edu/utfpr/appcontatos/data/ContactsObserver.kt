package br.edu.utfpr.appcontatos.data

interface ContactsObserver {
    fun onUpdate(updatedContacts: List<Contact>)
}