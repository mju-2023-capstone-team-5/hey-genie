package org.sopar.presentation.entry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.SoparFirebaseMessagingService
import org.sopar.databinding.ActivityEntryBinding
import org.sopar.presentation.main.MainActivity
import org.sopar.presentation.notice.NoticeActivity
import org.sopar.presentation.register.RegisterActivity

@AndroidEntryPoint
class EntryActivity: AppCompatActivity() {
    private val binding: ActivityEntryBinding by lazy {
        ActivityEntryBinding.inflate(layoutInflater)
    }
    private val entryViewModel by viewModels<EntryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        getDeviceToken()

    }

    private fun init() {
        binding.btnRegister.setOnClickListener {
            //등록하기 화면으로 전환
        }

        binding.btnHourlyParking.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("isHourly", true)
            startActivity(intent)
        }

        binding.btnMonthlyParking.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("isHourly", false)
            startActivity(intent)
        }

        binding.btnNotice.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("TokenTest", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result
                entryViewModel.setUserFCMToken(token)
            }
        )
    }
}