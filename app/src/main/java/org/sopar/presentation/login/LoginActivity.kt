package org.sopar.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopar.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}