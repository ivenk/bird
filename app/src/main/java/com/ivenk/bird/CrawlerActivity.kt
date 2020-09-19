package com.ivenk.bird

import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*

class CrawlerActivity : AppCompatActivity() {
    private val championUrlPart = "league-of-legends/champion/"
    private val baseURL = "https://www.mobafire.com"

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    lateinit var queue : RequestQueue

    lateinit var connectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        viewManager = LinearLayoutManager(this)
        val toTypedArray = mutableListOf<String>("test", "test1", "test2").toTypedArray()
        viewAdapter = MatchupAdapter(toTypedArray)

        recyclerView = findViewById<RecyclerView>(R.id.matchups).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        queue = Volley.newRequestQueue(this)

        connectButton = findViewById(R.id.loadButton)
        connectButton.setOnClickListener {
            GlobalScope.launch(context = Dispatchers.IO) {
                collectData()

                GlobalScope.launch(context = Dispatchers.Main) {
                    callBack()
                }
            }
        }
    }

    private fun callBack() {
        println("This hopefully comes from the main thread")
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
            val page = getPage(it)
            if (page != null) {
                val parseGuide = parseGuide(page)
                
                /*
                if (parseGuide != null) {
                    viewAdapter = MatchupAdapter(parseGuide.map { it.first }.toList().toTypedArray())
                    recyclerView.apply { adapter = viewAdapter }
                }

                 */
            }
        }
    }

    private fun browseChamp(champName: String = "renekton-68"): Document {
        // TODO: Change this to use different entdpoint : mobafire/browse ...
        // example:
        // https://www.mobafire.com/league-of-legends/browse?champion=Nasus&role=all&category=all&depth=guide&sort=updated&order=descending&author=all&page=1
        return getPage("$baseURL/$championUrlPart$champName") ?: throw IllegalStateException("Could not retrieve champion data")
    }

    private fun findGuides(webPage: Document): List<String> {
        val urls = mutableListOf<String>()

        val allGuidePanels = webPage.getElementsByClass("browse-list__item")

        val filteredGuides = filterGuides(allGuidePanels)


        filteredGuides.forEach{
            if(it.`is`("a")) {
                urls.add(baseURL + it.attr("href"))
            }
        }

        return urls
    }

    private fun filterGuides(allGuidePanels: Elements?) : Elements {
        //TODO: Filter guides based on rating, patch etc ....
        if (allGuidePanels != null) return allGuidePanels
        else throw IllegalArgumentException("No Elements given !")
    }


    private fun parseGuide(webPage: Document) : List<Triple<String, String, String>>? {
        //TODO: Maybe change return type to not be nullable instead an empty list
        val element = webPage.getElementsByClass("view-guide__tS__bot__left")
        val champEntries = element.first()?.getElementsByClass("row")
        return champEntries?.map {
            val champName = it.select("h4").text()
            val difficulty = it.select("label").text()
            val description = it.select("p").text()
            println("${champName}, difficulty : ${difficulty}")
            Triple(champName, difficulty, description)
        }?.toList()
    }

    private fun getPage(url : String) : Document? {
        return try {
            Jsoup.connect(url).get()
        } catch (exc: HttpStatusException) {
            println("[*] Could not reach url: $url")
            null
        }
    }
}