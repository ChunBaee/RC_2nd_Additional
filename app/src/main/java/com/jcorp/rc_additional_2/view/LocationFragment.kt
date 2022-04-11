package com.jcorp.rc_additional_2.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcorp.rc_additional_2.R
import com.jcorp.rc_additional_2.adapter.ChipAdapter
import com.jcorp.rc_additional_2.data.ChipItem
import com.jcorp.rc_additional_2.databinding.FragmentLocationBinding
import com.jcorp.rc_additional_2.mViewModel

class LocationFragment(position: Int) : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private val viewModel by activityViewModels<mViewModel>()
    private lateinit var chipAdapter: ChipAdapter
    private val mPosition = position

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLocationBinding.inflate(inflater, container, false)

        setRecycler()
        observe()

        return binding.root
    }

    private fun setRecycler() {
        chipAdapter = ChipAdapter(requireActivity())

        binding.locationRecycler.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.locationRecycler.adapter = chipAdapter

        chipAdapter.setData(viewModel._fragList[mPosition])

        binding.locationRecycler.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                when(e.action) {
                    MotionEvent.ACTION_DOWN -> {
                    }
                    MotionEvent.ACTION_UP -> {
                        var position = rv.getChildAdapterPosition(rv.findChildViewUnder(e.x, e.y)!!)
                        if (viewModel._fragList[mPosition][position].isChecked == true) {
                            viewModel._fragList[mPosition][position].isChecked = false
                            viewModel.setClickCount(mPosition, false)
                            viewModel.selectedTabCount.value = viewModel.selectedTabCount.value?.minus(1)

                        } else if (viewModel._fragList[mPosition][position].isChecked != true) {
                            viewModel._fragList[mPosition][position].isChecked = true
                            viewModel.setClickCount(mPosition, true)
                            viewModel.selectedTabCount.value = viewModel.selectedTabCount.value?.plus(1)
                            viewModel.title.value = viewModel._fragList[mPosition][position].title
                        }
                        viewModel.putChipList()
                    }
                }
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }

        })
    }

    private fun observe() {
        viewModel.fragList.observe(requireActivity(), Observer {
            chipAdapter.setData(it[mPosition])
        })
    }
}