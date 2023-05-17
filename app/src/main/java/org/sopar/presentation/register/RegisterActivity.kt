package org.sopar.presentation.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.sopar.databinding.ActivityMainBinding
import org.sopar.databinding.ActivityRegisterBinding

class RegisterActivity : FragmentActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = RegisterRecyclerViewAdapter(this)
        adapter.addFragment(RegisterFragment1())
        adapter.addFragment(RegisterFragment2())
        adapter.addFragment(RegisterFragment3())
        adapter.addFragment(RegisterFragment4())
        adapter.addFragment(RegisterFragment5())
        adapter.addFragment(RegisterFragment6())
        adapter.addFragment(RegisterParkingLotImageFragment())
        adapter.addFragment(RegisterPermissionImageFragment())

        binding.registerViewPager.adapter = adapter

    }

}