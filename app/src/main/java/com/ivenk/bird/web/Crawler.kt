package com.ivenk.bird.web

interface Crawler {

    fun gatherData(champion: String): List<MatchupScrap>
}