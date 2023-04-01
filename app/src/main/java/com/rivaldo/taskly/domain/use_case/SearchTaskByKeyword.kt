package com.rivaldo.taskly.domain.use_case

import com.rivaldo.taskly.data.repository.interfaces.TaskRepository

class SearchTaskByKeyword(val repository: TaskRepository) {
    operator fun invoke(keyword: String) = repository.searchTaskByKeyword(keyword)
}