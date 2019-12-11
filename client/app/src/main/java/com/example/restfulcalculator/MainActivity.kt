package com.example.restfulcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var n1 = Number1.text
        var n2 = Number2.text


        button_plus_get.setOnClickListener {
            val client: OkHttpClient = OkHttpClient()
            var request = Request.Builder()
                .url("http://10.251.77.148:4000/api/addition?first_number=${n1.toString()}&second_number=${n2.toString()}")
                .build()
//            Toast.makeText(baseContext, "no response", Toast.LENGTH_LONG).show()
            Log.e("REST", "request addition")
            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.message)
//                    Toast.makeText(baseContext, "no response", Toast.LENGTH_LONG).show()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        runOnUiThread {
                            val s = response.body()!!.string()
                            result_view.text = s

                            Log.e("REST", s)
                            Toast.makeText(baseContext, s, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }

        button_plus_post.setOnClickListener {
            var jsonObject = JSONObject()
            jsonObject.put("first_number", "${n1.toString()}")
            jsonObject.put("second_number", "${n2.toString()}");

            val json = MediaType.parse("application/json; charset=utf-8")
            val body = RequestBody.create(json, jsonObject.toString())

            val client: OkHttpClient = OkHttpClient()

            val request = Request.Builder()
                .url("http://10.251.77.148:4000/api/addition")
                .post(body)
                .build()
//            Toast.makeText(baseContext, "no response", Toast.LENGTH_LONG).show()
            Log.e("REST", "request addition by post")
            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("POST", e.message!!)
//                    Toast.makeText(baseContext, "no response", Toast.LENGTH_LONG).show()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        runOnUiThread {
                            val s = response.body()!!.string()
                            result_view.text = s
                            Log.e("REST", s)
                            Toast.makeText(baseContext, s, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })

        }
    }
}