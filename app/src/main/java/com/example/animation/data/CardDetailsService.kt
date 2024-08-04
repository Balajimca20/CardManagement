package com.example.animation.data

import com.example.animation.data.model.CardDetailResponse
import retrofit2.Response
import retrofit2.http.GET

interface CardDetailsService {

    @GET("card-management/get-cards")
    suspend fun getCards(
    ): Response<CardDetailResponse>
}