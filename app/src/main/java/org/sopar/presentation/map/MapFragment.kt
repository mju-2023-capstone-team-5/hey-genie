package org.sopar.presentation.map

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import org.sopar.R
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.Place
import org.sopar.databinding.FragmentMapBinding
import org.sopar.presentation.base.BaseFragment

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {
    private val args: MapFragmentArgs by navArgs()
    private lateinit var parkingLotAdapter: ParkingLotAdapter
    private var isHourly: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isAllPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        isHourly = requireActivity().intent.extras?.getBoolean("isHourly")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchFocusListener()
        setupRecyclerView()

        val place = args.place
        place?.let {
            //검색 결과가 있을 경우, 해당 위치로 지도 셋팅
            setSearchResult(place)
        }
    }

    private fun setupRecyclerView() {
        parkingLotAdapter = ParkingLotAdapter()

        parkingLotAdapter.setOnItemClickListener { parkingLot ->
            val action = MapFragmentDirections.actionFragmentMapToReservationFragment2(parkingLot, isHourly!!)
            findNavController().navigate(action)
        }

        binding.listParkingLot.apply {
            //정해진 사이즈가 있으니 새로운 요소를 추가할 때 recyclerview의 크기를 재측정 하지 않아도 된다.
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = parkingLotAdapter
        }

        val temp = listOf(
            ParkingLot("dfdf", null, 0.0, 1.0, "dfdf", 9, 9, 3000, 1000),
            ParkingLot("dfdf", null, 0.0, 1.0, "dfdf", 9, 9, 0, 0),
            ParkingLot("dfdf", null, 0.0, 1.0, "dfdf", 9, 9, 0, 0),
            ParkingLot("dfdf", null, 0.0, 1.0, "dfdf", 9, 9, 0, 0)
        )
        parkingLotAdapter.submitList(temp)
    }

    private fun setSearchResult(place: Place) {
        binding.textMapSearch.setText(place.place_name)

        val marker = MapPOIItem()
        val y = place.y.toDouble()
        val x = place.x.toDouble()
        val point = MapPoint.mapPointWithGeoCoord(y, x)
        marker.apply {
            itemName = place.place_name
            tag = 0
            mapPoint = point
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }

        binding.mapView.apply {
            setMapCenterPoint(MapPoint.mapPointWithGeoCoord(place.y.toDouble(), place.x.toDouble()), true)
            addPOIItem(marker)
        }

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