package com.rivaldo.taskly.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.taskly.domain.model.TaskModel
import com.rivaldo.taskly.domain.use_case.DeleteTask
import com.rivaldo.taskly.domain.use_case.GetTaskById
import com.rivaldo.taskly.domain.use_case.MarkCompleteTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    val getTaskById: GetTaskById,
    val markCompleteTask: MarkCompleteTask,
    val deleteTask: DeleteTask
) : ViewModel() {
    private var _uiState = MutableStateFlow(DetailUIState(isLoading = true))
    val uiState: StateFlow<DetailUIState> = _uiState

    fun initialize(idTask: String) {
        viewModelScope.launch {
            getTaskById(idTask)
                .catch {
                    _uiState.value = DetailUIState(
                        isError = true,
                        errorMessage = "Error"
                    )
                }
                .collect {
                    _uiState.value = DetailUIState(
                        task = it
                    )
                }
        }
    }

    fun markComplete(idTask: String) {
        viewModelScope.launch {
            markCompleteTask(idTask)
                .catch {
                    _uiState.value = DetailUIState(
                        isError = true,
                        errorMessage = "Error"
                    )
                }
                .collect {
                    _uiState.value = DetailUIState(
                        operationSuccess = it
                    )
                }
        }
    }

    fun delete(idTask: String) {
        viewModelScope.launch {
            deleteTask(idTask)
                .catch {
                    _uiState.value = DetailUIState(
                        isError = true,
                        errorMessage = "Error"
                    )
                }
                .collect {
                    _uiState.value = DetailUIState(
                        operationSuccess = it
                    )
                }
        }
    }
}

data class DetailUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val task: TaskModel? = null,
    val operationSuccess: Boolean = false
)