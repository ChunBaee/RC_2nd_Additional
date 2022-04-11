package com.jcorp.rc_additional_2

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jcorp.rc_additional_2.adapter.GridAdapter
import com.jcorp.rc_additional_2.adapter.HoriAdapter
import com.jcorp.rc_additional_2.data.ChipItem
import com.jcorp.rc_additional_2.data.GridItem
import com.jcorp.rc_additional_2.data.HoriItem
import com.jcorp.rc_additional_2.databinding.ActivityMainBinding
import com.jcorp.rc_additional_2.view.SelectLocationActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var horiAdapter: HoriAdapter
    private lateinit var gridAdapter: GridAdapter
    private val viewModel: mViewModel by viewModels()
    private var selectFrag = SelectLocationActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.btnNearMe.setOnClickListener(this)
        binding.btnLocation.setOnClickListener(this)

        setUI()
        setData()
        observe()
    }

    private fun observe() {
        viewModel.isNearPressed.observe(this, Observer {
            when (it) {
                true -> {
                    binding.imgNear.setImageResource(R.drawable.icon_reload_selected)
                    binding.txtNear.setTextColor(resources.getColor(R.color.near_pressed))
                }

                false -> {
                    binding.imgNear.setImageResource(R.drawable.icon_reload_unselected)
                    binding.txtNear.setTextColor(resources.getColor(R.color.gray))
                }
            }
        })

        viewModel.gridList.observe(this, Observer {
            Log.d("----", "observe: OBSERVED!")
            gridAdapter.setData(it)
        })

        viewModel.hideFab.observe(this, Observer {
            when(it) {
                false -> binding.bottomFab.visibility = View.VISIBLE

                true -> binding.bottomFab.visibility = View.GONE
            }
        })

        viewModel.title.observe(this, Observer {
            setTitle()
        })
    }

    private fun setUI() {
        binding.txtVillage.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        setSpinner()
        setBottomView()
        setHorizontal()
        setGrid()
    }

    private fun setTitle() {
        binding.txtVillage.text = "${viewModel.title.value.toString()} 외 ${viewModel.selectedTabCount.value!! - 1}곳" }

    private fun setSpinner() {
        val spinnerList = resources.getStringArray(R.array.spinner)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.item_spinner, spinnerList)
        binding.spinner.adapter = arrayAdapter
    }

    private fun setBottomView() {
        binding.bottomView.background = null
        var badge = binding.bottomView.getOrCreateBadge(R.id.menu_discount)
        badge.isVisible = true
        badge.backgroundColor = resources.getColor(R.color.near_pressed)

        binding.bottomFab.bringToFront()
    }

    private fun setHorizontal() {
        horiAdapter = HoriAdapter()
        binding.RvHorizontal.adapter = horiAdapter
        binding.RvHorizontal.setHasFixedSize(true)

        val list = mutableListOf<HoriItem>()
        for (i in 1 until 5) {
            list.add(
                HoriItem(
                    "가로아이템 ${i}번",
                    "종류 ${i}번",
                    "리뷰 ${i}번",
                    R.drawable.img_main_title,
                    "작성자 ${i}번",
                    R.drawable.img_main_title
                )
            )
        }
        horiAdapter.setList(list)
    }

    private fun setGrid() {
        gridAdapter = GridAdapter()
        binding.RvGrid.layoutManager = GridLayoutManager(this, 2)
        binding.RvGrid.adapter = gridAdapter

        val list = mutableListOf<GridItem>()
        for (i in 1..10) {
            list.add(
                GridItem(
                    R.drawable.img_main_title,
                    false,
                    "${i}/${i} ${i}km",
                    "${i}. 식당제목",
                    "종류 / 세부종류",
                    i.toFloat(),
                    "(리뷰 ${i})"
                )
            )
        }
        gridAdapter.setData(list)
        viewModel.editableGridList = list
        viewModel.setList()

        gridAdapter.clickListener(object : GridAdapter.ClickListener {
            override fun onClick(view: View, position: Int) {
                Log.d("----", "onClick: CLICKED!")
                viewModel.bookmarkClicked(position)
            }

        })
    }

    private fun setData() {
        for(i in 0 until 10) {
            val list = mutableListOf<ChipItem>()
            for (o in 0 until 10) {
                list.add(ChipItem("$i 의 $o 번째 아이템", false))
            }
            viewModel._fragList.add(i, list)
        }
        viewModel.setFirst()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_near_me -> {
                viewModel.isNearActivated()
            }

            R.id.btn_location -> {
                supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.MainContainer, selectFrag).commit()
                viewModel.setFabState(true)
            }
        }
    }
}