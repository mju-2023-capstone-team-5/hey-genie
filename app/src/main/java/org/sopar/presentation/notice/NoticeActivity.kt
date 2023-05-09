package org.sopar.presentation.notice

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopar.data.remote.response.Notice
import org.sopar.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {
    private val binding: ActivityNoticeBinding by lazy {
        ActivityNoticeBinding.inflate(layoutInflater)
    }
    private lateinit var noticeAdapter: NoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolBar()
        setupRecyclerView()
        setupNotice()
    }

    private fun setToolBar() {
        setSupportActionBar(binding.noticeToolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        noticeAdapter = NoticeAdapter()

        noticeAdapter.setOnItemClickListener { notice ->
            //다이얼로그로 공지 보여주기
        }

        binding.listNotice.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = noticeAdapter
        }
    }

    private fun setupNotice() {
        //notice 가져오기
        val temp = listOf(
            Notice("dfwfdfwdfw", "wdfwfdwfwfwdf"),
            Notice("dfwfdfwdfw", "wdfwfdwfwfwdf"),
            Notice("dfwfdfwdfw", "wdfwfdwfwfwdf"),
        )

        //notice 없을 경우,
//        binding.textNoNotice.visibility = View.VISIBLE
        noticeAdapter.submitList(temp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}