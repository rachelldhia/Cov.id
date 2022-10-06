package com.rachel.covid.Model

import com.google.gson.annotations.SerializedName

data class Request(

    @field:SerializedName("list_data")
    val listData: List<ListDataItem>
)

data class ListDataItem(

    @field:SerializedName("key")
    val key: String,

    @field:SerializedName("jumlah_meninggal")
    val jumlahMeninggal: Int,

    @field:SerializedName("jumlah_kasus")
    val jumlahKasus: Int,

    @field:SerializedName("jumlah_sembuh")
    val jumlahSembuh: Int,

    @field:SerializedName("jumlah_dirawat")
    val jumlahDirawat: Int
)

