package com.ivenk.bird.web

import com.ivenk.bird.domain.MatchupScrap

interface Crawler {

    fun gatherData(champion: String): List<MatchupScrap>
}