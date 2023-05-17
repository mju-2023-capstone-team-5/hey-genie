package org.sopar.presentation.entry

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopar.databinding.ActivityEntryBinding
import org.sopar.presentation.main.MainActivity
import org.sopar.presentation.notice.NoticeActivity
import org.sopar.presentation.register.RegisterActivity

class EntryActivity: AppCompatActivity() {
    private val binding: ActivityEntryBinding by lazy {
        ActivityEntryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
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
}