package org.sopar.presentation.map

import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import net.daum.mf.map.api.MapView
import org.sopar.R
import org.sopar.databinding.FragmentMapBinding
import org.sopar.presentation.base.BaseFragment


class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isAllPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchFocusListener()
    }

    private fun setSearchFocusListener() {
        binding.textMapSearch.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                findNavController().navigate(R.id.action_fragment_map_to_searchFragment)
            }
        }
    }

    private fun isAllPermissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(requireContext(), permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                when {
                    //권한을 승인하였을 떄
                    permission.value -> {
                        Toast.makeText(requireContext(), "필요한 권한이 승인되었습니다!", Toast.LENGTH_SHORT).show()
                    }
                    //권한을 완전히 거부했을 경우
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        Toast.makeText(requireContext(), "설정에서 권한을 승인해주세요!", Toast.LENGTH_SHORT).show()
                    }
                    //권한을 승인하지 않은 경우
                    else -> {
                        Toast.makeText(requireContext(), "설정에서 권한을 승인해주세요!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding {
        return FragmentMapBinding.inflate(inflater, container, false)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.INTERNET
        )
    }

}