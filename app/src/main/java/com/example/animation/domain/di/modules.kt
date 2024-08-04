package com.example.animation.domain.di


import androidx.room.Room
import com.example.animation.data.dataBase.CardInfoDatabase
import com.example.animation.data.repository.CardDetailsRepository
import com.example.animation.domain.network.ApiProvider
import com.example.animation.ui.dashboard.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    fun appModules() = viewModelModules + repoModules + commonModules

    private val commonModules = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                CardInfoDatabase::class.java,
                "card_management"
            ).fallbackToDestructiveMigration().build()
        }
       single { get<CardInfoDatabase>().localDao() }
        single { ApiProvider.client }

    }

    private val repoModules = module {
        single { CardDetailsRepository(get(),get()) }
    }

    private val viewModelModules = module {
       viewModel { MainViewModel(get()) }
    }

}