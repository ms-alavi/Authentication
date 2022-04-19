package com.authentication.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.authentication.R
import com.authentication.model.repository.TokenContainer
import com.authentication.view.fragment.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (TokenContainer.token != null) {
                startActivity(UserDetailActivity.newInstance(this))
                finish()
            }else{
                startActivity(LoginActivity.newInstance(this))
                finish()
            }
        }, 500)
    }
}