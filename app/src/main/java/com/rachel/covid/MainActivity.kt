package com.rachel.covid

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rachel.covid.Model.Request
import com.rachel.covid.databinding.ActivityMainBinding
import com.rachel.covid.screens.Protect
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var strProvince: Array<String>
    var strProvinceSelected: String = ""

    lateinit var request: Request


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fetchCovidData().start()
        provinceAndWays()

        strProvince = resources.getStringArray(R.array.provinceName)
        val spinner: Spinner = findViewById(R.id.spinnerProvinsi)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.provinceName,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (parent != null) {
                    strProvinceSelected = parent.getItemAtPosition(position).toString()
                }
                val uppercase = strProvinceSelected.uppercase()

                if(uppercase == request.listData[position].key){
                    updateUI(request)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchCovidData(): Thread {

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Fetching data....")
        progressDialog.show()

        return Thread {
            val url = URL("https://data.covid19.go.id/public/api/prov.json")
            val connection = url.openConnection() as HttpsURLConnection
            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)

                progressDialog.dismiss()

                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            } else {
                binding.connectionError.text = "Failed Connection"
            }
        }
    }

    private fun updateUI(request: Request) {
        runOnUiThread {
            kotlin.run {
                for (i in request.listData.indices) {
                    val tvKasus: TextView = findViewById(R.id.tv_kasus)
                    tvKasus.text = request.listData[i].jumlahKasus.toString()
                    val tvSembuh: TextView = findViewById(R.id.tv_sembuh)
                    tvSembuh.text = request.listData[i].jumlahSembuh.toString()
                    val tvMeninggal: TextView = findViewById(R.id.tv_meninggal)
                    tvMeninggal.text = request.listData[i].jumlahMeninggal.toString()
                    val tvDirawat: TextView = findViewById(R.id.tv_dirawat)
                    tvDirawat.text = request.listData[i].jumlahDirawat.toString()
                }
            }
        }
    }

    private fun provinceAndWays() {
        val btnPrevent = findViewById<Button>(R.id.btn_prevent)
        btnPrevent.setOnClickListener {
            startActivity(Intent(this, Protect::class.java))
        }
    }

}