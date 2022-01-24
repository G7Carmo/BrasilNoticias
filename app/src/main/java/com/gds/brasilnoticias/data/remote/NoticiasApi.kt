package com.gds.brasilnoticias.data.remote

import com.gds.brasilnoticias.data.local.model.RespostaDaNoticia
import com.gds.brasilnoticias.util.Constantes.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticiasApi {

    @GET("/v2/top-headlines")
    suspend fun getPrincipaisNoticias(
        @Query("country")
        counttryCode: String = "br",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<RespostaDaNoticia>

    @GET("/v2/everything")
    suspend fun pesquisarNoticias(
        @Query("q")
        pesquisarQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<RespostaDaNoticia>
}