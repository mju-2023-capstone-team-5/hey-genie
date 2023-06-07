package org.sopar.presentation.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.data.remote.response.Notice
import org.sopar.databinding.DialogNoticeBinding
import org.sopar.presentation.base.BaseDialog

class NoticeDialog(val notice: Notice): BaseDialog<DialogNoticeBinding>(R.layout.dialog_notice){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.textNoticeTitle.text = notice.title
        binding.textNoticeContent.text = notice.content

        binding.btnOk.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun getViewBinding(): DialogNoticeBinding {
        return DialogNoticeBinding.inflate(layoutInflater)
    }

}