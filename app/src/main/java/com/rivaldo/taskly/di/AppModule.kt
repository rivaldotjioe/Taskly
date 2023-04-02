package com.rivaldo.taskly.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.rivaldo.taskly.data.local.database.LocalDataSource
import com.rivaldo.taskly.data.local.database.TaskDao
import com.rivaldo.taskly.data.local.database.TaskRoomDatabase
import com.rivaldo.taskly.data.remote.api.ApiService
import com.rivaldo.taskly.data.remote.api.Constant
import com.rivaldo.taskly.data.remote.api.RemoteDataSource
import com.rivaldo.taskly.data.repository.TaskRepositoryImpl
import com.rivaldo.taskly.data.repository.dummy.TaskRepositoryDummy
import com.rivaldo.taskly.data.repository.interfaces.TaskRepository
import com.rivaldo.taskly.domain.use_case.*
import com.rivaldo.taskly.ui.add.AddViewModel
import com.rivaldo.taskly.ui.detail.DetailViewModel
import com.rivaldo.taskly.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
    single { RemoteDataSource(get()) }

}
val repositoryDummyModule = module {
    single<TaskRepository> { TaskRepositoryDummy() }
}
val repositoryModule = module {

    single<TaskRepository> { TaskRepositoryImpl(get(), get()) }
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

val localDataModule = module {

    factory { get<TaskRoomDatabase>().taskDao() }
    single {
        TaskRoomDatabase.buildDatabase(androidContext())
    }
    single { LocalDataSource(get()) }
}