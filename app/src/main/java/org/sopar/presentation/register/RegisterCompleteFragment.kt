package org.sopar.presentation.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopar.R
import org.sopar.databinding.FragmentRegisterCompleteBinding
import org.sopar.presentation.base.BaseFragment
import org.sopar.presentation.main.MainActivity

@AndroidEntryPoint
class RegisterCompleteFragment : BaseFragment<FragmentRegisterCompleteBinding>(R.layout.fragment_register_complete) {
    private val viewModel by activityViewModels<RegisterViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterCompleteBinding {
        return FragmentRegisterCompleteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListener()
    }

    private fun setListener() {
        binding.checkboxShowInfo.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.layoutInfo.visibility = View.VISIBLE
            } else {
                binding.layoutInfo.visibility = View.GONE
            }
        }

        binding.btnMain.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {

        binding.textParkingLotName.text = viewModel.name
        binding.textParkingLotAddress.text = viewModel.address
        binding.textParkingLotSpace.text = viewModel.totalSpace.toString()
        viewModel.availableTime?.let {
            val list = arrayListOf<String>()
            for (item in it) {
                list.add(item.startTime.toString())
            }

            binding.textSharingTimes.text = list.sorted().joinToString(separator = ", ")
        }
        viewModel.availableDay?.let {
            binding.textSharingDays.text = it.joinToString(separator = ", ")
        }
        binding.textCarTypes.text = viewModel.type?.joinToString(separator = ", ")
        binding.textPhoneNumber.text = viewModel.phoneNumber
//        viewModel.freeInformation?.let {
//            binding.textCaution.text = it
//        }
        viewModel.hourly?.let {
            binding.textHourly.text = "${it.minimum}원(최소) / ${it.surcharge}원(추가)"
        }
        viewModel.monthly?.let {
            binding.textMonthly.text = "${it.minimum}원(최소) / ${it.surcharge}원(추가)"
        }

    }

}