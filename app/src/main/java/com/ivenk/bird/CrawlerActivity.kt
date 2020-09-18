package com.ivenk.bird

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class CrawlerActivity : AppCompatActivity() {
    val baseURL = "https://www.mobafire.com/league-of-legends/champion/"
    lateinit var queue : RequestQueue

    lateinit var connectButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        queue = Volley.newRequestQueue(this)

        connectButton = findViewById(R.id.loadButton)
        connectButton.setOnClickListener {
            GlobalScope.launch {
                collectData()
            }
        }
    }

    private fun collectData() {
        val championDoc = browseChamp()

        val urls = findGuides(championDoc)

        browseGuides(urls)
    }

    private fun browseGuides(urls: List<String>) {
        println("BrowseGuides called with :" )

        urls.forEach {
            println(it)
            //TODO: Enable this
            // parseGuide(getPage(it))
        }
    }

    private fun browseChamp(champName: String = "renekton-68"): Document {
        return getPage(baseURL + champName) ?: throw IllegalStateException("Could not retrieve champion data")
    }

    private fun findGuides(webPage: Document): List<String> {
        val urls = mutableListOf<String>()

        val select = webPage.getElementsByClass("browse-list__item")

        /*
        select.forEach{
            // TODO: Continue here
            println("Element found !")
        }
        */

        return urls
    }


    private fun parseGuide(webPage: Document) {
        val element = webPage.getElementsByClass("view-guide__tS__bot__left")
        val champEntries = element.first().getElementsByClass("row")
        champEntries.forEach {
            val select = it.select("h4")
            println(select.text())
        }


    }

    private fun getPage(url : String) = Jsoup.connect(url).get();

}