package com.example.mobile_moviescatalog2023.View.AuthScreens.SplashScreen

import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.domain.useCases.user.UserGetProfileUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.authScreens.SplashScreen.SplashContract
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class SplashScreenViewModel(
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    init {
        getToken()
    }

    override fun setInitialState() = SplashContract.State(
        isError = false,
        isNetworkError = false
    )

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            is SplashContract.Event.GetToken -> getToken()
            is SplashContract.Event.OnTokenLoadedSuccess -> setEffect { SplashContract.Effect.Navigation.ToMain }
            is SplashContract.Event.OnTokenLoadedFailed -> setEffect { SplashContract.Effect.Navigation.ToIntroducingScreen }
        }
    }

    private fun getToken() {
//        setEffect { SplashContract.Effect.Navigation.ToIntroducingScreen }

        viewModelScope.launch(Dispatchers.IO) {
            UserGetProfileUseCase(UserRepository(Network.getUserApi())).invoke().onSuccess {
                    setState { copy(isError = false, isNetworkError = false) }
//                    Network.userId = it.id
                    MainScope().launch {
                        setEffect { SplashContract.Effect.Navigation.ToMain }
                    }
                }.onFailure {
                    setEffect { SplashContract.Effect.Navigation.ToIntroducingScreen }
            }
        }
    }
}