package com.zahirar.challengechap6.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zahirar.challengechap6.UserPrefs
import com.zahirar.challengechap6.model.DataUser
import com.zahirar.challengechap6.model.GetUserResponseItem
import com.zahirar.challengechap6.model.PostUserResponse
import com.zahirar.challengechap6.network.APIClient
import com.zahirar.challengechap6.network.APIInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(var api : APIInterface, @ApplicationContext appContext : Context) : ViewModel(){

    lateinit var livedataGetUser :MutableLiveData<List<GetUserResponseItem>>
    lateinit var postLDUser : MutableLiveData<PostUserResponse>
    lateinit var liveGetDataUserId : MutableLiveData<GetUserResponseItem>
    lateinit var liveDataUpdateUserId : MutableLiveData<GetUserResponseItem>
    private var livedataId : MutableLiveData<String>
    private var livedataUsername : MutableLiveData<String>
    private var livedataPassword : MutableLiveData<String>
    private var livedataName : MutableLiveData<String>
    private val livedataIsLogin : MutableLiveData<Boolean>
    private val userPrefs: UserPrefs = UserPrefs(appContext)

    init {
        livedataGetUser = MutableLiveData()
        postLDUser = MutableLiveData()
        liveGetDataUserId = MutableLiveData()
        liveDataUpdateUserId = MutableLiveData()
        livedataId = MutableLiveData()
        livedataUsername = MutableLiveData()
        livedataPassword = MutableLiveData()
        livedataName = MutableLiveData()
        livedataIsLogin = MutableLiveData()
    }

    fun observerLDGetUser() : MutableLiveData<List<GetUserResponseItem>> {
        return livedataGetUser
    }

    fun postLiveDataUser(): MutableLiveData<PostUserResponse> {
        return postLDUser
    }

    fun getLiveDataUserId() : MutableLiveData<GetUserResponseItem> {
        return liveGetDataUserId
    }

    fun getliveDataUpdateUserId() : MutableLiveData<GetUserResponseItem> {
        return liveDataUpdateUserId
    }

    fun observerId() : MutableLiveData<String> = livedataId
    fun observerUsername() : MutableLiveData<String> = livedataUsername
    fun observerName() : MutableLiveData<String> = livedataName
    fun observerPassword() : MutableLiveData<String> = livedataPassword
    fun observerIsLogin() : MutableLiveData<Boolean> = livedataIsLogin

    fun callGetAllUser(){
        api.getAllUser()
            .enqueue(object : Callback<List<GetUserResponseItem>>{
            override fun onResponse(
                call: Call<List<GetUserResponseItem>>,
                response: Response<List<GetUserResponseItem>>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null){
                        livedataGetUser.postValue(data)
                    }else{
                        livedataGetUser.postValue(null)
                    }
                }else{
                    livedataGetUser.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<GetUserResponseItem>>, t: Throwable) {
                livedataGetUser.postValue(null)
            }

        })
    }

    fun callPostApiUser(name : String, username : String, password : String){
        api.registerUser(
            DataUser(name,username,password)
        )
            .enqueue(object : retrofit2.Callback<PostUserResponse>{
                override fun onResponse(
                    call: retrofit2.Call<PostUserResponse>,
                    response: retrofit2.Response<PostUserResponse>
                ) {
                    if (response.isSuccessful){
                        postLDUser.postValue(response.body())
                    }else{
                        postLDUser.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<PostUserResponse>, t: Throwable) {
                    postLDUser.postValue(null)
                }

            })
    }

    fun callGetUserById(id : Int){
        api.getUsersById(id)
            .enqueue(object : Callback<GetUserResponseItem> {
                override fun onResponse(
                    call: Call<GetUserResponseItem>,
                    response: Response<GetUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveGetDataUserId.postValue(response.body())
                    }else{
                        liveGetDataUserId.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetUserResponseItem>, t: Throwable) {
                    liveGetDataUserId.postValue(null)
                }

            })
    }

    fun updateApiUsers(id : Int, name : String, username : String , password: String){
        api.updateDataUsers(id, DataUser(name, username, password))
            .enqueue(object : Callback<GetUserResponseItem> {
                override fun onResponse(
                    call: Call<GetUserResponseItem>,
                    response: Response<GetUserResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveDataUpdateUserId.postValue(response.body())
                    }else{
                        liveDataUpdateUserId.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetUserResponseItem>, t: Throwable) {
                    liveDataUpdateUserId.postValue(null)
                }

            })
    }

    fun getDataUser(lifecycle: LifecycleOwner){
        getUserId(lifecycle)
        getUsername(lifecycle)
        getName(lifecycle)
        getPassword(lifecycle)
    }

    fun saveData(name : String, username : String, password : String){
        GlobalScope.launch {
            userPrefs.saveData(username, name, password)
        }
    }

    fun saveDataId(id : String){
        GlobalScope.launch {
            userPrefs.saveDataId(id)
        }
    }

    fun getUserId(lifecycle: LifecycleOwner){
        userPrefs.getId.asLiveData().observe(lifecycle) {
            livedataId.postValue(it)
        }
    }

    fun getUsername(lifecycle: LifecycleOwner){
        userPrefs.getUsername.asLiveData().observe(lifecycle) {
            livedataUsername.postValue(it)
        }
    }

    fun getName(lifecycle: LifecycleOwner){
        userPrefs.getName.asLiveData().observe(lifecycle) {
            livedataName.postValue(it)
        }
    }

    fun getPassword(lifecycle: LifecycleOwner){
        userPrefs.getPassword.asLiveData().observe(lifecycle) {
            livedataPassword.postValue(it)
        }
    }

    fun checkIsLogin(lifecycle: LifecycleOwner){
        userPrefs.getIsLogin.asLiveData().observe(lifecycle) {
            livedataIsLogin.postValue(it)
        }
    }

    fun saveIsLoginStatus(status : Boolean){
        GlobalScope.launch {
            userPrefs.saveIsLoginStatus(status)
        }
    }

    fun removeIsLoginStatus(){
        GlobalScope.launch {
            userPrefs.removeIsLoginStatus()
        }
    }

}