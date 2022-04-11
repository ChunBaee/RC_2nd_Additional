package com.jcorp.rc_additional_2.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.jcorp.rc_additional_2.R
import com.jcorp.rc_additional_2.adapter.PagerAdapter
import com.jcorp.rc_additional_2.databinding.FragmentSelectLocationBinding
import com.jcorp.rc_additional_2.mViewModel

class SelectLocationActivity : Fragment(), View.OnClickListener {
    private lateinit var binding : FragmentSelectLocationBinding
    private val viewModel by activityViewModels<mViewModel>()
    private var fragList = mutableListOf<LocationFragment>()
    private lateinit var adapter : PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectLocationBinding.inflate(inflater, container, false)

        binding.btnLocationDismiss.setOnClickListener(this)
        binding.btnLocationSet.setOnClickListener(this)
        binding.btnRemoveLocation.setOnClickListener(this)

        setUi()
        observe()
        return binding.root
    }


    private fun observe() {
        viewModel.isClicked.observe(requireActivity(), Observer {
            when(it) {
                true -> {
                    binding.container.isUserInputEnabled = false
                }
                else -> binding.container.isUserInputEnabled = true
            }
        })

        viewModel.clickCountList.observe(requireActivity(), Observer {
            for(i in 0 until it.size) {
                if(it[i] == 0) {
                    binding.tabLayout.getTabAt(i)!!.removeBadge()
                } else {
                    val badge = binding.tabLayout.getTabAt(i)!!.orCreateBadge
                    badge.backgroundColor = resources.getColor(R.color.near_pressed)
                }
            }
        })
        viewModel.selectedTabCount.observe(requireActivity(), Observer{
            Log.d("====", "observe: $it")
            if(it == null || it <= 1) {
                binding.btnLocationSet.setBackgroundResource(R.drawable.background_location_set_unselect)
                binding.btnLocationSet.isClickable = false
                binding.btnRemoveLocation.setTextColor(resources.getColor(R.color.less_gray))
            } else {
                binding.btnLocationSet.setBackgroundResource(R.drawable.background_location_set)
                binding.btnLocationSet.isClickable = true
                binding.btnRemoveLocation.setTextColor(resources.getColor(R.color.near_pressed))
            }
        })
    }

    private fun setUi() {
        setTab()
    }

    private fun setTab() {
        for(i in 0 until 10) {
            fragList.add(LocationFragment(i))
        }
        adapter = PagerAdapter(requireActivity())
        for(frag in fragList) {
            adapter.addItem(frag)
        }
        binding.container.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.container) { tab , position ->
            tab.text = "${position}번 목록"
        }.attach()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_location_dismiss -> {
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

            }

            R.id.btn_remove_location -> {
                viewModel.selectedTabCount.value = 1
                viewModel.title.value = "지역"

                for(i in 0 until viewModel._clickCountList.size) {
                    viewModel._clickCountList[i] = 0
                }

                for(i in 0 until viewModel._fragList.size) {
                    for(o in 0 until viewModel._fragList[i].size) {
                        viewModel._fragList[i][o].isChecked = false
                    }
                }
                viewModel.putChipList()
                viewModel.notifyClickCount()
            }

            R.id.btn_location_set -> {
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }
    }

    override fun onStop() {
        viewModel.setFabState(false)
        super.onStop()
    }
}