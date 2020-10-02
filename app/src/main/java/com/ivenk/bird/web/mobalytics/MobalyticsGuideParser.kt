package com.ivenk.bird.web.mobalytics

import com.ivenk.bird.web.GuideParser
import com.ivenk.bird.web.MatchupScrap
import org.jsoup.nodes.Document

class MobalyticsGuideParser : GuideParser {
    override fun parse(guide: Document): List<MatchupScrap> {
        val element = guide.getElementsByClass(matchupSection)
        val champEntries = element.first()?.getElementsByClass(champEntry)
        return champEntries?.map {
            MatchupScrap(
                it.select(championName)
                    .text(),
                it.select(difficulty)
                    .text(),
                it.select(description)
                    .text()
            )
        }?.toList() ?: mutableListOf()
    }


    companion object {
        private const val matchupSection = "view-guide__tS__bot__left"
        private const val champEntry = "row"
        private const val championName = "h4"
        private const val difficulty = "label"
        private const val description = "p"
    }
}