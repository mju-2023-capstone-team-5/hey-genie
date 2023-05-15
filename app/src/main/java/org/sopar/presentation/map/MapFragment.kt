package org.sopar.presentation.map

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener
import net.daum.mf.map.api.MapView.POIItemEventListener
import org.sopar.R
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.Place
import org.sopar.databinding.FragmentMapBinding
import org.sopar.domain.entity.NetworkState
import org.sopar.presentation.base.BaseErrorDialog
import org.sopar.presentation.base.BaseFragment
import kotlin.math.log

@AndroidEntryPoint
class MapFragment: BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {
    private val args: MapFragmentArgs by navArgs()
    private val parkingLotAdapter: ParkingLotAdapter = ParkingLotAdapter()
    private var isHourly: Boolean? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val mapViewModel by viewModels<MapViewModel>()
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isAllPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        isHourly = requireActivity().intent.extras?.getBoolean("isHourly")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = MapView(requireActivity())
        mapView?.apply {
            setMapViewEventListener(object: MapViewEventListener {
                override fun onMapViewInitialized(p0: MapView?) {
                    val place = args.place
                    if (place == null) {
                        currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                    } else {
                        //검색 결과가 있을 경우, 해당 위치로 지도 셋팅
                        currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
                        setSearchResult(place)
                    }
                }

                override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

                override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}

                override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}

                override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

                override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

                override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

                override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

                override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
                    Log.e("current moved update", p1.toString())
                    mapViewModel.updateMapCenter(p1)
                }
            })
        }
        binding.mapView.addView(mapView)
        setMapPOIItemListener()
        setupRecyclerView()
        setObserve()
        setSearchFocusListener()
    }

    private fun setMapPOIItemListener() {
        mapView!!.setPOIItemEventListener(object: POIItemEventListener{
            override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
//                val action = MapFragmentDirections.actionFragmentMapToReservationFragment2()
            }

            override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {}

            override fun onCalloutBalloonOfPOIItemTouched(
                p0: MapView?,
                p1: MapPOIItem?,
                p2: MapPOIItem.CalloutBalloonButtonType?
            ) {}

            override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {}

        })
    }

    private fun setObserve() {
        mapViewModel.getParkingLotState.observe(viewLifecycleOwner) { state ->
            if (state == NetworkState.FAIL) {
                val dialog = BaseErrorDialog(R.string.base_error)
                dialog.show(requireActivity().supportFragmentManager, "BaseErrorDialog")
            }
        }

        mapViewModel.parkingLots.observe(viewLifecycleOwner) { parkingLots ->
            if (parkingLots.isNotEmpty()) {
                val temp = getCategoryList(parkingLots)
                Log.d("adapter submit", temp.toString())
                parkingLotAdapter.submitList(temp)
                for (item in temp) {
                    setCustomPicker(item)
                }

                binding.textNoticeParkingLot.visibility = View.GONE
                binding.listParkingLot.visibility = View.VISIBLE
            } else {
                binding.textNoticeParkingLot.visibility = View.VISIBLE
                binding.listParkingLot.visibility = View.GONE
            }
        }

        mapViewModel.mapCenter.observe(viewLifecycleOwner) { center ->
            if (center?.mapPointGeoCoord?.latitude != -1.0E7) {
                Log.d("center check", center.mapPointGeoCoord.latitude.toString())
                getBound()
            }
        }
    }

    private fun getCategoryList(list: List<ParkingLot>): List<ParkingLot> {
        val temp = ArrayList<ParkingLot>()
        for (item in list) {
            if (isHourly!!) {
                if (item.hourly != null) {
                    temp.add(item)
                }
            } else {
                temp.add(item)
            }
        }

        return temp
    }

    //화면에 표시할 위치 범위 구하기
    private fun getBound() {
        val x1 = mapView!!.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude
        val x2 = mapView!!.mapPointBounds.topRight.mapPointGeoCoord.latitude
        val y1 =  mapView!!.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
        val y2 = mapView!!.mapPointBounds.topRight.mapPointGeoCoord.longitude
        mapViewModel.getParkingLots(x1, x2, y1, y2)
    }

    private fun setupRecyclerView() {
        parkingLotAdapter.setOnItemClickListener { parkingLot ->
            val action = MapFragmentDirections.actionFragmentMapToReservationFragment2(parkingLot, isHourly!!)
            findNavController().navigate(action)
        }

        binding.listParkingLot.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = parkingLotAdapter
        }
    }

    private fun setSearchResult(place: Place) {
        binding.textMapSearch.setText(place.place_name)
        setPicker(place.place_name, place.x.toDouble(), place.y.toDouble())

        mapView!!.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(place.y.toDouble(), place.x.toDouble()), true)
    }

    private fun setCustomPicker(parkingLot: ParkingLot) {
        val marker = MapPOIItem()
        val point = MapPoint.mapPointWithGeoCoord(parkingLot.latitude, parkingLot.longitude)
        marker.apply {
            itemName = parkingLot.name
            tag = parkingLot.id
            mapPoint = point
            markerType = MapPOIItem.MarkerType.CustomImage
            //추후 평점 직접 등록
            customImageResourceId = getImageByScore(5)
            setCustomImageAnchor(0.5f, 1.0f)
            showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
            isDraggable = false
        }

        mapView!!.addPOIItem(marker)
    }

    private fun getImageByScore(score: Int): Int {
        if (score > 4) {
            return R.drawable.ic_sopar_logo
        } else {
            //평점에 따라 추후 변경해야 함
            return R.drawable.ic_sopar_logo
        }
    }

    private fun setPicker(name: String, x: Double, y:Double) {
        val marker = MapPOIItem()
        val point = MapPoint.mapPointWithGeoCoord(y, x)
        marker.apply {
            itemName = name
            tag = 0
            mapPoint = point
            markerType = MapPOIItem.MarkerType.BluePin
            selectedMarkerType = MapPOIItem.MarkerType.RedPin
        }

        mapView!!.addPOIItem(marker)
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