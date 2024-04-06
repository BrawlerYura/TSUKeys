package com.example.teamdevelopment.view.MainScreens.MyKeysScreen

import androidx.compose.ui.hapticfeedback.HapticFeedback
import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.domain.entities.data.RequestModel
import com.example.teamdevelopment.domain.entities.enums.RoleEnum
import com.example.teamdevelopment.domain.entities.enums.StatusEnum
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenContract
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState

class MyKeysScreenContract {
    sealed class Event : ViewEvent {
        object Back : Event()
        object OnSelectKeyNavigationRequested : Event()
        object LoadMyKeys : Event()
        class ReturnKeyEvent(val id: String) : Event()
        object LoadMyKeysEvent : Event()
        class CreateQR(val keyId: String, val pass: String) : Event()
        object LoadMyRequests : Event()
    }

    data class State(
        val isLoaded: Boolean,
        val isError: Boolean,
        val myKeysList: List<KeyModel>,
        val myRequestsList: List<RequestModel>,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
            object ToMainScreen : Navigation()
        }
    }
}