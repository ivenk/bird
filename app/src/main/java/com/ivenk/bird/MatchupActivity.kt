package com.ivenk.bird

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivenk.bird.web.Crawler
import com.ivenk.bird.web.MobalyticsCrawler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchupActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var connectButton: Button

    lateinit var crawler: Crawler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val enemyChamp = intent.extras?.getString("enemy-champ")
        println("And here its $enemyChamp")

        crawler = MobalyticsCrawler()

        viewManager = LinearLayoutManager(this)
        viewAdapter = MatchupAdapter(mutableListOf())

        recyclerView = findViewById<RecyclerView>(R.id.matchups).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        connectButton = findViewById(R.id.loadButton)
        connectButton.setOnClickListener {
            GlobalScope.launch(context = Dispatchers.IO) {
                val collectedData = crawler.gatherData("sth")

                GlobalScope.launch(context = Dispatchers.Main) {
                    callBack(collectedData)
                }
            }
        }
    }

    private fun callBack(data : List<Triple<String, String, String>>) {
        viewAdapter = MatchupAdapter(data)
        recyclerView.apply { adapter = viewAdapter }
    }
}