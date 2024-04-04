package com.meowbynykaa.app

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowbynykaa.app.data.Cat
import com.meowbynykaa.app.data.CatsAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CatsAPI
): ViewModel() {

    private val _state = mutableStateOf(CatState())
    val state: State<CatState> = _state

    init {
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                _state.value = _state.value.copy(
                    catList = api.getCats(),
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message.toString())
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    data class CatState(
        val catList: List<Cat>? = null,
        val isLoading: Boolean = false
    )
}