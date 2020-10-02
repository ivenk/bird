package com.ivenk.bird.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.ivenk.bird.ChampSelectFragment
import com.ivenk.bird.R

class MainActivity : FragmentActivity() {

    lateinit var enemyChampSelection: ChampSelectFragment
    lateinit var myChampSelection: ChampSelectFragment

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       button = findViewById(R.id.myActivity)
       button.setOnClickListener { onButtonClick() }

        myChampSelection = supportFragmentManager.findFragmentById(R.id.ownChampSelect) as ChampSelectFragment
        enemyChampSelection = supportFragmentManager.findFragmentById(R.id.enemyChampSelect) as ChampSelectFragment
    }

    private fun onButtonClick() {
        startActivity(Intent(this, MatchupActivity::class.java).putExtras(Bundle().apply {
            putString("enemy-champ", enemyChampSelection.getSelectedChampion())
            putString("my-champ", myChampSelection.getSelectedChampion())
        }))
    }


}