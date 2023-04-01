package com.rivaldo.taskly.di

import com.rivaldo.taskly.data.repository.dummy.TaskRepositoryDummy
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.use_case.*
import com.rivaldo.taskly.ui.add.AddViewModel
import com.rivaldo.taskly.ui.detail.DetailViewModel
import com.rivaldo.taskly.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryDummyModule = module {
    single<TaskRepository> { TaskRepositoryDummy() }
}

val useCaseModule = module {
    factory { GetAllTaskByStatus(get()) }
    factory { GetTaskById(get()) }
    factory { SearchTaskByKeyword(get()) }
    factory { MarkCompleteTask(get()) }
    factory { DeleteTask(get()) }
    factory { AddTask(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get(), get()) }
    viewModel { AddViewModel(get()) }
}