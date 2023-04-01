package com.rivaldo.taskly.di

import com.rivaldo.taskly.data.repository.dummy.TaskRepositoryDummy
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.use_case.GetAllTaskByStatus
import com.rivaldo.taskly.domain.use_case.GetTaskById
import com.rivaldo.taskly.domain.use_case.SearchTaskByKeyword
import com.rivaldo.taskly.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryDummyModule = module {
    single<TaskRepository> { TaskRepositoryDummy() }
}

val useCaseModule = module {
    factory { GetAllTaskByStatus(get()) }
    factory { GetTaskById(get()) }
    factory { SearchTaskByKeyword(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}