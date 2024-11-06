package com.app.androidassesment

import androidx.room.Room
import com.app.androidassesment.network.ApiService
import com.app.androidassesment.repository.CartRepository
import com.app.androidassesment.repository.ProductRepository
import com.app.androidassesment.repository.UserRepository
import com.app.androidassesment.viewmodel.CartViewModel
import com.app.androidassesment.viewmodel.ProductViewModel
import com.app.androidassesment.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "cart_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().cartDao() }
    single { get<Retrofit>().create(ApiService::class.java) }
    single { ProductRepository(get()) }
    single { UserRepository(get()) }
    single { CartRepository(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { ProductViewModel(get()) }
}
