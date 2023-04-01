package com.rivaldo.taskly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.taskly.domain.Resource
import com.rivaldo.taskly.domain.StatusTask
import com.rivaldo.taskly.domain.model.TaskModel
import com.rivaldo.taskly.domain.use_case.GetAllTaskByStatus
import com.rivaldo.taskly.domain.use_case.SearchTaskByKeyword
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(val getAllTaskByStatus: GetAllTaskByStatus, val searchTaskByKeyword: SearchTaskByKeyword) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch {
            val getActiveTask = async { getTaskByStatus(StatusTask.ACTIVE) }
            val getCompletedTask = async { getTaskByStatus(StatusTask.COMPLETED) }
            if (getActiveTask.await() is Resource.Success && getCompletedTask.await() is Resource.Success) {
                val listActiveTask = getActiveTask.await().data
                val listCompletedTask = getCompletedTask.await().data
                _uiState.value = HomeUIState(
                    listActiveTask = listActiveTask ?: emptyList(),
                    listCompletedTask = listCompletedTask ?: emptyList()
                )
            } else {
                _uiState.value = HomeUIState(
                    isError = true,
                    errorMessage = "Error"
                )
            }
        }

    }

    suspend fun getTaskByStatus(status: StatusTask): Resource<List<TaskModel>> {
        lateinit var result: Resource<List<TaskModel>>
        getAllTaskByStatus(status)
            .catch {
                result = Resource.Error(it.message ?: "Error", emptyList())
            }
            .collect { resource ->
                result = Resource.Success(resource)
            }

        return result
    }

    fun search(keyword: String) {
        viewModelScope.launch {
            searchTaskByKeyword(keyword)
                .catch {
                    _uiState.value = HomeUIState(
                        isError = true,
                        errorMessage = it.message ?: "Error"
                    )
                }
                .collect { resource ->
                    val listActiveTask = resource.filter { it.status == StatusTask.ACTIVE }
                    val listCompletedTask = resource.filter { it.status == StatusTask.COMPLETED }
                    _uiState.value = HomeUIState(
                        listActiveTask = listActiveTask,
                        listCompletedTask = listCompletedTask
                    )
                }
        }
    }


}

data class HomeUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val listActiveTask: List<TaskModel> = emptyList(),
    val listCompletedTask: List<TaskModel> = emptyList(),
)