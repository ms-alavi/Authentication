package com.authentication.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.authentication.R
import com.authentication.view.fragment.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, LoginFragment.newInstance())
        }.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) =
            Intent(context, LoginActivity::class.java)
    }
}