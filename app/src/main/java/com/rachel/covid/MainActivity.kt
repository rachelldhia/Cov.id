package com.rachel.covid

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.rachel.covid.Model.Request
import com.rachel.covid.databinding.ActivityMainBinding
import com.rachel.covid.screens.Protect
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    var progressDialog: ProgressDialog? = null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fetchCovidData().start()
        provinceAndWays()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchCovidData(): Thread
    {
        return Thread {
            val url = URL("https://data.covid19.go.id/public/api/prov.json")
            val connection  = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                binding.connectionError.text = "Failed Connection"
            }
        }
    }

    private fun updateUI(request: Request)
    {
        runOnUiThread {
            kotlin.run {
                for (i in request.list_data.indices) {
                    val tvKasus: TextView = findViewById(R.id.tv_kasus)
                    tvKasus.text = request.list_data[i].jumlah_kasus.toString()
                    val tvSembuh: TextView = findViewById(R.id.tv_sembuh)
                    tvSembuh.text = request.list_data[i].jumlah_sembuh.toString()
                    val tvMeninggal: TextView = findViewById(R.id.tv_meninggal)
                    tvMeninggal.text = request.list_data[i].jumlah_meninggal.toString()
                    val tvDirawat: TextView = findViewById(R.id.tv_dirawat)
                    tvDirawat.text = request.list_data[i].jumlah_dirawat.toString()
                }
            }
        }
    }



    private fun provinceAndWays(){
        val btnPrevent = findViewById<Button>(R.id.btn_prevent)
        btnPrevent.setOnClickListener{
            startActivity(Intent(this, Protect::class.java))
        }
    }


}