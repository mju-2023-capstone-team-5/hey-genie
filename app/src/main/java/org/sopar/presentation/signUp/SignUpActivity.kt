package org.sopar.presentation.signUp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopar.databinding.ActivitySignUpBinding

class SignUpActivity: AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}