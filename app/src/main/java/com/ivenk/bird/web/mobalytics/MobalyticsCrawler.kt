package com.ivenk.bird.web.mobalytics

import com.ivenk.bird.web.Crawler
import com.ivenk.bird.web.MatchupScrap
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MobalyticsCrawler : Crawler {
    private val championUrlPart = "league-of-legends/champion/"
    private val baseURL = "https://www.mobafire.com"

    private val guideParser =
        MobalyticsGuideParser()

    override fun gatherData(champion: String): List<MatchupScrap> {
        return browseGuides(findGuides(browseChamp()))
    }

    private fun browseGuides(urls: List<String>) : List<MatchupScrap> {
        return urls.map { url ->
            getPage(url)?.let { guideParser.parse(it) } ?: mutableListOf()
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
        //TODO: Implement usage of guide reference
        //TODO: Filter guides based on rating, patch etc ....
        if (allGuidePanels != null) return allGuidePanels
        else throw IllegalArgumentException("No Elements given !")
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