package org.sopar.presentation.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.ActivityRegisterBinding
import org.sopar.presentation.entry.EntryActivity
import org.sopar.presentation.main.MainActivity

@AndroidEntryPoint
class RegisterActivity :AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolBar()
        setViewPager()
    }

    private fun setViewPager() {

        val pagerAdapter = RegisterRecyclerViewAdapter(this)
        pagerAdapter.addFragment(RegisterFragment1())
        pagerAdapter.addFragment(RegisterFragment2())
        pagerAdapter.addFragment(RegisterFragment3())
        pagerAdapter.addFragment(RegisterFragment4())
        pagerAdapter.addFragment(RegisterFragment5())
        pagerAdapter.addFragment(RegisterFragment6())
        pagerAdapter.addFragment(RegisterParkingLotImageFragment())
        pagerAdapter.addFragment(RegisterPermissionImageFragment())
        pagerAdapter.addFragment(RegisterCompleteFragment())

        binding.registerViewPager.apply {
            adapter = pagerAdapter
            isUserInputEnabled = false
        }

    }

    private fun setToolBar() {
        setSupportActionBar(binding.noticeToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val cur = binding.registerViewPager.currentItem
            if ((cur == 0) or (cur == 8)) {
                val intent = Intent(this, EntryActivity::class.java)
                startActivity(intent)
            } else {
                binding.registerViewPager.setCurrentItem(cur-1, true)
            }
        }
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}