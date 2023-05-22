package org.sopar.presentation.register

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegisterParkingLotImageBinding
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.util.Constants.REQUIRED_EXTERNAL_STORAGE
import org.sopar.util.Constants.REQUIRED_GALLERY_PERMISSION

@AndroidEntryPoint

class RegisterParkingLotImageFragment: BaseFragment<FragmentRegisterParkingLotImageBinding>(R.layout.fragment_register_parking_lot_image) {
    private val viewModel by activityViewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterParkingLotImageBinding {
        return FragmentRegisterParkingLotImageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {

        binding.imageParkingLot.setOnClickListener {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
                requestGalleryLauncher.launch(REQUIRED_GALLERY_PERMISSION)
            } else {
                requestGalleryLauncher.launch(REQUIRED_EXTERNAL_STORAGE)
            }
        }

        binding.imageGallery.setOnClickListener {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
                requestGalleryLauncher.launch(REQUIRED_GALLERY_PERMISSION)
            } else {
                requestGalleryLauncher.launch(REQUIRED_EXTERNAL_STORAGE)
            }
        }

        binding.btnNextStep.setOnClickListener {
            if (viewModel.imageUrl == null) {
                Toast.makeText(context, "주차장 이미지를 등록해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                viewPager.setCurrentItem(7, true)
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
            viewModel.imageUrl = it.toString()
            binding.imageParkingLot.visibility = View.VISIBLE
            binding.imageParkingLot.setImageURI(it)
            binding.imageGallery.visibility = View.GONE
        }
    }

}