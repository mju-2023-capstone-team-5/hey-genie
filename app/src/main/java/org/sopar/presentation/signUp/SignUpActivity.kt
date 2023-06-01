package org.sopar.presentation.signUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopar.R
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.databinding.ActivitySignUpBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.login.LoginActivity

@AndroidEntryPoint
class SignUpActivity: AppCompatActivity() {
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getUserId()
        setObserver()
        init()
    }

    private fun getUserId() {
        CoroutineScope(Dispatchers.IO).launch {
            userId = signUpViewModel.getUserId()
        }
    }

    private fun init() {
        binding.btnSignUpComplete.setOnClickListener {
            val name = binding.edtTextSignUpName.text.toString()
            val address = binding.edtTextSignUpAddress.text.toString()
            val phone = binding.edtTextSignUpPhoneNumber.text.toString()
            val carNumber = binding.edtTextSignUpCarNumber.text.toString()

            if (name.isNullOrEmpty() || address.isNullOrEmpty() || phone.isNullOrEmpty() || carNumber.isNullOrEmpty()) {
                val dialog = BaseErrorDialog(R.string.sign_up_error)
                dialog.show(supportFragmentManager, "BaseErrorDialog")
            } else {
                userId?.let { uId ->
                    val userRegisterRequest = UserRegisterRequest(uId, name, address, phone, carNumber)
                    signUpViewModel.userRegister(userRegisterRequest)
                }
            }
        }
    }

    private fun setObserver() {
        signUpViewModel.registerState.observe(this) { state ->
            if (state == NetworkState.SUCCESS) {
                Toast.makeText(this, "회원가입에 성공하셨습니다!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else if (state == NetworkState.FAIL) {
                Log.d("SignUPFragment", "userRegisterState Error")
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(supportFragmentManager, "BaseErrorDialog")
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

}