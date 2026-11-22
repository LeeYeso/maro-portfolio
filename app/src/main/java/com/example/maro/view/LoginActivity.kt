package com.example.maro.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.maro.databinding.ActivityLoginBinding
import com.example.maro.utils.LanguageUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    // set Language
    override fun attachBaseContext(newBase: Context) {
        val langCode = LanguageUtil.getSavedLanguage(newBase)
        val context = LanguageUtil.setLocale(newBase, langCode)
        super.attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogleLogin.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    // Google login
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                // 로그인 성공 후 온보딩 체크
                val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                val onboardingDone = prefs.getBoolean("onboarding_completed", false)

                val nextActivity = if (onboardingDone) {
                    MainActivity::class.java
                } else {
                    OnboardingLanguageActivity::class.java
                }

                val intent = Intent(this, nextActivity)
                startActivity(intent)
                finish()
            } catch (e: ApiException) {
                Toast.makeText(
                    this,
                    "Google login failed. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}
