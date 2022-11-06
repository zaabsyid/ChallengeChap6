package com.zahirar.challengechap6.viewmodel

import com.zahirar.challengechap6.model.FavoriteResponseItem
import com.zahirar.challengechap6.network.APIInterfaceFavorite
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ViewModelFavoriteTest{

    lateinit var servis : APIInterfaceFavorite
    @Before
    fun setUp(){
        servis = mockk()
    }

    @Test
    fun testGetFavorite(){
        val respFavorite = mockk <Call<List<FavoriteResponseItem>>>()
        every {
            servis.getAllFilm()

        } returns respFavorite

        //        System Under Test (WHEN)
        val result = servis.getAllFilm()

        verify {
            servis.getAllFilm()
        }
        Assert.assertEquals(result, respFavorite)
    }
    @Test
    fun testFavorite(){
        val respFavorite = mockk <Call<List<FavoriteResponseItem>>>()
        every {
            servis.getAllFilm()

        } returns respFavorite

        //        System Under Test (WHEN)
        val result = servis.getAllFilm()

        verify {
            runBlocking { servis.getAllFilm() }
        }
        Assert.assertEquals(result, respFavorite)
    }
    @Test
    fun testDeleteData(){
        val id = 0
        val resDelete = mockk <Call<FavoriteResponseItem>>()
        every {
            servis.deleteFilm(id)

        } returns resDelete

        //        System Under Test (WHEN)
        val result = servis.deleteFilm(id)

        verify {
            runBlocking { servis.deleteFilm(id) }
        }
        Assert.assertEquals(result, resDelete)
    }
    @Test
    fun testDeleteFavorite(){
        val id = 0
        val resDeleteFavorite = mockk <Call<FavoriteResponseItem>>()
        every {
            servis.deleteFilm(id)

        } returns resDeleteFavorite

        //        System Under Test (WHEN)
        val result = servis.deleteFilm(id)

        verify {
            runBlocking { servis.deleteFilm(id) }
        }
        Assert.assertEquals(result, resDeleteFavorite)
    }

}