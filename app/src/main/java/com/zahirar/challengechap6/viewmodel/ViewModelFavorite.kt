package com.zahirar.challengechap6.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zahirar.challengechap6.model.*
import com.zahirar.challengechap6.network.APIInterfaceFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelFavorite @Inject constructor(var api : APIInterfaceFavorite): ViewModel() {
    lateinit var liveDataFilm : MutableLiveData<List<FavoriteResponseItem>>
    lateinit var LDFilmById : MutableLiveData<FavoriteResponseItem>
    lateinit var postLDFilm : MutableLiveData<FavoriteResponseItem>
    lateinit var updateFilm : MutableLiveData<FavoriteResponseItem>
    lateinit var deleteFilm : MutableLiveData<FavoriteResponseItem>

    init {
        liveDataFilm = MutableLiveData()
        LDFilmById = MutableLiveData()
        postLDFilm = MutableLiveData()
        updateFilm = MutableLiveData()
        deleteFilm = MutableLiveData()
    }

    fun getLiveDataFilem() : MutableLiveData<List<FavoriteResponseItem>> {
        return liveDataFilm
    }

    fun getFilmById(id: Int): MutableLiveData<FavoriteResponseItem> {
        return LDFilmById
    }

    fun postLiveDataFilm(): MutableLiveData<FavoriteResponseItem> {
        return postLDFilm
    }

    fun updatLiveDataFilm() : MutableLiveData<FavoriteResponseItem> {
        return updateFilm
    }

    fun getLiveDataDelFilm() : MutableLiveData<FavoriteResponseItem> {
        return deleteFilm
    }

    fun callPostApiFilm(name : String, image : String, director : String, desc : String){
        api.addDataFilm(DataFavoriteFilm(name,image,director,desc))
            .enqueue(object : Callback<FavoriteResponseItem> {
                override fun onResponse(
                    call: Call<FavoriteResponseItem>,
                    response: Response<FavoriteResponseItem>
                ) {
                    if (response.isSuccessful){
                        postLDFilm.postValue(response.body())
                    }else{
                        postLDFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<FavoriteResponseItem>, t: Throwable) {
                    postLDFilm.postValue(null)
                }

            })
    }

    fun callGetFilmById(id : Int){
        api.getFilmById(id)
            .enqueue(object : Callback<FavoriteResponseItem> {
                override fun onResponse(
                    call: Call<FavoriteResponseItem>,
                    response: Response<FavoriteResponseItem>
                ) {
                    if (response.isSuccessful){
                        LDFilmById.postValue(response.body())
                    }else{
                        LDFilmById.postValue(null)
                    }
                }

                override fun onFailure(call: Call<FavoriteResponseItem>, t: Throwable) {
                    LDFilmById.postValue(null)
                }

            })
    }

    fun callApiFilm(){
        api.getAllFilm()
            .enqueue(object : Callback<List<FavoriteResponseItem>> {
                override fun onResponse(
                    call: Call<List<FavoriteResponseItem>>,
                    response: Response<List<FavoriteResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    } else{
                        liveDataFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<FavoriteResponseItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }

    fun updateApiFilm(id : Int, name : String, image : String , director: String, description : String){
        api.updateDataFilm(id, DataFavoriteFilm(name,image,director,description))
            .enqueue(object : Callback<FavoriteResponseItem> {
                override fun onResponse(
                    call: Call<FavoriteResponseItem>,
                    response: Response<FavoriteResponseItem>
                ) {
                    if (response.isSuccessful){
                        updateFilm.postValue(response.body())
                    }else{
                        updateFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<FavoriteResponseItem>, t: Throwable) {
                    updateFilm.postValue(null)
                }

            })
    }

    fun callDeleteFilm(id: Int) {
        api.deleteFilm(id)
            .enqueue(object : Callback<FavoriteResponseItem> {
                override fun onResponse(
                    call: Call<FavoriteResponseItem>,
                    response: Response<FavoriteResponseItem>
                ) {
                    if (response.isSuccessful){
                        deleteFilm.postValue(response.body())
                    }else{
                        deleteFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<FavoriteResponseItem>, t: Throwable) {
                    deleteFilm.postValue(null)
                }

            })
    }
}