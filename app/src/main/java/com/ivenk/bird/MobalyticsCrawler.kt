package com.ivenk.bird

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MobalyticsCrawler : Crawler {
    private val championUrlPart = "league-of-legends/champion/"
    private val baseURL = "https://www.mobafire.com"


    private fun browseGuides(urls: List<String>) : List<Triple<String, String, String>>{
        println("BrowseGuides called with :" )

        return urls.map {
            val page = getPage(it)
            if (page != null) {
                parseGuide(page) ?: mutableListOf()
            } else {
                mutableListOf()
            }
        }.toList().flatten()
    }

    private fun browseChamp(champName: String = "renekton-68"): Document {
        // TODO: Change this to use different entdpoint : mobafire/browse ...
        // example:
        // https://www.mobafire.com/league-of-legends/browse?champion=Nasus&role=all&category=all&depth=guide&sort=updated&order=descending&author=all&page=1
        return getPage("$baseURL/$championUrlPart$champName") ?: throw IllegalStateException("Could not retrieve champion data")
    }

    private fun findGuides(webPage: Document): List<String> {
        val allGuidePanels = webPage.getElementsByClass("browse-list__item")

        val filteredGuides = filterGuides(allGuidePanels)

        return filteredGuides.map{
            if(it.`is`("a")) {
                baseURL + it.attr("href")
            } else null
        }.filterNotNull()
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

    override fun gatherData(champion: String): List<Triple<String, String, String>> {
        val championDoc = browseChamp()

        val urls = findGuides(championDoc)

        return browseGuides(urls)
    }
}