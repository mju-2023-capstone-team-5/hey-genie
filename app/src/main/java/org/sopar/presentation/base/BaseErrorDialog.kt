package org.sopar.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sopar.R
import org.sopar.databinding.DialogBaseErrorBinding

class BaseErrorDialog(val errorRes: Int): BaseDialog<DialogBaseErrorBinding>(R.layout.dialog_base_error) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.textError.setText(errorRes)
        binding.btnError.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun getViewBinding(): DialogBaseErrorBinding {
        return DialogBaseErrorBinding.inflate(layoutInflater)
    }

}