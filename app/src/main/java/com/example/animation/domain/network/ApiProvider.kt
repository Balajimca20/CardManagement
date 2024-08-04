package com.example.animation.domain.network


import com.example.animation.data.CardDetailsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



object ApiProvider : KoinComponent {

   /* private val properties = loadProperties("gradle.properties")
    fun getProperty(key: String): String {
        return properties.getProperty(key)
    }*/
    private val onlineInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60
        return@Interceptor response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    private fun loggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor())
            addNetworkInterceptor(onlineInterceptor)
            addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/json")
                builder.addHeader("accept", "application/json")
               // builder.addHeader("Authorization", "Bearer ${Constants.BASE_ACCESS_TOKEN}")
                return@addInterceptor chain.proceed(builder.build())
            }
        }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://41030134-c95f-4327-bbc1-a53802f4ba7a.mock.pstmn.io/")
        addConverterFactory(GsonConverterFactory.create())
        client(httpClient())
    }.build()

    val client: CardDetailsService by lazy { retrofit.create(CardDetailsService::class.java) }
}

