package com.ecodeli.app.storage

import com.ecodeli.app.network.ApiService
import com.ecodeli.app.network.RetrofitInstance
import com.ecodeli.app.model.TransportAnnonceResponse
import com.ecodeli.app.model.PrestationAnnonceResponse
import okhttp3.ResponseBody
import retrofit2.Response

class AnnonceRepository {
    private val apiService: ApiService = RetrofitInstance.api

    suspend fun getTransportAnnonces(userId: Int): Response<TransportAnnonceResponse> {
        return apiService.getTransportAnnonces(userId)
    }

    suspend fun getPrestationAnnonces(userId: Int): Response<PrestationAnnonceResponse> {
        return apiService.getPrestationAnnonces(userId)
    }

    suspend fun validerPrestation(id: Int, userId: Int): Response<ResponseBody> {
        return apiService.validerPrestation(id, userId)
    }




}