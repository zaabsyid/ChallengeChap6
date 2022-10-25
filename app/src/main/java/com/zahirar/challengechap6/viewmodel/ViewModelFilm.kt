package com.zahirar.challengechap6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zahirar.challengechap6.model.DataFilm
import com.zahirar.challengechap6.model.GetFilmResponseItem
import com.zahirar.challengechap6.model.PostDataFilm
import com.zahirar.challengechap6.model.PostDataFilmItem
import com.zahirar.challengechap6.network.APIClient
import com.zahirar.challengechap6.network.APIInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(var api : APIInterface) : ViewModel() {

    lateinit var liveDataFilm : MutableLiveData<List<GetFilmResponseItem>>
    lateinit var LDFilmById : MutableLiveData<PostDataFilmItem>
    lateinit var postLDFilm : MutableLiveData<PostDataFilm>
    lateinit var updateFilm : MutableLiveData<PostDataFilmItem>
    lateinit var deleteFilm : MutableLiveData<PostDataFilmItem>

    init {
        liveDataFilm = MutableLiveData()
        LDFilmById = MutableLiveData()
        postLDFilm = MutableLiveData()
        updateFilm = MutableLiveData()
        deleteFilm = MutableLiveData()
    }

    fun getLiveDataFilem() : MutableLiveData<List<GetFilmResponseItem>> {
        return liveDataFilm
    }

    fun getFilmById(id: Int): MutableLiveData<PostDataFilmItem> {
        return LDFilmById
    }

    fun postLiveDataFilm(): MutableLiveData<PostDataFilm> {
        return postLDFilm
    }

    fun updatLiveDataFilm() : MutableLiveData<PostDataFilmItem> {
        return updateFilm
    }

    fun getLiveDataDelFilm() : MutableLiveData<PostDataFilmItem> {
        return deleteFilm
    }

    fun callPostApiFilm(name : String, image : String, director : String, desc : String){
        api.addDataFilm(DataFilm(name,image,director,desc))
            .enqueue(object : Callback<PostDataFilm> {
                override fun onResponse(
                    call: Call<PostDataFilm>,
                    response: Response<PostDataFilm>
                ) {
                    if (response.isSuccessful){
                        postLDFilm.postValue(response.body())
                    }else{
                        postLDFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PostDataFilm>, t: Throwable) {
                    postLDFilm.postValue(null)
                }

            })
    }

    fun callGetFilmById(id : Int){
        api.getFilmById(id)
            .enqueue(object : Callback<PostDataFilmItem> {
                override fun onResponse(
                    call: Call<PostDataFilmItem>,
                    response: Response<PostDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        LDFilmById.postValue(response.body())
                    }else{
                        LDFilmById.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PostDataFilmItem>, t: Throwable) {
                    LDFilmById.postValue(null)
                }

            })
    }

    fun callApiFilm(){
        api.getAllFilm()
            .enqueue(object : Callback<List<GetFilmResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetFilmResponseItem>>,
                    response: Response<List<GetFilmResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    } else{
                        liveDataFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetFilmResponseItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }

    fun updateApiFilm(id : Int, name : String, image : String , director: String, description : String){
        api.updateDataFilm(id, DataFilm(name,image,director,description))
            .enqueue(object : Callback<PostDataFilmItem> {
                override fun onResponse(
                    call: Call<PostDataFilmItem>,
                    response: Response<PostDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        updateFilm.postValue(response.body())
                    }else{
                        updateFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PostDataFilmItem>, t: Throwable) {
                    updateFilm.postValue(null)
                }

            })
    }

    fun callDeleteFilm(id: Int) {
        api.deleteFilm(id)
            .enqueue(object : Callback<PostDataFilmItem> {
                override fun onResponse(
                    call: Call<PostDataFilmItem>,
                    response: Response<PostDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        deleteFilm.postValue(response.body())
                    }else{
                        deleteFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PostDataFilmItem>, t: Throwable) {
                    deleteFilm.postValue(null)
                }

            })
    }

}