package org.sopar.presentation.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopar.R
import org.sopar.databinding.ActivityLoginBinding
import org.sopar.presentation.base.BaseErrorDialog

class LoginActivity: AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        setObserver()
    }

    private fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            loginViewModel.checkToken()
        }

        binding.buttonKakaoLogin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                loginViewModel.loginWithKakao(this@LoginActivity)
            }
        }
    }

    private fun setObserver() {
        loginViewModel.error.observe(this) { error ->
            if (error) {
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(supportFragmentManager, "BaseErrorDialog")
            }
        }
    }
}