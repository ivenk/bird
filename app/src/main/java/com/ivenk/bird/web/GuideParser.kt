package com.ivenk.bird.web

import org.jsoup.nodes.Document

interface GuideParser {
    fun parse(guide: Document): List<MatchupScrap>
}
