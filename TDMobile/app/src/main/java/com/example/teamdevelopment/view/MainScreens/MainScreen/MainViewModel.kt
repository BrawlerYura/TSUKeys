package com.example.teamdevelopment.view.MainScreens.MainScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.teamdevelopment.view.authScreens.SplashScreen.SplashContract
import com.example.teamdevelopment.domain.entities.data.LessonCard
import com.example.teamdevelopment.domain.useCases.user.UserGetProfileUseCase
import com.example.teamdevelopment.network.Network
import com.example.teamdevelopment.network.repository.UserRepository
import com.example.teamdevelopment.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    init {
        changeLessonCards()
    }

    override fun setInitialState() = MainContract.State(
        isLoading = true,
        isError = false,
        lessonCards = listOf(
            LessonCard("1 пара · 8:45-10:20", isSelected = false, isEnabled = true, index = 0),
            LessonCard("2 пара · 10:35-12:10", isSelected = false, isEnabled = true, index = 1),
            LessonCard("3 пара · 12:25-14:00", isSelected = false, isEnabled = true, index = 2),
            LessonCard("4 пара · 14:45-16:20", isSelected = false, isEnabled = true, index = 3),
            LessonCard("5 пара · 16:35-18:10", isSelected = false, isEnabled = true, index = 4),
            LessonCard("6 пара · 18:25-20:00", isSelected = false, isEnabled = true, index = 5)
        ),
        selectedDate = LocalDate.now(),
        selectedCouple = -1,
        profile = null,
    )

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.SaveDateEvent -> saveDate(inputDate = event.date)
            is MainContract.Event.SaveCoupleEvent -> saveCouple(index = event.index)
            is MainContract.Event.NavigateToProfile -> setEffect { MainContract.Effect.Navigation.ToProfile }
            is MainContract.Event.NavigateToSelectKey -> setEffect { MainContract.Effect.Navigation.ToSelectKey(state.value.selectedDate, state.value.selectedCouple) }
            is MainContract.Event.NavigateToMyKeys -> setEffect { MainContract.Effect.Navigation.ToMyKeys() }
            is MainContract.Event.LoadProfile -> loadProfile()
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

    private fun saveDate(inputDate: LocalDate) {
        setState {
            copy(selectedDate = inputDate)
        }
        changeLessonCards()
    }

    private fun saveCouple(index: Int) {
        setState {
            copy(selectedCouple = index)
        }
        for(i in 0..5) {
            state.value.lessonCards[i].isSelected = false
        }
        state.value.lessonCards[index].isSelected = true
    }

    private fun changeLessonCards() {
        val currentTime = LocalTime.now()
        if (state.value.selectedDate == LocalDate.now()) {
            when {
                currentTime < LocalTime.parse("10:20") -> {
                    state.value.lessonCards[0].isEnabled = true
                    state.value.lessonCards[0].isSelected = true
                    state.value.lessonCards[1].isEnabled = true
                    state.value.lessonCards[1].isSelected = false
                    state.value.lessonCards[2].isEnabled = true
                    state.value.lessonCards[2].isSelected = false
                    state.value.lessonCards[3].isEnabled = true
                    state.value.lessonCards[3].isSelected = false
                    state.value.lessonCards[4].isEnabled = true
                    state.value.lessonCards[4].isSelected = false
                    state.value.lessonCards[5].isEnabled = true
                    state.value.lessonCards[5].isSelected = false
                    setState {
                        copy(selectedCouple = 0)
                    }
                }

                currentTime in LocalTime.parse("10:20")..LocalTime.parse("12:10") -> {
                    state.value.lessonCards[0].isEnabled = false
                    state.value.lessonCards[0].isSelected = false
                    state.value.lessonCards[1].isEnabled = true
                    state.value.lessonCards[1].isSelected = true
                    state.value.lessonCards[2].isEnabled = true
                    state.value.lessonCards[2].isSelected = false
                    state.value.lessonCards[3].isEnabled = true
                    state.value.lessonCards[3].isSelected = false
                    state.value.lessonCards[4].isEnabled = true
                    state.value.lessonCards[4].isSelected = false
                    state.value.lessonCards[5].isEnabled =  true
                    state.value.lessonCards[5].isSelected = false
                    setState {
                        copy(selectedCouple = 1)
                    }
                }

                currentTime in LocalTime.parse("12:10")..LocalTime.parse("14:00") -> {
                    state.value.lessonCards[0].isEnabled = false
                    state.value.lessonCards[0].isSelected = false
                    state.value.lessonCards[1].isEnabled = false
                    state.value.lessonCards[1].isSelected = false
                    state.value.lessonCards[2].isEnabled = true
                    state.value.lessonCards[2].isSelected = true
                    state.value.lessonCards[3].isEnabled = true
                    state.value.lessonCards[3].isSelected = false
                    state.value.lessonCards[4].isEnabled = true
                    state.value.lessonCards[4].isSelected = false
                    state.value.lessonCards[5].isEnabled =  true
                    state.value.lessonCards[5].isSelected = false
                    setState {
                        copy(selectedCouple = 2)
                    }
                }

                currentTime in LocalTime.parse("14:00")..LocalTime.parse("16:20") -> {
                    state.value.lessonCards[0].isEnabled = false
                    state.value.lessonCards[0].isSelected = false
                    state.value.lessonCards[1].isEnabled = false
                    state.value.lessonCards[1].isSelected = false
                    state.value.lessonCards[2].isEnabled = false
                    state.value.lessonCards[2].isSelected = false
                    state.value.lessonCards[3].isEnabled = true
                    state.value.lessonCards[3].isSelected =true
                    state.value.lessonCards[4].isEnabled = true
                    state.value.lessonCards[4].isSelected = false
                    state.value.lessonCards[5].isEnabled =  true
                    state.value.lessonCards[5].isSelected = false
                    setState {
                        copy(selectedCouple = 3)
                    }
                }

                currentTime in LocalTime.parse("16:20")..LocalTime.parse("18:10") -> {
                    state.value.lessonCards[0].isEnabled = false
                    state.value.lessonCards[0].isSelected = false
                    state.value.lessonCards[1].isEnabled = false
                    state.value.lessonCards[1].isSelected = false
                    state.value.lessonCards[2].isEnabled = false
                    state.value.lessonCards[2].isSelected = false
                    state.value.lessonCards[3].isEnabled = false
                    state.value.lessonCards[3].isSelected = false
                    state.value.lessonCards[4].isEnabled = true
                    state.value.lessonCards[4].isSelected = true
                    state.value.lessonCards[5].isEnabled =  true
                    state.value.lessonCards[5].isSelected = false
                    setState {
                        copy(selectedCouple = 4)
                    }
                }

                currentTime in LocalTime.parse("18:10")..LocalTime.parse("20:00") -> {
                    state.value.lessonCards[0].isEnabled = false
                    state.value.lessonCards[0].isSelected = false
                    state.value.lessonCards[1].isEnabled = false
                    state.value.lessonCards[1].isSelected = false
                    state.value.lessonCards[2].isEnabled = false
                    state.value.lessonCards[2].isSelected = false
                    state.value.lessonCards[3].isEnabled = false
                    state.value.lessonCards[3].isSelected = false
                    state.value.lessonCards[4].isEnabled = false
                    state.value.lessonCards[4].isSelected = false
                    state.value.lessonCards[5].isEnabled =  true
                    state.value.lessonCards[5].isSelected = true
                    setState {
                        copy(selectedCouple = 5)
                    }
                }
                else -> {
                    state.value.lessonCards[0].isEnabled = false
                    state.value.lessonCards[0].isSelected = false
                    state.value.lessonCards[1].isEnabled = false
                    state.value.lessonCards[1].isSelected = false
                    state.value.lessonCards[2].isEnabled = false
                    state.value.lessonCards[2].isSelected = false
                    state.value.lessonCards[3].isEnabled = false
                    state.value.lessonCards[3].isSelected = false
                    state.value.lessonCards[4].isEnabled = false
                    state.value.lessonCards[4].isSelected = false
                    state.value.lessonCards[5].isEnabled = false
                    state.value.lessonCards[5].isSelected = false
                    setState {
                        copy(selectedCouple = -1)
                    }
                }
            }
        }
        if(state.value.selectedDate < LocalDate.now()) {
            state.value.lessonCards[0].isEnabled = false
            state.value.lessonCards[0].isSelected = false
            state.value.lessonCards[1].isEnabled = false
            state.value.lessonCards[1].isSelected = false
            state.value.lessonCards[2].isEnabled = false
            state.value.lessonCards[2].isSelected = false
            state.value.lessonCards[3].isEnabled = false
            state.value.lessonCards[3].isSelected = false
            state.value.lessonCards[4].isEnabled = false
            state.value.lessonCards[4].isSelected = false
            state.value.lessonCards[5].isEnabled = false
            state.value.lessonCards[5].isSelected = false
            setState {
                copy(selectedCouple = -1)
            }
        }
        if (state.value.selectedDate > LocalDate.now()) {
            state.value.lessonCards[0].isEnabled = true
            state.value.lessonCards[0].isSelected = true
            state.value.lessonCards[1].isEnabled = true
            state.value.lessonCards[1].isSelected = false
            state.value.lessonCards[2].isEnabled = true
            state.value.lessonCards[2].isSelected = false
            state.value.lessonCards[3].isEnabled = true
            state.value.lessonCards[3].isSelected = false
            state.value.lessonCards[4].isEnabled = true
            state.value.lessonCards[4].isSelected = false
            state.value.lessonCards[5].isEnabled = true
            state.value.lessonCards[5].isSelected = false
            setState {
                copy(selectedCouple = 0)
            }
        }
    }


}