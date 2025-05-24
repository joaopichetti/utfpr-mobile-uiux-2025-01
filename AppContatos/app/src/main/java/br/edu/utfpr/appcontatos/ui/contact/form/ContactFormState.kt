package br.edu.utfpr.appcontatos.ui.contact.form

import br.edu.utfpr.appcontatos.data.Contact

data class FormField(
    val value: String = "",
    val errorMessage: String = ""
) {
    val hasError get(): Boolean = errorMessage.isNotBlank()
    val isValid get(): Boolean = !hasError
}

data class FormState(
    val firstName: FormField = FormField(),
    val lastName: FormField = FormField(),
    val phone: FormField = FormField(),
    val email: FormField = FormField(),
    val isFavorite: FormField = FormField(),
    val birthDate: FormField = FormField(),
    val type: FormField = FormField(),
    val salary: FormField = FormField()
) {
    val isValid get(): Boolean = firstName.isValid &&
            lastName.isValid &&
            phone.isValid &&
            email.isValid &&
            isFavorite.isValid &&
            birthDate.isValid &&
            type.isValid &&
            salary.isValid
}

data class ContactFormState(
    val isLoading: Boolean = false,
    val contact: Contact = Contact(),
    val hasErrorLoading: Boolean = false,
    val isSaving: Boolean = false,
    val hasErrorSaving: Boolean = false,
    val contactSaved: Boolean = false,
    val formState: FormState = FormState()
) {
    val isNewContact get(): Boolean = contact.id <= 0
}