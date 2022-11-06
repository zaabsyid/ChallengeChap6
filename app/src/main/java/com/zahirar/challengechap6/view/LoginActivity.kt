package com.zahirar.challengechap6.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.zahirar.challengechap6.R
import com.zahirar.challengechap6.UserPrefs
import com.zahirar.challengechap6.databinding.ActivityLoginBinding
import com.zahirar.challengechap6.model.GetUserResponseItem
import com.zahirar.challengechap6.network.APIClient
import com.zahirar.challengechap6.network.APIInterface
import com.zahirar.challengechap6.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var userVM: ViewModelUser
    lateinit var userPrefs: UserPrefs

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPrefs = UserPrefs(this)
        userVM = ViewModelProvider(this).get(ViewModelUser::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            loginProcess()
        }

        binding.btnIn.setOnClickListener {
            setLocale("id")
        }

        binding.btnEn.setOnClickListener {
            setLocale("en")
        }

        binding.btnLoginGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    fun gotoHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun loginProcess() {
        var username = binding.edtUsername.editText?.text.toString()
        var password = binding.edtPassword.editText?.text.toString()

        if(username.isEmpty() || password.isEmpty()) {
            binding.edtUsername.error = resources.getString(R.string.isi_masukan)
            binding.edtPassword.error = resources.getString(R.string.isi_masukan)
        }else{
            userVM.callGetAllUser()
            userVM.saveIsLoginStatus(true)
            userVM.observerLDGetUser().observe(this) {
                if (it != null) {
                    for (i in it){
                        if (i.username == username && i.password == password){
                            userVM.saveDataId(i.id)
                            Toast.makeText(this, resources.getString(R.string.login_berhasil), Toast.LENGTH_SHORT).show()
                            gotoHome()
                        }
                    }
                }
            }
        }
    }

    fun setLocale(lang: String) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun signInGoogle(){
        val signInIntent: Intent =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException){
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", account.displayName)
            startActivity(intent)
//            startActivity(Intent(this, MainActivity::class.java))


        }
    }
    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, MainActivity::class.java))

        }
    }

    override fun onResume() {
        super.onResume()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, MainActivity::class.java))

        }
    }
}