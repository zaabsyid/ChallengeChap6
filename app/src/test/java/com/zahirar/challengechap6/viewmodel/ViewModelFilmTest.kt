package com.zahirar.challengechap6.viewmodel

import com.zahirar.challengechap6.model.GetFilmResponseItem
import com.zahirar.challengechap6.network.APIInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ViewModelFilmTest{
    lateinit var servis : APIInterface
    @Before
    fun setUp(){
        servis = mockk()
    }

    @Test
    fun getDataFilmTest(): Unit = runBlocking {
//        mocking GIVEN
        val respAllDataFilm = mockk <Call<List<GetFilmResponseItem>>>()

        every {
            runBlocking {
                servis.getAllFilm()
            }
        } returns respAllDataFilm

//        System Under Test (WHEN)
        val result = servis.getAllFilm()

        verify {
            runBlocking { servis.getAllFilm() }
        }
        assertEquals(result, respAllDataFilm)
    }

    @Test
    fun testGetDataFilm(){
        val respAllDataFilm = mockk <Call<List<GetFilmResponseItem>>>()
        every {
            servis.getAllFilm()

        }returns respAllDataFilm

        //        System Under Test (WHEN)
        val result = servis.getAllFilm()

        verify {
            servis.getAllFilm()
        }
        Assert.assertEquals(result, respAllDataFilm)
    }
}