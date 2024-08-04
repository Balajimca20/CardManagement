package com.example.animation.apiutils

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    Resource.Success(data = response.body())
                } else {
                    val errorResponse: CustomErrorResponse? = convertErrorBody(response.errorBody())
                    Resource.Error(message = errorResponse?.message ?: "Something went wrong")
                }
            } catch (e: HttpException) {
                Resource.Error(message = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(message = "Something went wrong")
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): CustomErrorResponse? {
        return try {
            errorBody?.source()?.let {
                Gson().fromJson(it.buffer.readUtf8(), CustomErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }
}