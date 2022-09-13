package com.example.weater


import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weater.databinding.ActivityMainBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject


const val API_KEY = "4c055295910e4768bf5204040221109"



class MainActivity : AppCompatActivity() {



    lateinit var binding : ActivityMainBinding
    private var temp: String = ""
    private var weather: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteCity.setOnClickListener{
            binding.textCity.text = null
        }

        //Kostyl!!!!!!!!!!
        reView()
        //!!!!!!!!!!!!!!!!

        binding.btnGetWeather.setOnClickListener{
            //TO COROUTINE. Я РЕАЛЬНО НЕ ПОНИМАЮ КАК ЭТО ВСЕ ЗАПИХАТЬ В КОРУТИНЫ!!!
            // думал и в класс это вот все запихать, но там какая то проблема с запросом вылезает
            // Так что это просто приложение где я юзаю API
            // Но кажется я придумал, но нет времени. Просто не успею...

            reView()
        }


    }

    private fun reView(){
        binding.viewCity.text = binding.textCity.editableText
        getResult(binding.viewCity.text.toString())

    }



    private fun getResult(city: String){
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$city&aqi=no"
        val queue = Volley.newRequestQueue(applicationContext)//А ТЕПЕРЬ МОЖНО И В КЛАС ЗАХУЯРИТЬ, ТОЛЬКО ПОКА ХЗ ЗОЧЕМ
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {
                    response -> parseJSON(response)
            },
            {
                Log.d("MyLog", " ----------- error $it")
            }
        )
        queue.add(stringRequest)
    }

    private fun parseJSON(result: String){

        val mainObject = JSONObject(result)
        temp = mainObject.getJSONObject("current").getString("temp_c").toString()
        binding.viewTemp.text = buildString {
            append(temp)
            append("°C")
        }
        binding.viewWeather.text = mainObject.getJSONObject("current").getJSONObject("condition").getString("text").toString()
    }
}