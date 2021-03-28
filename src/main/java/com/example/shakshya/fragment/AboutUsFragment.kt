package com.example.shakshya.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.shakshya.R

class AboutUsFragment : Fragment() {

    lateinit var btnLink:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_about_us, container, false)
        btnLink=view.findViewById(R.id.btnKnowMore)
        btnLink.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://himanshu-0624.github.io/Shakshya/index.html"))
            startActivity(intent)
        }
        return view
    }
}