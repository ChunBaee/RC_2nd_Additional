package com.jcorp.rc_additional_2.data

data class GridItem (
    var gridImage : Int? = 0,
    var isBookmarked : Boolean = false,
    var gridLocation : String? = "",
    var gridTitle : String? = "",
    var gridType : String? = "",
    var gridRate : Float? = 0F,
    var gridRateCount : String? = ""
        )
