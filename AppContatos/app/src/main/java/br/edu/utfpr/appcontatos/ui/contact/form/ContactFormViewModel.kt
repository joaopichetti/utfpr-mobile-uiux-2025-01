package br.edu.utfpr.appcontatos.ui.contact.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.appcontatos.data.ContactDatasource
import br.edu.utfpr.appcontatos.data.ContactTypeEnum
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random

class ContactFormViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val contactId: Int = savedStateHandle.get<String>("id")?.toIntOrNull() ?: 0
    private val datasource: ContactDatasource = ContactDatasource.instance
    var uiState: ContactFormState by mutableStateOf(ContactFormState())
        private set

    init {
        if (contactId > 0) {
            loadContact()
        }
    }

    fun loadContact() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false
        )
        viewModelScope.launch {
            delay(2000)
            val contact = datasource.findById(contactId)
            uiState = if (contact == null) {
                uiState.copy(
                    isLoading = false,
                    hasErrorLoading = true
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    contact = contact,
                    formState = FormState(
                        firstName = FormField(contact.firstName),
                        lastName = FormField(contact.lastName),
                        phone = FormField(contact.phoneNumber),
                        email = FormField(contact.email),
                        isFavorite = FormField(contact.isFavorite.toString()),
                        birthDate = FormField(contact.birthDate.toString()),
                        type = FormField(contact.type.toString()),
                        salary = FormField(contact.salary.toString())
                    )
                )
            }
        }
    }

    fun onFirstNameChanged(newFirstName: String) {
        if (uiState.formState.firstName.value != newFirstName) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    firstName = uiState.formState.firstName.copy(
                        value = newFirstName,
                        errorMessage = validateFirstName(newFirstName)
                    )
                )
            )
        }
    }

    private fun validateFirstName(firstName: String): String {
        return if (firstName.isBlank()) {
            "O nome é obrigatório"
        } else {
            ""
        }
    }

    fun onLastNameChanged(newLastName: String) {
        if (uiState.formState.lastName.value != newLastName) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    lastName = uiState.formState.lastName.copy(
                        value = newLastName
                    )
                )
            )
        }
    }

    fun onPhoneChanged(value: String) {
        val newPhone = value.replace(Regex("\\D"), "")
        if (newPhone.length <= 11 && uiState.formState.phone.value != newPhone) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    phone = uiState.formState.phone.copy(
                        value = newPhone,
                        errorMessage = validatePhone(newPhone)
                    )
                )
            )
        }
    }

    private fun validatePhone(phone: String): String {
        return if (phone.isNotBlank() && (phone.length < 10 || phone.length > 11)) {
            "Telefone inválido"
        } else {
            ""
        }
    }

    fun onEmailChanged(newEmail: String) {
        if (uiState.formState.email.value != newEmail) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    email = uiState.formState.email.copy(
                        value = newEmail,
                        errorMessage = validateEmail(newEmail)
                    )
                )
            )
        }
    }

    private fun validateEmail(email: String): String {
        return if (email.isNotBlank() && !Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matches(email)) {
            "E-mail inválido"
        } else {
            ""
        }
    }

    fun onIsFavoriteChanged(isFavorite: String) {
        if (uiState.formState.isFavorite.value != isFavorite) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    isFavorite = uiState.formState.isFavorite.copy(value = isFavorite)
                )
            )
        }
    }

    fun onBirthDateChanged(newBirthDate: String) {
        if (uiState.formState.birthDate.value != newBirthDate) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    birthDate = uiState.formState.birthDate.copy(
                        value = newBirthDate
                    )
                )
            )
        }
    }

    fun onTypeChanged(newType: String) {
        if (uiState.formState.type.value != newType) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    type = uiState.formState.type.copy(
                        value = newType
                    )
                )
            )
        }
    }

    fun onSalaryChanged(newSalary: String) {
        if (uiState.formState.salary.value != newSalary) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    salary = uiState.formState.salary.copy(
                        value = newSalary,
                        errorMessage = validateSalary(newSalary)
                    )
                )
            )
        }
    }

    private fun validateSalary(salary: String): String {
        if (salary.isNotBlank()) {
            try {
                BigDecimal(salary)
            } catch (_: NumberFormatException) {
                return "Valor inválido"
            }
        }
        return ""
    }

    private fun isValidForm(): Boolean {
        uiState = uiState.copy(
            formState = uiState.formState.copy(
                firstName = uiState.formState.firstName.copy(
                    errorMessage = validateFirstName(uiState.formState.firstName.value)
                ),
                phone = uiState.formState.phone.copy(
                    errorMessage = validatePhone(uiState.formState.phone.value)
                ),
                email = uiState.formState.email.copy(
                    errorMessage = validateEmail(uiState.formState.email.value)
                ),
                salary = uiState.formState.salary.copy(
                    errorMessage = validateSalary(uiState.formState.salary.value)
                )
            )
        )
        return uiState.formState.isValid
    }

    fun save() {
        if (uiState.isSaving || !isValidForm()) return

        uiState = uiState.copy(
            isSaving = true,
            hasErrorSaving = false
        )
        viewModelScope.launch {
            delay(2000)
            val hasError = Random.nextBoolean()
            uiState = if (!hasError) {
                val contactToSave = uiState.contact.copy(
                    firstName = uiState.formState.firstName.value,
                    lastName = uiState.formState.lastName.value,
                    phoneNumber = uiState.formState.phone.value,
                    email = uiState.formState.email.value,
                    isFavorite = uiState.formState.isFavorite.value.toBoolean(),
                    type = if (uiState.formState.type.value.isBlank()) {
                        ContactTypeEnum.PERSONAL
                    } else {
                        ContactTypeEnum.valueOf(uiState.formState.type.value)
                    },
                    birthDate = if (uiState.formState.birthDate.value.isBlank()) {
                        LocalDate.now()
                    } else {
                        LocalDate.parse(uiState.formState.birthDate.value)
                    },
                    salary = if (uiState.formState.salary.value.isBlank()) {
                        BigDecimal.ZERO
                    } else {
                        BigDecimal(uiState.formState.salary.value)
                    }
                )
                datasource.save(contactToSave)
                uiState.copy(
                    isSaving = false,
                    contactSaved = true
                )
            } else {
                uiState.copy(
                    isSaving = false,
                    hasErrorSaving = true
                )
            }
        }
    }
}