package org.sopar.presentation.register

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegisterPermissionImageBinding
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.util.Constants

@AndroidEntryPoint
class RegisterPermissionImageFragment: BaseFragment<FragmentRegisterPermissionImageBinding>(R.layout.fragment_register_permission_image) {
    private val viewModel by viewModels<RegisterViewModel> ()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterPermissionImageBinding {
        return FragmentRegisterPermissionImageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {

        binding.imageGallery.setOnClickListener {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
                requestGalleryLauncher.launch(Constants.REQUIRED_GALLERY_PERMISSION)
            } else {
                requestGalleryLauncher.launch(Constants.REQUIRED_EXTERNAL_STORAGE)
            }
        }

        binding.imageDocument.setOnClickListener {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
                requestGalleryLauncher.launch(Constants.REQUIRED_GALLERY_PERMISSION)
            } else {
                requestGalleryLauncher.launch(Constants.REQUIRED_EXTERNAL_STORAGE)
            }
        }

        binding.btnNextStep.setOnClickListener {
            if (viewModel.permissionUrl == null) {
                Toast.makeText(context, "토지 대장을 등록해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                //등록 정보 확인 페이지로 이동
            }
        }
    }

    private val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted->
        if (isGranted) {
            getGalleryLauncher.launch("image/*")
        } else {
            val dialog = BaseErrorDialog(R.string.permission_error)
            dialog.show(requireActivity().supportFragmentManager, "reques")
        }
    }

    private val getGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.permissionUrl = it.toString()
            binding.imageDocument.visibility = View.VISIBLE
            binding.imageDocument.setImageURI(it)
            binding.imageGallery.visibility = View.GONE
        }
    }
}