package com.example.teamdevelopment.domain.useCases

import android.util.Log
import com.example.teamdevelopment.domain.entities.data.NewProfileModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.UserProfile

class ValidationUseCase {
    fun checkIfLoginValid(text: String): Boolean {
        return text.isNotEmpty()
    }

    fun checkIfNameValid(text: String): Boolean {
        return text.isNotEmpty()
    }

    fun checkIfPasswordValid(text: String): Boolean {
        return text.isNotEmpty()
    }

    fun checkIfBirthDateValid(text: String): Boolean {
        return Regex("""((([1-2][0-9]|3[0-1]|0[1-9])\.(0[13-9]|1[0-2])|(0[13-9]|1[0-2])\.([1-2][0-9]|3[0-1]|0[1-9]))\.\d{4})""").matches(
            text
        )
    }

    fun checkIfEmailValid(text: String): Boolean {
        return Regex("""^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+${'$'}""").matches(text)
    }

    fun checkIfFacNumberValid(text: String): Boolean {
        return Regex("^\\d+").matches(text)
    }

    fun checkIfPasswordEqualsRepeatedPassword(password: String, repeatedPassword: String): Boolean {
        return password == repeatedPassword
    }

    fun checkIfProfileModelMatches(
        newProfileModel: ProfileModel,
        profileModel: ProfileModel
    ): Boolean {
        return newProfileModel.birthDate == profileModel.birthDate &&
                newProfileModel.name == profileModel.name &&
                newProfileModel.email == profileModel.email
    }
}