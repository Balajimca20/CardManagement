package com.example.animation.data.repository


import android.util.Log
import com.example.animation.apiutils.BaseRepo
import com.example.animation.apiutils.Resource
import com.example.animation.data.CardDetailsService
import com.example.animation.data.dataBase.dao.LocalDataSource
import com.example.animation.data.model.CardDetailResponse
import com.example.animation.data.model.CardListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CardDetailsRepository(
    private val studentDetailsService: CardDetailsService,
    private val localDataSource: LocalDataSource,
) : BaseRepo() {
    suspend fun getCardDetails(
    ): Flow<Resource<CardDetailResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(
                safeApiCall {
                    studentDetailsService.getCards()
                }
            )
        }
    }

    val getCardItem: Flow<List<CardListItem>> = localDataSource.getCardItem()

    suspend fun insertCardDetail(
        cardInfo: CardListItem,
    ) {
            localDataSource.insertCardData(cardInfo=cardInfo)

    }
}