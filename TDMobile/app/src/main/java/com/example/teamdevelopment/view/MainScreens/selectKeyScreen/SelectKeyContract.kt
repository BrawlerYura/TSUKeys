package com.example.teamdevelopment.view.MainScreens.selectKeyScreen

import com.example.teamdevelopment.domain.entities.data.KeyModel
import com.example.teamdevelopment.domain.entities.data.LessonCard
import com.example.teamdevelopment.domain.entities.data.ProfileModel
import com.example.teamdevelopment.view.MainScreens.MainScreen.MainContract
import com.example.teamdevelopment.view.MainScreens.ProfileScreen.ProfileScreenContract
import com.example.teamdevelopment.view.base.ViewEvent
import com.example.teamdevelopment.view.base.ViewSideEffect
import com.example.teamdevelopment.view.base.ViewState
import java.time.LocalDate

class SelectKeyContract {
    sealed class Event : ViewEvent {
        class LoadDateAndCouple(val date: String, val couple: String) : Event()
        class SaveTextValue(val text: String) : Event()
        class LoadKeysList(val date: String, val couple: Int) : Event()
        class RequestKey(val keyId: String, val schedule: Int, val date: String, val isRepeat: Boolean) : Event()
        object ToMain : Event()
        object LoadProfile : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isError: Boolean,
        val date: LocalDate,
        val couple: Int,
        val textValue: String,
        val keyListWithFilter: List<KeyModel>,
        val keyList: List<KeyModel>,
        val profile: ProfileModel?
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToMain : Navigation()
        }
    }
}