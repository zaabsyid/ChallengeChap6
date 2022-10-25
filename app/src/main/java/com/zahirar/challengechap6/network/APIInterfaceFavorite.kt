package com.zahirar.challengechap6.network

import com.zahirar.challengechap6.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterfaceFavorite {
    @GET("favorite")
    fun getAllFilm() : Call<List<FavoriteResponseItem>>

    @GET("favorite/{id}")
    fun getFilmById(@Path("id") id: Int) : Call<FavoriteResponseItem>

    @POST("favorite")
    fun addDataFilm(@Body request : DataFavoriteFilm): Call<FavoriteResponseItem>

    @PUT("favorite/{id}")
    fun updateDataFilm(@Path("id") id : Int, @Body reques : DataFavoriteFilm): Call<FavoriteResponseItem>

    @DELETE("favorite/{id}")
    fun deleteFilm(@Path("id") id : Int): Call<FavoriteResponseItem>
}