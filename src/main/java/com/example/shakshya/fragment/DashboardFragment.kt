package com.example.shakshya.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shakshya.R
import com.example.shakshya.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DashboardFragment : Fragment() {

    lateinit var etNews: EditText
    lateinit var etDesc: EditText
    lateinit var etLink: EditText
    lateinit var btnCheck: Button
    lateinit var btnCheck2:Button
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        etNews=view.findViewById(R.id.etNews)
        etDesc=view.findViewById(R.id.etDesc)
        etLink=view.findViewById(R.id.etLink)
        btnCheck=view.findViewById(R.id.btnCheck)
        btnCheck2=view.findViewById(R.id.btnCheck2)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)
        btnCheck.setOnClickListener {
            progressLayout.visibility=View.VISIBLE
            val title=etNews.text.toString()
            val desc=etDesc.text.toString()
            if (ConnectionManager().checkConnectivity(activity as Context)){
                val queue= Volley.newRequestQueue(activity as Context)
                val url="https://shakshya.herokuapp.com/api"     //add api here
                val request=object : JsonObjectRequest(Request.Method.POST,url,null, Response.Listener{
                    try {
                        //Response will be handled here
                        progressLayout.visibility=View.GONE
                        val success=it.getString("res")
                        if (success=="fake"){
                            val dialog= AlertDialog.Builder(activity as Context)
                            dialog.setTitle("Result")
                            dialog.setMessage("Appears to be Fake.")
                            dialog.setPositiveButton("OK"){text,listener ->

                            }
                            dialog.create()
                            dialog.show()
                        }else{
                            val dialog= AlertDialog.Builder(activity as Context)
                            dialog.setTitle("Result")
                            dialog.setMessage("Appears to be Real.")
                            dialog.setPositiveButton("OK"){text,listener ->

                            }
                            dialog.create()
                            dialog.show()
                        }

                    }catch (e: JSONException){
                        Toast.makeText(activity as Context,"Some Unexpected Error Occurred..!!",
                            Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener{
                    //error handled here
                    Toast.makeText(activity as Context,"Volley Error Occurred..!!", Toast.LENGTH_SHORT).show()
                }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers=HashMap<String,String>()
                        headers["Content-type"]="application/json"
                        return headers
                    }
                    override fun getBody(): ByteArray {
                        val params = HashMap<String, String>()
                        params["title"]=title
                        params["maintext"]=desc
                        return JSONObject(params as Map<*, *>).toString().toByteArray()
                    }
                }
                queue.add(request)
            }else{
                //Internet not available
                val dialog= AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Not Connected..!!")
                dialog.setPositiveButton("Open Settings"){text,listener ->
                    val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    activity?.finish()
                }
                dialog.setNegativeButton("Exit"){text,listener ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()
            }
            etNews.text.clear()
            etDesc.text.clear()
            etLink.text.clear()
        }

        btnCheck2.setOnClickListener {
            progressLayout.visibility=View.VISIBLE
            val link=etLink.text.toString()
            if (ConnectionManager().checkConnectivity(activity as Context)){
                val queue= Volley.newRequestQueue(activity as Context)
                val url="https://shakshya.herokuapp.com/api?link=$link"     //add api here

                val request=object : JsonObjectRequest(Request.Method.GET,url,null, Response.Listener{
                    try {
                        //Response will be handled here
                        progressLayout.visibility=View.GONE
                        val response=it.getString("res")
                        if (response=="fake"){
                            val dialog= AlertDialog.Builder(activity as Context)
                            dialog.setTitle("Result")
                            dialog.setMessage("Appears to be Fake.")
                            dialog.setPositiveButton("OK"){text,listener ->

                            }
                            dialog.create()
                            dialog.show()
                        }
                        else{
                            val dialog= AlertDialog.Builder(activity as Context)
                            dialog.setTitle("Result")
                            dialog.setMessage("Appears to be Real.")
                            dialog.setPositiveButton("OK"){text,listener ->

                            }
                            dialog.create()
                            dialog.show()
                        }

                    }catch (e: Exception){
                        Toast.makeText(activity as Context,"Some Unexpected Error Occurred..!!",
                            Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener{
                    //error handled here
                    Toast.makeText(activity as Context,"Volley Error..", Toast.LENGTH_SHORT).show()
                }){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers=HashMap<String,String>()
                        headers["Content-type"]="application/json"
                        return headers
                    }
                }
                queue.add(request)
            }else{
                //Internet not available
                val dialog= AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Not Connected..!!")
                dialog.setPositiveButton("Open Settings"){text,listener ->
                    val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    activity?.finish()
                }
                dialog.setNegativeButton("Exit"){text,listener ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()
            }
            etNews.text.clear()
            etDesc.text.clear()
            etLink.text.clear()
        }
        return view
    }
}