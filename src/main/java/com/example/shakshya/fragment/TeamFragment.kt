package com.example.shakshya.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.shakshya.R

class TeamFragment : Fragment() {

    lateinit var dev1:CardView
    lateinit var dev2:CardView
    lateinit var dev3:CardView
    lateinit var dev4:CardView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_team, container, false)
        dev1=view.findViewById(R.id.dev1)
        dev2=view.findViewById(R.id.dev2)
        dev3=view.findViewById(R.id.dev3)
        dev4=view.findViewById(R.id.dev4)
        dev1.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/sushil-kumar-bh20/"))
            startActivity(intent)
        }
        dev2.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("http://linkedin.com/in/yuvraj-2503"))
            startActivity(intent)
        }
        dev3.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/himanshu-gupta-0624/"))
            startActivity(intent)
        }
        dev4.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/shubham-varshney-5822701aa/"))
            startActivity(intent)
        }
        return view
    }
}