package com.example.teamdevelopment.view.MainScreens.selectKeyScreen

import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.domain.useCases.app.RequestKeyUseCase
import com.example.teamdevelopment.domain.useCases.key.KeyGetKeysList
import com.example.teamdevelopment.domain.useCases.user.UserGetProfileUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.AppRepository
import com.example.teamdevelopment.network.repository.KeyRepository
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.authScreens.LoginScreen.LoginContract
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SelectKeyViewModel(
) : BaseViewModel<SelectKeyContract.Event, SelectKeyContract.State, SelectKeyContract.Effect>() {

    override fun setInitialState() = SelectKeyContract.State(
        isError = false,
        isLoading = false,
        date = LocalDate.now(),
        couple = 0,
        textValue = "",
        keyList = listOf(),
        keyListWithFilter = listOf(),
        profile = null
    )

    override fun handleEvents(event: SelectKeyContract.Event) {
        when (event) {
            is SelectKeyContract.Event.LoadDateAndCouple -> loadDateAndCouple(date = event.date, couple = event.couple)
            is SelectKeyContract.Event.SaveTextValue -> saveTextValue(text = event.text)
            is SelectKeyContract.Event.LoadKeysList -> loadKeysList(date = event.date, couple = event.couple)
            is SelectKeyContract.Event.RequestKey -> requestKey(keyId = event.keyId, schedule = event.schedule, date = event.date, isRepeat = event.isRepeat)
            is SelectKeyContract.Event.ToMain -> setEffect { SelectKeyContract.Effect.Navigation.ToMain }
            is SelectKeyContract.Event.LoadProfile -> loadProfile()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            UserGetProfileUseCase(UserRepository(Network.getUserApi())).invoke().onSuccess {
                setState { copy(profile = it) }
            }.onFailure {

            }
        }
    }

    private fun requestKey(keyId: String, schedule: Int, date: String, isRepeat: Boolean) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = LocalDate.parse(date, dateFormatter)
        viewModelScope.launch {
            RequestKeyUseCase(AppRepository(Network.getAppApi())).invoke(
                keyId = keyId,
                date = formattedDate.toString(),
                schedule = when {
                    schedule == 0 -> "First"
                    schedule == 1 -> "Second"
                    schedule == 2 -> "Third"
                    schedule == 3 -> "Fourth"
                    schedule == 4 -> "Fifth"
                    schedule == 5 -> "Sixth"
                    schedule == 6 -> "Seventh"
                    else -> "Seventh"
                },
                isRepeat = isRepeat
            ).onSuccess {
                MainScope().launch {
                    setEffect { SelectKeyContract.Effect.Navigation.ToMain }
                }
            }.onFailure {

            }
        }
    }

    private fun loadKeysList(date: String, couple: Int) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = LocalDate.parse(date, dateFormatter)
        viewModelScope.launch {
            val result = KeyGetKeysList(KeyRepository(Network.getKeyApi())).invoke(
                date = formattedDate.toString(),
                cell =  when {
                    couple == 0 -> "First"
                    couple == 1 -> "Second"
                    couple == 2 -> "Third"
                    couple == 3 -> "Fourth"
                    couple == 4 -> "Fifth"
                    couple == 5 -> "Sixth"
                    couple == 6 -> "Seventh"
                    else -> "Seventh"
                }
            ).onSuccess {
                setState {
                    copy(
                        keyList = it,
                        keyListWithFilter = it,
                    )
                }
            }.onFailure {

            }
        }
    }

    private fun loadDateAndCouple(date: String, couple: String) {
        setState { copy(date = LocalDate.parse(date), couple = couple.toInt()) }
    }

    private fun saveTextValue(text: String) {
        setState {
            copy(textValue = text)
        }

        setState {
            copy(keyListWithFilter = state.value.keyList.filter { key->
                key.number.toString().contains(textValue, ignoreCase = true)
            })
        }
    }
}