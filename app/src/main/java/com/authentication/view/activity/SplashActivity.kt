package com.authentication.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.authentication.R
import com.authentication.model.repository.TokenContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
            if (TokenContainer.token != null) {
                startActivity(UserDetailActivity.newInstance(this))
                finish()
            }else{
                startActivity(LoginActivity.newInstance(this))
                finish()
            }
    }
}