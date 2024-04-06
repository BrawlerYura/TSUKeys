package com.example.mobile_moviescatalog2023.View.AuthScreens.RegistrationScreen

import com.example.teamdevelopment.domain.useCases.FormatDateUseCase
import com.example.teamdevelopment.domain.useCases.ValidationUseCase
import com.example.teamdevelopment.view.authScreens.RegistrationScreen.RegistrationContract
import com.example.teamdevelopment.view.base.BaseViewModel

class RegistrationViewModel(
    private val formatDateUseCase: FormatDateUseCase,
    private val validationUseCase: ValidationUseCase
) : BaseViewModel<RegistrationContract.Event, RegistrationContract.State, RegistrationContract.Effect>() {
    private fun saveName(name: String) {
        setState { copy(name = name) }
        if (validationUseCase.checkIfNameValid(state.value.name)) {
            setState {
                copy(isNameValid = true)
            }
        } else {
            setState {
                copy(isNameValid = false)
            }
        }
    }

    private fun saveSurname(surname: String) {
        setState { copy(surname = surname) }
        if (validationUseCase.checkIfNameValid(state.value.surname)) {
            setState {
                copy(isSurnameValid = true)
            }
        } else {
            setState {
                copy(isSurnameValid = false)
            }
        }
    }

    private fun savePatronymic(patronymic: String) {
        setState { copy(patronymic = patronymic) }
        if (validationUseCase.checkIfFacNumberValid(state.value.patronymic)) {
            setState {
                copy(isPatronymicValid = true)
            }
        } else {
            setState {
                copy(isPatronymicValid = false)
            }
        }
    }

    private fun saveGender(gender: Int) {
        setState { copy(gender = gender) }
    }

    private fun saveEmail(email: String) {
        setState { copy(email = email) }
        if (validationUseCase.checkIfEmailValid(state.value.email)) {
            setState {
                copy(isEmailValid = true)
            }
        } else {
            setState {
                copy(isEmailValid = false)
            }
        }
    }

    private fun saveBirthDate(birthDate: String) {
        setState {
            copy(
                birthDate = birthDate,
                apiBirthDate = try {
                    formatDateUseCase.formatDateToApi(birthDate)
                } catch (e: Throwable) {
                    ""
                }
            )
        }
        if (validationUseCase.checkIfBirthDateValid(state.value.birthDate)) {
            setState {
                copy(isBirthDateValid = true)
            }
        } else {
            setState {
                copy(isBirthDateValid = false)
            }
        }
    }

    override fun setInitialState() = RegistrationContract.State(
        name = "",
        surname = "",
        patronymic = "",
        gender = 0,
        email = "",
        birthDate = "",
        apiBirthDate = "",
        isNameValid = null,
        isSurnameValid = null,
        isPatronymicValid = null,
        isEmailValid = null,
        isBirthDateValid = null
    )

    override fun handleEvents(event: RegistrationContract.Event) {
        when (event) {
            is RegistrationContract.Event.SaveNameEvent -> saveName(event.name)
            is RegistrationContract.Event.SaveSurnameEvent -> saveSurname(event.surname)
            is RegistrationContract.Event.SavePatronymicEvent -> savePatronymic(event.patronymic)
            is RegistrationContract.Event.SaveGenderEvent -> saveGender(event.gender)
            is RegistrationContract.Event.SaveEmailEvent -> saveEmail(event.email)
            is RegistrationContract.Event.SaveBirthDateEvent -> saveBirthDate(event.birthDate)
            is RegistrationContract.Event.SaveBirthDateWithFormatEvent -> saveBirthDate(
                formatDateUseCase.formatDateToTextField(event.birthDate)
            )
        }
    }
}