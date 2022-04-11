package com.jcorp.rc_additional_2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcorp.rc_additional_2.data.ChipItem
import com.jcorp.rc_additional_2.data.GridItem

class mViewModel : ViewModel() {
    private val _isNearPressed = MutableLiveData<Boolean>(false)
    val isNearPressed : LiveData<Boolean> = _isNearPressed

    var editableGridList = mutableListOf<GridItem>()
    private val _gridList = MutableLiveData<MutableList<GridItem>>()
    val gridList : LiveData<MutableList<GridItem>> = _gridList

    var fragList = MutableLiveData<MutableList<MutableList<ChipItem>>>()
    var _fragList = mutableListOf<MutableList<ChipItem>>()

    var _newFragList = MutableLiveData<MutableList<MutableList<ChipItem>>>()

    var clickCountList = MutableLiveData<MutableList<Int?>>()
    var _clickCountList = mutableListOf<Int?>()

    var _newClickCountList = MutableLiveData<MutableList<Int?>>()

    var selectedTabCount = MutableLiveData<Int>(1)

    var _newSelectedTabCount = MutableLiveData<Int>(1)

    var isClicked = MutableLiveData<Boolean>()

    var hideFab = MutableLiveData<Boolean>(false)

    var title = MutableLiveData<String>("지역")

    fun isNearActivated() {
        if(_isNearPressed.value == true) {
            _isNearPressed.value = false
        } else if(_isNearPressed.value == false) {
            _isNearPressed.value = true
        }
    }

    fun setList () {
        _gridList.value = editableGridList
    }

    fun bookmarkClicked (position : Int) {
        editableGridList[position].isBookmarked = !editableGridList[position].isBookmarked
        setList()
    }

    fun setFirst() {
        for(i in 0 until 10) {
            _clickCountList.add(i, 0)
        }
    }

    fun setClickCount (position : Int, isClick : Boolean) {
        if (isClick) {
            _clickCountList[position] = _clickCountList[position]!! + 1
        } else {
            _clickCountList[position] = _clickCountList[position]!! - 1
        }
        notifyClickCount()
        Log.d("====", "setClickCount: ${clickCountList.value}")
    }

    fun notifyClickCount() {
        clickCountList.value = _clickCountList
    }

    fun putChipList () {

        fragList.value = _fragList
    }

    fun setFabState(state : Boolean) {
        hideFab.value = state
    }
}