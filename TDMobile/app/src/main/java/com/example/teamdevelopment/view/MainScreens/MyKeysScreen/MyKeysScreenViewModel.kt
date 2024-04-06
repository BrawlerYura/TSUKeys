package com.example.teamdevelopment.view.MainScreens.MyKeysScreen

import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.enums.KeyStatusEnum
import com.example.teamdevelopment.domain.useCases.FormatDateUseCase
import com.example.teamdevelopment.domain.useCases.app.AppAllAppsUseCase
import com.example.teamdevelopment.domain.useCases.key.KeyGetKeysList
import com.example.teamdevelopment.domain.useCases.key.KeyLoadMyKeys
import com.example.teamdevelopment.domain.useCases.key.KeyReturnKeyUseCase
import com.example.teamdevelopment.domain.useCases.user.UserCreateQRUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.AppRepository
import com.example.teamdevelopment.network.repository.KeyRepository
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenContract
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MyKeysScreenViewModel(
    val formatDateUseCase: FormatDateUseCase
) : BaseViewModel<MyKeysScreenContract.Event, MyKeysScreenContract.State, MyKeysScreenContract.Effect>() {
    override fun setInitialState() = MyKeysScreenContract.State(
        isLoaded = true,
        isError = false,
        myKeysList = listOf(),
        myRequestsList = listOf()
    )

    override fun handleEvents(event: MyKeysScreenContract.Event) {
        when (event) {
            is MyKeysScreenContract.Event.Back -> setEffect { MyKeysScreenContract.Effect.Navigation.Back }
            is MyKeysScreenContract.Event.OnSelectKeyNavigationRequested -> setEffect { MyKeysScreenContract.Effect.Navigation.ToMainScreen }
            is MyKeysScreenContract.Event.LoadMyKeys -> loadMyKeys()
            is MyKeysScreenContract.Event.ReturnKeyEvent -> returnKey(id = event.id)
            is MyKeysScreenContract.Event.LoadMyKeysEvent -> loadMyKeys()
            is MyKeysScreenContract.Event.CreateQR -> createQR(keyId = event.keyId, pass = event.pass)
            is MyKeysScreenContract.Event.LoadMyRequests -> loadMyRequests()
        }
    }

    private fun loadMyRequests() {
        viewModelScope.launch {
            AppAllAppsUseCase(AppRepository(Network.getAppApi())).invoke().onSuccess {
                for(request in it) {
                    request.date = formatDateUseCase.formatDateFromApi(request.date)
                }
                setState { copy(myRequestsList = it) }
            }.onFailure {

            }
        }
    }

    private fun createQR(keyId: String, pass: String) {
        viewModelScope.launch {
            UserCreateQRUseCase(UserRepository(Network.getUserApi())).invoke(keyId, pass).onSuccess {

            }.onFailure {

            }
        }
    }

    private fun returnKey(id: String) {
        viewModelScope.launch {
            KeyReturnKeyUseCase(KeyRepository(Network.getKeyApi())).invoke(id).onSuccess {
                val index = state.value.myKeysList.indexOfFirst { it.id == id }
                val filteredItems =  state.value.myKeysList.filterIndexed { i, _ -> i != index }

                setState {
                    copy(
                        myKeysList = filteredItems
                    )
                }
            }.onFailure {

            }
        }
    }

    private fun loadMyKeys() {
        viewModelScope.launch {
            KeyLoadMyKeys(KeyRepository(Network.getKeyApi())).invoke().onSuccess {
                setState {
                    copy(
                        myKeysList = it
                    )
                }
            }.onFailure {

            }
        }
    }
}