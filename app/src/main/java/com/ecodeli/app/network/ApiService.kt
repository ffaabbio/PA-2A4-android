package com.ecodeli.app.network

import com.ecodeli.app.model.LoginRequest
import com.ecodeli.app.model.LoginResponse
import com.ecodeli.app.model.TransportAnnonceResponse
import com.ecodeli.app.model.PrestationAnnonceResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/logout")
    suspend fun logout(): Response<LoginResponse>

    @GET("api/annonces/transport")
    suspend fun getTransportAnnonces(@Query("user_id") userId: Int): Response<TransportAnnonceResponse>

    @GET("api/annonces/prestation")
    suspend fun getPrestationAnnonces(@Query("user_id") userId: Int): Response<PrestationAnnonceResponse>

    @POST("api/annonces/prestation/{id}/valider")
    suspend fun validerPrestation(@Path("id") id: Int, @Query("user_id") userId: Int): Response<ResponseBody>

    @POST("api/annonces/transport/{id}/valider")
    suspend fun validerLivraison(@Path("id") id: Int, @Query("user_id") userId: Int): Response<ResponseBody>



}
