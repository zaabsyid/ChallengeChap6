package com.zahirar.challengechap6.network

import com.zahirar.challengechap6.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("film")
    fun getAllFilm() : Call<List<GetFilmResponseItem>>

    @GET("film/{id}")
    fun getFilmById(@Path("id") id: Int) : Call<PostDataFilmItem>

    @POST("film")
    fun addDataFilm(@Body request : DataFilm): Call<PostDataFilm>

    @PUT("film/{id}")
    fun updateDataFilm(@Path("id") id : Int, @Body reques : DataFilm): Call<PostDataFilmItem>

    @DELETE("film/{id}")
    fun deleteFilm(@Path("id") id : Int): Call<PostDataFilmItem>

    @GET("users")
    fun getAllUser(): Call<List<GetUserResponseItem>>

    @POST("users")
    fun registerUser(@Body request : DataUser): Call<PostUserResponse>

    @GET("users/{id}")
    fun getUsersById(@Path("id") id: Int) : Call<GetUserResponseItem>

    @PUT("users/{id}")
    fun updateDataUsers(@Path("id") id : Int, @Body reques : DataUser): Call<GetUserResponseItem>
}