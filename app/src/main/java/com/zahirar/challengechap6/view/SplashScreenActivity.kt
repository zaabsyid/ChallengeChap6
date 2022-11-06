package com.zahirar.challengechap6.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap6.R
import com.zahirar.challengechap6.databinding.ActivitySplashScreenBinding
import com.zahirar.challengechap6.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashScreenBinding
    lateinit var userVM : ViewModelUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        throw RuntimeException("Test Crash") // Force a crash

        userVM = ViewModelProvider(this).get(ViewModelUser::class.java)
        var isLogin = false
        userVM.checkIsLogin(this)
        userVM.observerIsLogin().observe(this) {
            isLogin = it
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if(isLogin){
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }, 3000)
    }
}