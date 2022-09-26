package com.rachel.covid.screens

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class SpinnerConfig : Activity(), AdapterView.OnItemSelectedListener {

    var strProvinceSelected: String = ""
    private lateinit var strProvince: Array<String>


    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        strProvinceSelected = parent.getItemAtPosition(pos).toString()
        spinnerProvinsi.isEnabled = true
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText( this, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }
}