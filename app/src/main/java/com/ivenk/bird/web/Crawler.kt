package com.ivenk.bird.web

interface Crawler {

    fun gatherData(champion: String): List<Triple<String, String, String>>
}