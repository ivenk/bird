package com.ivenk.bird

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), ChampSelectFragment.ChampSelectListener {

   lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       button = findViewById(R.id.myActivity)
       button.setOnClickListener { startActivity(Intent(this, MatchupActivity::class.java)) }
    }

    override fun onChampSelected(champ: String) {
        println("Activity received selected champion $champ")
    }
}