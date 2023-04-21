package org.sopar.presentation.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopar.R
import org.sopar.databinding.ActivityLoginBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.entry.EntryActivity
import org.sopar.presentation.main.MainActivity
import org.sopar.presentation.signUp.SignUpActivity

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setObserver()
        init()
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

        this.splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L

            slideUp.doOnEnd { splashScreenView.remove() }
            slideUp.start()
        }
    }

    private fun setObserver() {
        // 기존 유저 로그인 성공
        loginViewModel.loginState.observe(this) { state ->
            if (state == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(supportFragmentManager, "BaseErrorDialog")
            }
            if (state == NetworkState.SUCCESS) {

                val intent = Intent(this, EntryActivity::class.java)
                startActivity(intent)
            }
        }

        // 새로운 유저 로그인 성공
        loginViewModel.newUserLoginState.observe(this) { state ->
            if (state == NetworkState.SUCCESS) {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}