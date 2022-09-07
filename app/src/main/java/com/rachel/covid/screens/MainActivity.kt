package com.rachel.covid.screens

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.rachel.covid.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCovidData().start()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchCovidData(): Thread{
        return Thread{
            val url = URL("https://data.covid19.go.id/public/api/prov.json")
            val connection = url.openConnection() as HttpsURLConnection

            if (connection.responseCode == 200){
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
            }else{
                binding.connectionError.text = "Connection Error"
            }
        }
    }

}