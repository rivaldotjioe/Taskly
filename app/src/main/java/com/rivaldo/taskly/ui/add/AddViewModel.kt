package com.rivaldo.taskly.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.taskly.domain.use_case.AddTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AddViewModel(val addTask: AddTask) : ViewModel() {
    private var _uiState = MutableStateFlow(AddUIState(isLoading = false))
    val uiState: StateFlow<AddUIState> = _uiState


    fun add(taskName: String, taskDesc: String) {
        _uiState.value = AddUIState(isLoading = true)
        viewModelScope.launch {
            addTask(taskName, taskDesc)
                .catch {
                    _uiState.value = AddUIState(
                        isError = true,
                        errorMessage = "Error"
                    )
                }
                .collect {
                    _uiState.value = AddUIState(
                        operationSuccess = it
                    )
                }
        }
    }

}

data class AddUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val operationSuccess: Boolean = false,
    val taskNameEdit: String = "",
    val taskDescEdit: String = ""
)