package org.sopar.presentation.register

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.databinding.FragmentRegisterPermissionImageBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import org.sopar.util.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class RegisterPermissionImageFragment: BaseFragment<FragmentRegisterPermissionImageBinding>(R.layout.fragment_register_permission_image) {
    private val viewModel by activityViewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterPermissionImageBinding {
        return FragmentRegisterPermissionImageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObserve()
    }

    private fun setObserve() {
        viewModel.registerStatus.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.SUCCESS) {
                registerImage()
            } else {
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(requireActivity().supportFragmentManager, "RegisterErrorDialog")
            }

        }
    }

    private fun registerImage() {
        val parkingLotFile = File(getRealPath(viewModel.imageUrl!!.toUri()))
        val permissionFile = File(getRealPath(viewModel.permissionUrl!!.toUri()))
        viewModel.registerParkingLotImage(parkingLotFile)
        viewModel.registerPermissionImage(permissionFile)
    }

    private fun getRealPath(uri: Uri): String?{
        val contentResolver = requireContext().contentResolver
        val filePath = requireContext().applicationInfo.dataDir + File.separator + System.currentTimeMillis() + ".jpg"
        val file = File(filePath)

        try{
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while(inputStream.read(buf).also { len = it } > 0)
                outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        }catch(ignore: IOException){
            return null
        }
        return file.absolutePath
    }

    private fun setListener() {

        binding.imageGallery.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                requestGalleryLauncher.launch(Constants.REQUIRED_GALLERY_PERMISSION)
            } else {
                requestGalleryLauncher.launch(Constants.REQUIRED_EXTERNAL_STORAGE)
            }
        }

        binding.imageDocument.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                requestGalleryLauncher.launch(Constants.REQUIRED_GALLERY_PERMISSION)
            } else {
                requestGalleryLauncher.launch(Constants.REQUIRED_EXTERNAL_STORAGE)
            }
        }

        binding.btnNextStep.setOnClickListener {
            if (viewModel.permissionUrl == null) {
                Toast.makeText(context, "토지 대장을 등록해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.register_view_pager)
                viewPager.setCurrentItem(8, true)

                registerParkingLot()

                Toast.makeText(context, "등록이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun registerParkingLot() {
        val name = viewModel.name!!
        val address = viewModel.address!!
        val freeInformation = viewModel.freeInformation
        val phoneNumber = viewModel.phoneNumber
        val latitude = viewModel.latitude
        val longitude = viewModel.longitude
        val totalSpace = viewModel.totalSpace
        val remainingSpace = viewModel.totalSpace
        val ownerId = viewModel.ownerId
        val type = viewModel.type
        val availableDay = viewModel.availableDay
        val availableTime = viewModel.availableTime
        val monthly = viewModel.monthly
        val hourly = viewModel.hourly

        val parkingLot = ParkingLotRequest(
            name = name,
            address = address,
            freeInformation = freeInformation,
            phoneNumber = phoneNumber!!,
            latitude = latitude!!,
            longitude = longitude!!,
            totalSpace = totalSpace!!,
            remainingSpace = remainingSpace!!,
            ownerId = null,
            type = type!!,
            availableDay = availableDay!!,
            availableTime = availableTime!!,
            monthly = monthly,
            hourly = hourly,
        )

        viewModel.registerParkingLot(parkingLot)
    }


        private val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                getGalleryLauncher.launch("image/*")
            } else {
                val dialog = BaseErrorDialog(R.string.permission_error)
                dialog.show(requireActivity().supportFragmentManager, "reques")
            }
        }

        private val getGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                viewModel.permissionUrl = it.toString()
                binding.imageDocument.visibility = View.VISIBLE
                binding.imageDocument.setImageURI(it)
                binding.imageGallery.visibility = View.GONE
            }
        }

}